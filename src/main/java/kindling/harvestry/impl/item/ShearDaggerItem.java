package kindling.harvestry.impl.item;

import kindling.harvestry.api.item.DoubleWieldedItem;
import kindling.harvestry.impl.Harvestry;
import kindling.harvestry.impl.component.SingleSlotComponent;
import kindling.harvestry.impl.index.HarvestryBlocks;
import kindling.harvestry.impl.index.HarvestryComponents;
import kindling.harvestry.impl.index.HarvestryItems;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static net.minecraft.block.Block.dropStack;

public class ShearDaggerItem extends SwordItem implements DoubleWieldedItem, ModelVaryingItem {
    Type type;

    public ShearDaggerItem(Type type, Settings settings) {
        super(ToolMaterials.NETHERITE, settings);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean isDouble(ItemStack stack) {
        SingleSlotComponent component = stack.get(HarvestryComponents.SINGLE_SLOT);
        return component != null && component.type() != ShearDaggerItem.Type.NONE;
    }

    @Override
    public ItemStack getSecondStack(ItemStack stack) {
        SingleSlotComponent component = stack.get(HarvestryComponents.SINGLE_SLOT);
        if (component == null || component.type() == ShearDaggerItem.Type.NONE) {
            return ItemStack.EMPTY;
        }

        Item item = HarvestryItems.getDaggerFromType(component.type());
        return item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
    }

    private String getComboPath(ItemStack stack, boolean gui) {
        Type leftType = this.getType();
        Type rightType = stack.get(HarvestryComponents.SINGLE_SLOT).type();

        String path = leftType.name().toLowerCase();

        if (gui) {
            if (leftType == Type.ALCHEMICAL && rightType == Type.CANNIBAL) {
                path = "alchemical_left_cannibal_right";
            } else if (leftType == Type.CANNIBAL && rightType == Type.ALCHEMICAL) {
                path = "alchemical_right_cannibal_left";
            } else if (leftType == Type.BOTANICAL && rightType == Type.ALCHEMICAL) {
                path = "botanical_left_alchemical_right";
            } else if (leftType == Type.BOTANICAL && rightType == Type.CANNIBAL) {
                path = "botanical_left_cannibal_right";
            } else if (leftType == Type.ALCHEMICAL && rightType == Type.BOTANICAL) {
                path = "botanical_right_alchemical_left";
            } else if (leftType == Type.CANNIBAL && rightType == Type.BOTANICAL) {
                path = "botanical_right_cannibal_left";
            } else if (rightType == Type.NONE) {
                path = leftType.name().toLowerCase() + "_dagger";
            } else if (leftType == rightType) {
                path = "double_" + leftType.name().toLowerCase() + "_dagger";
            }
        } else {
            path += "_dagger";
        }

        return path;
    }

    @Override
    public Text getName(ItemStack stack) {
        if (stack.get(HarvestryComponents.SINGLE_SLOT).type() != Type.NONE) {
            return Text.translatable("item.harvestry.shear_daggers");
        }
        String path = getComboPath(stack, true);
        return Text.translatable("item.harvestry." + path);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Type mainType = this.getType();
        Type secondType = stack.get(HarvestryComponents.SINGLE_SLOT).type();

        tooltip.add(Text.literal(WordUtils.capitalizeFully(mainType.toString())).setStyle(getTypeStyle(mainType)));

        if (stack.get(HarvestryComponents.SINGLE_SLOT).type() != Type.NONE) {
            tooltip.add(Text.literal(WordUtils.capitalizeFully(secondType.toString())).setStyle(getTypeStyle(secondType)));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    private Style getTypeStyle(Type type) {
        switch (type) {
            case ALCHEMICAL -> {
                return Style.EMPTY.withColor(TextColor.fromRgb(0xE485EC));
            }
            case BOTANICAL -> {
                return Style.EMPTY.withColor(TextColor.fromRgb(0x54824B));
            }
            case CANNIBAL -> {
                return Style.EMPTY.withColor(TextColor.fromRgb(0xB56030));
            }
        }
        return Style.EMPTY;
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        boolean gui = MiscUtils.isGui(renderMode);
        String path = getComboPath(stack, gui);
        return Harvestry.id(path);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack cursorStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorReference) {
        if (clickType != ClickType.RIGHT || !slot.canTakePartial(player)) {
            return false;
        }

        SingleSlotComponent component = stack.get(HarvestryComponents.SINGLE_SLOT);
        if (component == null) return false;

        Type storedType = component.type();

        if (cursorStack.isEmpty() && storedType != Type.NONE) {
            cursorReference.set(HarvestryItems.getDaggerFromType(storedType).getDefaultStack());
            stack.set(HarvestryComponents.SINGLE_SLOT, new SingleSlotComponent(Type.NONE));
            return true;
        }

        if (!(cursorStack.getItem() instanceof ShearDaggerItem incomingDagger)) {
            return false;
        }

        Type incomingType = incomingDagger.getType();

        if (storedType == Type.NONE) {
            cursorStack.decrement(1);
            stack.set(HarvestryComponents.SINGLE_SLOT, new SingleSlotComponent(incomingType));
            return true;
        }

        return false;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) return false;

        SingleSlotComponent component = stack.get(HarvestryComponents.SINGLE_SLOT);
        if (component == null) return false;

        Type storedType = component.type();
        ItemStack slotStack = slot.getStack();

        if (slotStack.isEmpty() && storedType != Type.NONE) {
            slot.setStack(HarvestryItems.getDaggerFromType(storedType).getDefaultStack());
            stack.set(HarvestryComponents.SINGLE_SLOT, new SingleSlotComponent(Type.NONE));
            return true;
        }

        if (!(slotStack.getItem() instanceof ShearDaggerItem daggerInSlot)) {
            return false;
        }

        Type incomingType = daggerInSlot.getType();

        if (storedType == Type.NONE) {
            slot.setStack(ItemStack.EMPTY);
            stack.set(HarvestryComponents.SINGLE_SLOT, new SingleSlotComponent(incomingType));
            return true;
        }

        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();

        if (block instanceof BeehiveBlock hive && state.get(BeehiveBlock.HONEY_LEVEL) == 5) {
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
            stack.damage(1, player, LivingEntity.getSlotForHand(player.getActiveHand()));
            world.emitGameEvent(player, GameEvent.SHEAR, pos);
            BeehiveBlock.dropHoneycomb(world, pos);

            if (CampfireBlock.isLitCampfireInRange(world, pos) && hasBees(world, pos)) {
                hive.takeHoney(world, state, pos, player, BeehiveBlockEntity.BeeState.EMERGENCY);
            } else {
                hive.takeHoney(world, state, pos);
            }

            Random random = player.getRandom();

            int count = 1;
            if (random.nextFloat() < 0.1f) {
                count = 2;
            }

            ItemStack dropStack = HarvestryItems.WEIRD_WAX.getDefaultStack();
            dropStack.setCount(count);
            dropStack(world, pos, dropStack);
        }

        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof SheepEntity sheep && !sheep.isSheared()) {
            sheep.sheared(SoundCategory.PLAYERS);
            sheep.emitGameEvent(GameEvent.SHEAR);
            Random random = user.getRandom();

            int count = 1;
            if (random.nextFloat() < 0.1f) {
                count = 2;
            }

            ItemStack dropStack = HarvestryBlocks.COMPACTED_STRANGE_SILK.asItem().getDefaultStack();
            dropStack.setCount(count);
            dropStack(user.getWorld(), entity.getBlockPos(), dropStack);
        }

        return super.useOnEntity(stack, user, entity, hand);
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Harvestry.id("alchemical_dagger"),
                Harvestry.id("botanical_dagger"),
                Harvestry.id("cannibal_dagger"),
                Harvestry.id("double_alchemical_dagger"),
                Harvestry.id("double_botanical_dagger"),
                Harvestry.id("double_cannibal_dagger"),
                Harvestry.id("alchemical_left_cannibal_right"),
                Harvestry.id("alchemical_right_cannibal_left"),
                Harvestry.id("botanical_left_alchemical_right"),
                Harvestry.id("botanical_left_cannibal_right"),
                Harvestry.id("botanical_right_alchemical_left"),
                Harvestry.id("botanical_right_cannibal_left")
        );
    }

    public BipedEntityModel.ArmPose getArmPose(ItemStack stack, AbstractClientPlayerEntity player) {
        return BipedEntityModel.ArmPose.ITEM;
    }

    private boolean hasBees(World world, BlockPos pos) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof BeehiveBlockEntity beehiveBlockEntity) {
            return !beehiveBlockEntity.hasNoBees();
        } else {
            return false;
        }
    }

    public enum Type {
        NONE,
        ALCHEMICAL,
        BOTANICAL,
        CANNIBAL
    }
}
