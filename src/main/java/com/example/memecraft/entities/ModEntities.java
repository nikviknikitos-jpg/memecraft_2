package com.example.memecraft.entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<FlyingTireEntity> FLYING_TIRE;
    public static EntityType<VoidAnomalyEntity> VOID_ANOMALY;
    public static EntityType<AnomalyMinionEntity> ANOMALY_MINION;
    public static EntityType<WormParasiteEntity> WORM_PARASITE;
    public static EntityType<PlayerMimicEntity> PLAYER_MIMIC;
    public static EntityType<HamsterEntity> HAMSTER;
    public static EntityType<BeggarEntity> BEGGAR;
    public static EntityType<BananaMonkeyEntity> BANANA_MONKEY;

    public static void register() {
        FLYING_TIRE = Registry.register(Registries.ENTITY_TYPE, new Identifier("memecraft", "flying_tire"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, FlyingTireEntity::new)
                        .dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());
        VOID_ANOMALY = Registry.register(Registries.ENTITY_TYPE, new Identifier("memecraft", "void_anomaly"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, VoidAnomalyEntity::new)
                        .dimensions(EntityDimensions.fixed(5.0f, 5.0f)).build());
        ANOMALY_MINION = Registry.register(Registries.ENTITY_TYPE, new Identifier("memecraft", "anomaly_minion"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AnomalyMinionEntity::new)
                        .dimensions(EntityDimensions.fixed(0.45f, 0.9f)).build());
        WORM_PARASITE = Registry.register(Registries.ENTITY_TYPE, new Identifier("memecraft", "worm_parasite"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WormParasiteEntity::new)
                        .dimensions(EntityDimensions.fixed(0.9f, 3.0f)).build());
        PLAYER_MIMIC = Registry.register(Registries.ENTITY_TYPE, new Identifier("memecraft", "player_mimic"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PlayerMimicEntity::new)
                        .dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
        HAMSTER = Registry.register(Registries.ENTITY_TYPE, new Identifier("memecraft", "hamster"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HamsterEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
        BEGGAR = Registry.register(Registries.ENTITY_TYPE, new Identifier("memecraft", "beggar"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BeggarEntity::new)
                        .dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
        BANANA_MONKEY = Registry.register(Registries.ENTITY_TYPE, new Identifier("memecraft", "banana_monkey"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BananaMonkeyEntity::new)
                        .dimensions(EntityDimensions.fixed(0.6f, 0.9f)).build());
    }
}
