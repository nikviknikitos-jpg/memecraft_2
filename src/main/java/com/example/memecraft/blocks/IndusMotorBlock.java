package com.example.memecraft.blocks;

import com.example.memecraft.entities.FlyingTireEntity;
import com.example.memecraft.entities.ModEntities;
import com.example.memecraft.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IndusMotorBlock extends Block implements BlockEntityProvider {
    public IndusMotorBlock(Settings s) { super(s); }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IndusMotorBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof IndusMotorBlockEntity motor)) return ActionResult.PASS;

        ItemStack held = player.getStackInHand(hand);

        if (held.getItem() == ModItems.TIRE_ITEM && !motor.hasTire) {
            motor.hasTire = true;
            held.decrement(1);
            motor.markDirty();
            world.updateListeners(pos, state, state, 3);
            world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0f, 1.2f);
            return ActionResult.SUCCESS;
        }

        if (player.isSneaking() && motor.hasTire && motor.running) {
            motor.hasTire = false;
            motor.running = false;
            float rpm = motor.rpm;
            motor.rpm = 0;
            motor.markDirty();
            world.updateListeners(pos, state, state, 3);
            Direction dir = state.get(FACING);
            Vec3d v = new Vec3d(dir.getOffsetX() * 1.6, 0.25, dir.getOffsetZ() * 1.6);
            FlyingTireEntity tire = ModEntities.FLYING_TIRE.create((ServerWorld) world);
            if (tire != null) {
                tire.refreshPositionAndAngles(pos.getX() + 0.5 + v.x, pos.getY() + 0.5 + v.y, pos.getZ() + 0.5 + v.z, 0, 0);
                double speed = 0.9 + (rpm / 100.0) * 1.3;
                tire.setVelocity(dir.getOffsetX() * speed, 0.15, dir.getOffsetZ() * speed);
                tire.setPower(rpm, rpm > 80);
                world.spawnEntity(tire);
            }
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, 1.5f);
            return ActionResult.SUCCESS;
        }

        if (held.isEmpty()) {
            motor.running = !motor.running;
            motor.markDirty();
            world.updateListeners(pos, state, state, 3);
            world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS,
                    1.0f, motor.running ? 1.2f : 0.8f);
        }
        return ActionResult.SUCCESS;
    }

    public static final net.minecraft.state.property.DirectionProperty FACING =
            net.minecraft.state.property.Properties.HORIZONTAL_FACING;
}
