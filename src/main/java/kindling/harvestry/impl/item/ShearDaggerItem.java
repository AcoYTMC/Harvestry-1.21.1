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
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

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

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        boolean gui = MiscUtils.isGui(renderMode);

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
            } else if (leftType == Type.BOTANICAL && rightType == Type.NONE) {
                path = "botanical_dagger";
            } else if (leftType == Type.ALCHEMICAL && rightType == Type.NONE) {
                path = "alchemical_dagger";
            } else if (leftType == Type.CANNIBAL && rightType == Type.NONE) {
                path = "cannibal_dagger";
            } else if (leftType == Type.CANNIBAL && rightType == Type.CANNIBAL) {
                path = "double_cannibal";
            } else if (leftType == Type.ALCHEMICAL && rightType == Type.ALCHEMICAL) {
                path = "double_alchemical";
            } else if (leftType == Type.BOTANICAL && rightType == Type.BOTANICAL) {
                path = "double_botanical";
            }
        } else {
            path += "_dagger";
        }

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

        // Case 1: Cursor is empty -> take out stored dagger
        if (cursorStack.isEmpty() && storedType != Type.NONE) {
            Item storedItem = HarvestryItems.getDaggerFromType(storedType);
            if (storedItem == null) return false;

            cursorReference.set(new ItemStack(storedItem));
            stack.set(HarvestryComponents.SINGLE_SLOT, new SingleSlotComponent(Type.NONE));
            return true;
        }

        // Case 2: Cursor has a dagger -> store it in the main dagger
        if (cursorStack.getItem() instanceof ShearDaggerItem incomingDagger) {
            Type incomingType = incomingDagger.getType();

            // Swap if something is already stored
            stack.set(HarvestryComponents.SINGLE_SLOT, new SingleSlotComponent(incomingType));

            if (storedType == Type.NONE) {
                // Nothing previously stored -> remove cursor dagger
                cursorReference.set(ItemStack.EMPTY);
            } else {
                // Swap -> put old stored dagger in cursor
                Item oldItem = HarvestryItems.getDaggerFromType(storedType);
                cursorReference.set(oldItem == null ? ItemStack.EMPTY : new ItemStack(oldItem));
            }
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

        // Case 1: Slot empty -> take out stored dagger
        if (slotStack.isEmpty() && storedType != Type.NONE) {
            Item storedItem = HarvestryItems.getDaggerFromType(storedType);
            if (storedItem != null) {
                slot.setStack(new ItemStack(storedItem));
                stack.set(HarvestryComponents.SINGLE_SLOT, new SingleSlotComponent(Type.NONE));
            }
            return true;
        }

        // Case 2: Slot has dagger -> store it in main dagger
        if (slotStack.getItem() instanceof ShearDaggerItem daggerInSlot) {
            Type incomingType = daggerInSlot.getType();

            // Swap if something is already stored
            stack.set(HarvestryComponents.SINGLE_SLOT, new SingleSlotComponent(incomingType));

            if (storedType == Type.NONE) {
                // Nothing previously stored -> remove dagger from slot
                slot.setStack(ItemStack.EMPTY);
            } else {
                Item oldItem = HarvestryItems.getDaggerFromType(storedType);
                slot.setStack(oldItem == null ? ItemStack.EMPTY : new ItemStack(oldItem));
            }
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
                Harvestry.id("double_cannibal_dagger"),
                Harvestry.id("double_cannibal_dagger"),
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
