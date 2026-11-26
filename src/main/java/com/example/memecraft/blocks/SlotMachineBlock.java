package com.example.memecraft.blocks;

import com.example.memecraft.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
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
import net.minecraft.world.World;

public class SlotMachineBlock extends Block {
    public SlotMachineBlock(Settings s) { super(s); }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult hit) {
        if (!world.isClient && player instanceof ServerPlayerEntity sp && world instanceof ServerWorld sw) {
            ItemStack held = sp.getStackInHand(hand);
            if (held.getItem() == ModItems.MEME_COIN) {
                held.decrement(1);
                int roll = sw.random.nextInt(5);
                switch (roll) {
                    case 0 -> {
                        sp.getInventory().insertStack(new ItemStack(Items.DIAMOND, 1 + sw.random.nextInt(3)));
                        sp.sendMessage(net.minecraft.text.Text.literal("§b+ Алмазы!"), false);
                    }
                    case 1 -> {
                        sp.getInventory().insertStack(new ItemStack(Items.GOLDEN_APPLE, 1));
                        sp.sendMessage(net.minecraft.text.Text.literal("§6+ Золотое яблоко!"), false);
                    }
                    case 2 -> {
                        sp.getInventory().insertStack(new ItemStack(ModItems.MEME_COIN, 5));
                        sp.sendMessage(net.minecraft.text.Text.literal("§e+ 5 монеток!"), false);
                    }
                    case 3 -> {
                        sp.getInventory().insertStack(new ItemStack(ModItems.MEME_DECK, 1));
                        sp.sendMessage(net.minecraft.text.Text.literal("§d+ Колода!"), false);
                    }
                    case 4 -> {
                        sw.createExplosion(null, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                                1.0f, World.ExplosionSourceType.NONE);
                        sp.sendMessage(net.minecraft.text.Text.literal("§cМини-взрыв!"), false);
                    }
                }
                sw.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.BLOCKS, 1f, 1.4f);
            }
        }
        return ActionResult.SUCCESS;
    }
}
