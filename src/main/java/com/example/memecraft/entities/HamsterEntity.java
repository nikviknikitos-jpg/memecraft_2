package com.example.memecraft.entities;

import com.example.memecraft.items.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class HamsterEntity extends PathAwareEntity {
    public HamsterEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
        this.setCustomName(Text.literal("[Hamster Kombat]"));
        this.setCustomNameVisible(true);
        this.setPersistent();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 8.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 0.6));
        this.goalSelector.add(2, new LookAroundGoal(this));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!getWorld().isClient && player instanceof ServerPlayerEntity sp) {
            sp.getInventory().insertStack(new ItemStack(ModItems.MEME_COIN));
            setVelocity(0, 0.6, 0);
            velocityModified = true;
            for (int i = 0; i < 20; i++) {
                getWorld().addParticle(ParticleTypes.NOTE,
                        getX() + (random.nextDouble() - 0.5), getY() + 1, getZ() + (random.nextDouble() - 0.5), 0, 0.1, 0);
            }
            sp.sendMessage(Text.literal("§eПи-пи! Держи монетку!"), false);
        }
        return ActionResult.SUCCESS;
    }
}
