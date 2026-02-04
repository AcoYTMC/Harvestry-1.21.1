package kindling.harvestry.impl.block.entity;

import kindling.harvestry.impl.component.ItemHoldingBlockComponent;
import kindling.harvestry.impl.index.HarvestryBlockEntities;
import kindling.harvestry.impl.index.HarvestryComponents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WitherRibcgeBlockEntity extends BlockEntity {
    private float timer = 0f;

    public WitherRibcgeBlockEntity(BlockPos pos, BlockState state) {
        super(HarvestryBlockEntities.WITHER_RIBCAGE, pos, state);
    }

    public float getTimer() {
        return timer;
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T be) {
        if (be instanceof WitherRibcgeBlockEntity wrbe) {
            wrbe.timer += 1f;
        }
    }

    public void setHeldItem(ItemStack stack) {
        ItemStack copy = stack.isEmpty() ? ItemStack.EMPTY : stack.copy();
        //this.set(HarvestryComponents.SINGLE_SLOT, new ItemHoldingBlockComponent(copy));
        this.markDirty();

        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public ItemStack getHeldItem() {
        //ItemHoldingBlockComponent component = this.get(HarvestryComponents.SINGLE_SLOT);
        return ItemStack.EMPTY; /*component != null ? component.stack() : ItemStack.EMPTY;*/
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        ItemStack stack = this.getHeldItem();
        if (!stack.isEmpty()) {
            nbt.put("HeldItem", stack.encode(registryLookup));
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains("HeldItem")) {
            ItemStack stack = ItemStack.fromNbt(registryLookup, nbt.getCompound("HeldItem"))
                    .orElse(ItemStack.EMPTY);
            this.setHeldItem(stack);
        }
    }
}

