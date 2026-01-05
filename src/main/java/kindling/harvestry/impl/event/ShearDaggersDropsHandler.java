package kindling.harvestry.impl.event;

import kindling.harvestry.impl.index.HarvestryBlocks;
import kindling.harvestry.impl.index.HarvestryItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

import static net.minecraft.block.Block.dropStack;

public class ShearDaggersDropsHandler {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity.getWorld() instanceof ServerWorld world) {
                if (damageSource.getAttacker() instanceof PlayerEntity player && player.isHolding(HarvestryItems.ALCHEMICAL_DAGGER)) {
                    Random random = world.getRandom();
                    BlockPos pos = entity.getBlockPos();
                    if (entity instanceof BeeEntity) { // ? Weird Wax
                        if (random.nextFloat() < 0.3f) {
                            int count = 1;
                            if (random.nextFloat() < 0.1f) {
                                count = 2;
                            }

                            ItemStack dropStack = HarvestryItems.WEIRD_WAX.getDefaultStack();
                            dropStack.setCount(count);
                            dropStack(world, pos, dropStack);
                        }
                    } else if (entity instanceof WitherEntity) { // ? Wither Ribcage
                        while (world.getBlockState(pos.down()).equals(Blocks.AIR.getDefaultState())) {
                            pos = pos.down();
                        }

                        world.setBlockState(pos, HarvestryBlocks.WITHER_RIBCAGE.getDefaultState());
                    } else if (entity.getType().isIn(EntityTypeTags.UNDEAD) && !(entity instanceof SkeletonEntity) ) { // ? Cursed Flesh
                        if (random.nextFloat() < 0.3f) {
                            int count = 1;
                            if (random.nextFloat() < 0.1f) {
                                count = 2;
                            }

                            ItemStack dropStack = HarvestryItems.CURSED_FLESH.getDefaultStack();
                            dropStack.setCount(count);
                            dropStack(world, pos, dropStack);
                        }
                    } else if (entity instanceof SkeletonEntity) { // ? Baffling Crystals
                        if (random.nextFloat() < 0.3f) {
                            int count = 1;
                            if (random.nextFloat() < 0.1f) {
                                count = 2;
                            }

                            ItemStack dropStack = HarvestryItems.BAFFLING_CRYSTALS.getDefaultStack();
                            dropStack.setCount(count);
                            dropStack(world, pos, dropStack);
                        }
                    } else if (entity instanceof SpiderEntity) { // ? Cursed Flesh
                        if (random.nextFloat() < 0.3f) {
                            int count = 1;
                            if (random.nextFloat() < 0.1f) {
                                count = 2;
                            }

                            ItemStack dropStack = HarvestryItems.STRANGE_SILK.getDefaultStack();
                            dropStack.setCount(count);
                            dropStack(world, pos, dropStack);
                        }
                    }
                }
            }
        });
    }
}
