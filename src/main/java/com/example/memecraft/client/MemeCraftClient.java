package com.example.memecraft.client;

import com.example.memecraft.blocks.ModBlocks;
import com.example.memecraft.entities.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class MemeCraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.FLYING_TIRE, FlyingTireRenderer::new);
        EntityRendererRegistry.register(ModEntities.VOID_ANOMALY, VoidAnomalyRenderer::new);
        EntityRendererRegistry.register(ModEntities.ANOMALY_MINION, ctx -> new MobEntityRenderer<com.example.memecraft.entities.AnomalyMinionEntity, BipedEntityModel<com.example.memecraft.entities.AnomalyMinionEntity>>(
                ctx, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER)), 0.45f) {
            @Override
            public Identifier getTexture(com.example.memecraft.entities.AnomalyMinionEntity e) {
                return new Identifier("memecraft", "textures/entity/anomaly_minion.png");
            }
        });
        EntityRendererRegistry.register(ModEntities.WORM_PARASITE, WormParasiteRenderer::new);
        EntityRendererRegistry.register(ModEntities.PLAYER_MIMIC, ctx -> new MobEntityRenderer<com.example.memecraft.entities.PlayerMimicEntity, BipedEntityModel<com.example.memecraft.entities.PlayerMimicEntity>>(
                ctx, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER)), 0.6f) {
            @Override
            public Identifier getTexture(com.example.memecraft.entities.PlayerMimicEntity e) {
                return new Identifier("memecraft", "textures/entity/player_mimic.png");
            }
        });
        EntityRendererRegistry.register(ModEntities.HAMSTER, ctx -> new BipedEntityRenderer<com.example.memecraft.entities.HamsterEntity, BipedEntityModel<com.example.memecraft.entities.HamsterEntity>>(
                ctx, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER)), 0.4f) {
            @Override
            public Identifier getTexture(com.example.memecraft.entities.HamsterEntity e) {
                return new Identifier("memecraft", "textures/entity/hamster.png");
            }
        });
        EntityRendererRegistry.register(ModEntities.BEGGAR, ctx -> new MobEntityRenderer<com.example.memecraft.entities.BeggarEntity, BipedEntityModel<com.example.memecraft.entities.BeggarEntity>>(
                ctx, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER)), 0.6f) {
            @Override
            public Identifier getTexture(com.example.memecraft.entities.BeggarEntity e) {
                return new Identifier("memecraft", "textures/entity/beggar.png");
            }
        });
        EntityRendererRegistry.register(ModEntities.BANANA_MONKEY, ctx -> new MobEntityRenderer<com.example.memecraft.entities.BananaMonkeyEntity, BipedEntityModel<com.example.memecraft.entities.BananaMonkeyEntity>>(
                ctx, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER)), 0.6f) {
            @Override
            public Identifier getTexture(com.example.memecraft.entities.BananaMonkeyEntity e) {
                return new Identifier("memecraft", "textures/entity/banana_monkey.png");
            }
        });
        BlockEntityRendererRegistry.register(ModBlocks.INDUS_MOTOR_BE, IndusMotorBlockEntityRenderer::new);
    }
}
