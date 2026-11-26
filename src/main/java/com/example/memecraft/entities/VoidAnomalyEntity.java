package com.example.memecraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class VoidAnomalyEntity extends Entity {
    public int age = 0;

    public VoidAnomalyEntity(EntityType<?> type, World world) {
        super(type, world);
        this.setInvulnerable(true);
    }

    @Override
    public void tick() {
        super.tick();
        age++;
        if (getWorld().isClient) {
            for (int i = 0; i < 3; i++) {
                double a = random.nextDouble() * Math.PI * 2;
                double r = 2.5 + random.nextDouble() * 1.5;
                getWorld().addParticle(ParticleTypes.PORTAL,
                        getX() + Math.cos(a) * r, getY() + 2.5 + (random.nextDouble() - 0.5) * 2,
                        getZ() + Math.sin(a) * r, 0, 0.05, 0);
            }
        } else {
            if (age % 40 == 0 && getWorld() instanceof ServerWorld sw) {
                sw.spawnParticles(ParticleTypes.DRAGON_BREATH, getX(), getY() + 2.5, getZ(), 20, 2.5, 2.5, 2.5, 0.05);
            }
            Box box = getBoundingBox().expand(4.0);
            for (ItemEntity ie : getWorld().getEntitiesByClass(ItemEntity.class, box, e -> e.getStack().isOf(Items.STICK))) {
                if (getWorld() instanceof ServerWorld sw) {
                    sw.spawnParticles(ParticleTypes.FLASH, getX(), getY() + 2.5, getZ(), 1, 0, 0, 0, 0);
                    sw.playSound(null, getBlockPos(), SoundEvents.ENTITY_ENDERMAN_AMBIENT, SoundCategory.NEUTRAL, 2f, 0.8f);
                    for (int i = 0; i < 6; i++) {
                        double a = Math.PI * 2 * i / 6.0;
                        AnomalyMinionEntity m = ModEntities.ANOMALY_MINION.create(sw);
                        if (m != null) {
                            m.refreshPositionAndAngles(getX() + Math.cos(a) * 2.5, getY(), getZ() + Math.sin(a) * 2.5, 0, 0);
                            sw.spawnEntity(m);
                        }
                    }
                }
                ie.discard();
            }
        }
    }

    @Override public boolean canHit() { return true; }
    @Override protected void initDataTracker() {}
    @Override public void readCustomDataFromNbt(NbtCompound nbt) {}
    @Override public void writeCustomDataToNbt(NbtCompound nbt) {}
}
