package com.example.memecraft.items;

import com.example.memecraft.entities.ModEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CasinoTicketItem extends Item {
    public CasinoTicketItem(Settings s) { super(s); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp && world instanceof ServerWorld sw) {
            BlockPos base = sp.getBlockPos();
            for (int dx = -6; dx <= 6; dx++) {
                for (int dz = -6; dz <= 6; dz++) {
                    for (int dy = 0; dy <= 6; dy++) {
                        boolean wall = Math.abs(dx) == 6 || Math.abs(dz) == 6 || dy == 0 || dy == 6;
                        if (wall && (dy == 0 || dy == 6)) {
                            if ((dx + dz + dy) % 3 == 0) {
                                sw.setBlockState(base.add(dx, dy, dz), Blocks.GOLD_BLOCK.getDefaultState());
                            } else {
                                sw.setBlockState(base.add(dx, dy, dz), Blocks.QUARTZ_BLOCK.getDefaultState());
                            }
                        } else if (wall && (Math.abs(dx) == 6 || Math.abs(dz) == 6) && dy < 6) {
                            if ((dx + dz + dy) % 4 == 0) {
                                sw.setBlockState(base.add(dx, dy, dz), Blocks.RED_WOOL.getDefaultState());
                            } else {
                                sw.setBlockState(base.add(dx, dy, dz), Blocks.QUARTZ_BLOCK.getDefaultState());
                            }
                        } else if (dy == 6) {
                            sw.setBlockState(base.add(dx, dy, dz), Blocks.GOLD_BLOCK.getDefaultState());
                        } else {
                            sw.setBlockState(base.add(dx, dy, dz), Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                sw.setBlockState(base.add(i - 1, 1, -3), com.example.memecraft.blocks.ModBlocks.SLOT_MACHINE.getDefaultState());
            }
            sp.getStackInHand(hand).decrement(1);
            sw.playSound(null, base, SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, SoundCategory.PLAYERS, 2f, 1f);
            sp.sendMessage(net.minecraft.text.Text.literal("§6Добро пожаловать в Казино!"), false);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
