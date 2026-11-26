package com.example.memecraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlyingTireEntity extends Entity {
    public float rpm = 30f;
    public boolean burning = false;
    public int lifeTicks = 0;

    public FlyingTireEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public void setPower(float rpm, boolean burning) {
        this.rpm = rpm;
        this.burning = burning;
    }

    @Override
    public void tick() {
        super.tick();
        lifeTicks++;
        if (lifeTicks > 600) { discard(); return; }
        if (!getWorld().isClient) {
            Vec3d v = getVelocity();
            if (horizontalCollision) {
                setVelocity(-v.x * 0.6, 0.4, -v.z * 0.6);
            } else if (verticalCollision && v.y < 0) {
                setVelocity(v.x, 0, v.z);
            }
            Box box = getBoundingBox().expand(1.0);
            for (LivingEntity le : getWorld().getEntitiesByClass(LivingEntity.class, box, e -> e.isAlive())) {
                le.damage(getWorld().getDamageSources().magic(), 4.0f + rpm / 10f);
                Vec3d dir = getVelocity().normalize().multiply(1.2);
                le.setVelocity(dir.x, 0.5, dir.z);
                le.velocityModified = true;
                if (burning) le.setOnFireFor(5);
            }
            if (getWorld() instanceof ServerWorld sw && burning && lifeTicks % 5 == 0) {
                sw.spawnParticles(ParticleTypes.FLAME, getX(), getY() + 0.3, getZ(), 4, 0.3, 0.3, 0.3, 0.02);
                if (lifeTicks % 100 == 0) {
                    sw.createExplosion(this, getX(), getY(), getZ(), 4.0f, World.ExplosionSourceType.TNT);
                    discard();
                }
            }
        } else {
            getWorld().addParticle(burning ? ParticleTypes.FLAME : ParticleTypes.CLOUD,
                    getX() + (random.nextDouble() - 0.5) * 0.5, getY() + 0.5,
                    getZ() + (random.nextDouble() - 0.5) * 0.5, 0, 0.02, 0);
        }
    }

    @Override public boolean canHit() { return true; }
    @Override protected void initDataTracker() {}
    @Override public void readCustomDataFromNbt(NbtCompound nbt) {}
    @Override public void writeCustomDataToNbt(NbtCompound nbt) {}
}
