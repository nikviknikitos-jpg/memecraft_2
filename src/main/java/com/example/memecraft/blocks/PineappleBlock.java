package com.example.memecraft.blocks;

import com.example.memecraft.entities.BananaMonkeyEntity;
import com.example.memecraft.entities.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.particle.ParticleTypes;

public class PineappleBlock extends Block {
    public PineappleBlock(Settings s) { super(s); }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (!world.isClient && world instanceof ServerWorld sw) {
            for (int i = 0; i < 4; i++) {
                BananaMonkeyEntity monkey = ModEntities.BANANA_MONKEY.create(sw);
                if (monkey != null) {
                    double angle = Math.PI * 2 * i / 4.0;
                    monkey.refreshPositionAndAngles(pos.getX() + 0.5 + Math.cos(angle) * 2.5, pos.getY() + 1,
                            pos.getZ() + 0.5 + Math.sin(angle) * 2.5, 0, 0);
                    sw.spawnEntity(monkey);
                }
            }
            for (int i = 0; i < 30; i++) {
                sw.spawnParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                        1, 0.5, 0.5, 0.5, 0.05);
            }
        }
    }
}
