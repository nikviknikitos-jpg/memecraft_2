package com.example.memecraft;

import com.example.memecraft.blocks.ModBlocks;
import com.example.memecraft.command.MemeCommand;
import com.example.memecraft.entities.ModEntities;
import com.example.memecraft.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemeCraftMod implements ModInitializer {
    public static final String MODID = "memecraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        ModBlocks.register();
        ModItems.register();
        ModEntities.register();

        FabricDefaultAttributeRegistry.register(ModEntities.ANOMALY_MINION,
                com.example.memecraft.entities.AnomalyMinionEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.WORM_PARASITE,
                com.example.memecraft.entities.WormParasiteEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.HAMSTER,
                com.example.memecraft.entities.HamsterEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BEGGAR,
                com.example.memecraft.entities.BeggarEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BANANA_MONKEY,
                com.example.memecraft.entities.BananaMonkeyEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.PLAYER_MIMIC,
                com.example.memecraft.entities.PlayerMimicEntity.createAttributes());

        CommandRegistrationCallback.EVENT.register((d, ra, e) -> MemeCommand.register(d));
        LOGGER.info("MemeCraftMod initialized");
    }
}
