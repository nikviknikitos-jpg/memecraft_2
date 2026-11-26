package com.example.memecraft.items;

import com.example.memecraft.entities.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpawnEggWrapper extends Item {
    private final String entityId;

    public SpawnEggWrapper(String entityId) {
        super(new Settings());
        this.entityId = entityId;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp && world instanceof ServerWorld sw) {
            EntityType<?> type = Registries.ENTITY_TYPE.get(new Identifier("memecraft", entityId));
            if (type != null) {
                Entity e = type.create(sw);
                if (e != null) {
                    Vec3d look = sp.getRotationVec(1.0f).normalize();
                    Vec3d pos = sp.getPos().add(look.multiply(2.0));
                    e.refreshPositionAndAngles(pos.x, pos.y, pos.z, 0, 0);
                    sw.spawnEntity(e);
                }
            }
            sp.getStackInHand(hand).decrement(1);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
