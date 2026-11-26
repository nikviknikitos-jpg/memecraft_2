package com.example.memecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RedButtonBoxBlock extends Block {
    public RedButtonBoxBlock(Settings s) { super(s); }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult hit) {
        if (!world.isClient && player instanceof ServerPlayerEntity sp && world instanceof ServerWorld sw) {
            int roll = world.random.nextInt(6);
            switch (roll) {
                case 0 -> {
                    BlockPos bp = sp.getBlockPos().add(0, 8, 0);
                    sw.setBlockState(bp, net.minecraft.block.Blocks.ANVIL.getDefaultState());
                    sw.playSound(null, pos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 2f, 0.7f);
                    sp.sendMessage(net.minecraft.text.Text.literal("§6Наковальня!"), false);
                }
                case 1 -> {
                    sp.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 60, 2));
                    sp.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 4));
                    sp.sendMessage(net.minecraft.text.Text.literal("§bЛевитация и Скорость V!"), false);
                }
                case 2 -> {
                    for (int i = 0; i < 6; i++) {
                        ChickenEntity c = EntityType.CHICKEN.create(sw);
                        if (c != null) {
                            c.refreshPositionAndAngles(sp.getX() + (i - 3), sp.getY() + 1, sp.getZ() + i, 0, 0);
                            sw.spawnEntity(c);
                        }
                    }
                    sp.sendMessage(net.minecraft.text.Text.literal("§e6 куриц!"), false);
                }
                case 3 -> {
                    var a = com.example.memecraft.entities.ModEntities.VOID_ANOMALY.create(sw);
                    if (a != null) {
                        a.refreshPositionAndAngles(sp.getX() + 6, sp.getY(), sp.getZ(), 0, 0);
                        sw.spawnEntity(a);
                    }
                    sp.sendMessage(net.minecraft.text.Text.literal("§5Аномалия!"), false);
                }
                case 4 -> {
                    for (int i = 0; i < 16; i++) {
                        ItemEntity ie = new ItemEntity(sw,
                                sp.getX() + (sw.random.nextDouble() - 0.5) * 4, sp.getY() + 3,
                                sp.getZ() + (sw.random.nextDouble() - 0.5) * 4,
                                new ItemStack(sw.random.nextBoolean() ? Items.BAKED_POTATO : Items.GOLDEN_APPLE));
                        sw.spawnEntity(ie);
                    }
                    sp.sendMessage(net.minecraft.text.Text.literal("§6Фонтан еды!"), false);
                }
                case 5 -> {
                    Box box = sp.getBoundingBox().expand(8);
                    for (var m : sw.getEntitiesByClass(net.minecraft.entity.mob.MobEntity.class, box, e -> true)) {
                        Vec3d dir = m.getPos().subtract(sp.getPos()).normalize().multiply(2.0);
                        m.setVelocity(dir.x, 1.0, dir.z);
                        m.velocityModified = true;
                    }
                    sw.createExplosion(sp, sp.getX(), sp.getY(), sp.getZ(), 1.5f, World.ExplosionSourceType.NONE);
                    sp.sendMessage(net.minecraft.text.Text.literal("§cБАБАХ!"), false);
                }
            }
        }
        return ActionResult.SUCCESS;
    }
}
