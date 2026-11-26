package com.example.memecraft.blocks;

import com.example.memecraft.entities.FlyingTireEntity;
import com.example.memecraft.entities.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class IndusMotorBlockEntity extends BlockEntity {
    public boolean running = false;
    public boolean hasTire = false;
    public float rpm = 0f;
    public int runningTicks = 0;
    public float shaftRotation = 0f;

    public IndusMotorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.INDUS_MOTOR_BE, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        running = nbt.getBoolean("running");
        hasTire = nbt.getBoolean("hasTire");
        rpm = nbt.getFloat("rpm");
        runningTicks = nbt.getInt("runningTicks");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("running", running);
        nbt.putBoolean("hasTire", hasTire);
        nbt.putFloat("rpm", rpm);
        nbt.putInt("runningTicks", runningTicks);
    }

    public void serverTick() {
        if (world == null || world.isClient) return;
        if (running) {
            runningTicks++;
            rpm = Math.min(100f, rpm + 100f / 300f);
            shaftRotation += rpm * 0.2f;
            if (world instanceof ServerWorld sw) {
                sw.spawnParticles(ParticleTypes.LARGE_SMOKE,
                        pos.getX() + 0.5, pos.getY() + 1.3, pos.getZ() + 0.5,
                        1, 0.1, 0.05, 0.1, 0.01);
                if (rpm > 50) {
                    sw.spawnParticles(ParticleTypes.ELECTRIC_SPARK,
                            pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5,
                            1, 0.2, 0.2, 0.2, 0.05);
                }
                if (runningTicks > 300) {
                    sw.spawnParticles(ParticleTypes.FLAME,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            3, 0.3, 0.3, 0.3, 0.02);
                }
                if (runningTicks > 400 && hasTire) {
                    running = false;
                    hasTire = false;
                    FlyingTireEntity tire = ModEntities.FLYING_TIRE.create(sw);
                    if (tire != null) {
                        tire.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);
                        Vec3d dir = new Vec3d(1, 0, 0);
                        tire.setVelocity(dir.multiply(0.9));
                        tire.setPower(100f, true);
                        sw.spawnEntity(tire);
                    }
                    runningTicks = 0;
                    rpm = 0;
                }
            }
        } else {
            rpm = Math.max(0f, rpm - 100f / 300f);
            shaftRotation += rpm * 0.2f;
            if (rpm < 0.1f) runningTicks = 0;
        }
        markDirty();
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
