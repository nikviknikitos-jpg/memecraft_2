package com.example.memecraft.entities;

import com.example.memecraft.items.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BeggarEntity extends PathAwareEntity {
    public BeggarEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
        this.setPersistent();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 0.5));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 16));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!getWorld().isClient && player instanceof ServerPlayerEntity sp) {
            ItemStack held = sp.getStackInHand(hand);
            if (held.getItem() == ModItems.MEME_COIN) {
                held.decrement(1);
                sp.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 3600, 2));
                sp.getInventory().insertStack(new ItemStack(Items.DIAMOND, 3));
                sp.sendMessage(Text.literal("§aСпасибо, друг! Лови Удачу III и подарок!"), false);
            } else {
                sp.sendMessage(Text.literal("§eЭй, друг, подкинь монетку на удачу!"), false);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void tick() {
        super.tick();
        if (!getWorld().isClient && age % 40 == 0) {
            PlayerEntity closest = getWorld().getClosestPlayer(this, 8.0);
            if (closest instanceof ServerPlayerEntity sp && sp.getInventory().contains(new ItemStack(ModItems.MEME_COIN))) {
                sp.sendMessage(Text.literal("§eЭй, друг, подкинь монетку на удачу!"), false);
            }
        }
    }
}
