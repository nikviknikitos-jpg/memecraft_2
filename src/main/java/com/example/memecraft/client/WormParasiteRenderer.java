package com.example.memecraft.client;

import com.example.memecraft.entities.WormParasiteEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class WormParasiteRenderer extends EntityRenderer<WormParasiteEntity> {
    private final BlockRenderManager brm;

    public WormParasiteRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.brm = MinecraftClient.getInstance().getBlockRenderManager();
    }

    @Override
    public Identifier getTexture(WormParasiteEntity e) {
        return new Identifier("minecraft", "textures/block/brown_mushroom_block.png");
    }

    @Override
    public void render(WormParasiteEntity e, float yaw, float tickDelta, MatrixStack m,
                       VertexConsumerProvider vcp, int light) {
        float age = e.age + tickDelta;
        m.push();
        for (int i = 0; i < 6; i++) {
            m.push();
            double wave = Math.sin(age * 0.2 + i * 0.6) * 0.3;
            m.translate(0, 0.2 + i * 0.45, wave);
            m.scale(0.6f, 0.5f, 0.6f);
            brm.renderBlockAsEntity(Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState(), m, vcp, light, OverlayTexture.DEFAULT_UV);
            m.pop();
        }
        m.pop();
    }
}
