package com.example.memecraft.client;

import com.example.memecraft.entities.VoidAnomalyEntity;
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

public class VoidAnomalyRenderer extends EntityRenderer<VoidAnomalyEntity> {
    private final BlockRenderManager brm;

    public VoidAnomalyRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.brm = MinecraftClient.getInstance().getBlockRenderManager();
    }

    @Override
    public Identifier getTexture(VoidAnomalyEntity e) {
        return new Identifier("minecraft", "textures/block/purpur_block.png");
    }

    @Override
    public void render(VoidAnomalyEntity e, float yaw, float tickDelta, MatrixStack m,
                       VertexConsumerProvider vcp, int light) {
        float age = e.age + tickDelta;
        m.push();
        m.translate(0, 2.5, 0);
        int seg = 12;
        for (int i = 0; i < seg; i++) {
            double lat = Math.PI * (i + 0.5) / seg - Math.PI / 2;
            for (int j = 0; j < seg * 2; j++) {
                double lon = Math.PI * j / seg;
                double R = 2.5;
                double x = R * Math.cos(lat) * Math.cos(lon + age * 0.02);
                double y = R * Math.sin(lat);
                double z = R * Math.cos(lat) * Math.sin(lon + age * 0.02);
                m.push();
                m.translate(x, y, z);
                m.scale(0.6f, 0.6f, 0.6f);
                brm.renderBlockAsEntity(Blocks.PURPUR_BLOCK.getDefaultState(), m, vcp, light, OverlayTexture.DEFAULT_UV);
                m.pop();
            }
        }
        m.pop();
        for (int r = 0; r < 2; r++) {
            m.push();
            m.translate(0, 2.5, 0);
            m.multiply(RotationAxis.POSITIVE_X.rotationDegrees(r == 0 ? 25 + age * 2 : -25 - age * 2));
            for (int i = 0; i < 24; i++) {
                double a = Math.PI * 2 * i / 24.0;
                double R = r == 0 ? 3.2 : 4.2;
                m.push();
                m.translate(Math.cos(a) * R, 0, Math.sin(a) * R);
                m.scale(0.4f, 0.4f, 0.4f);
                brm.renderBlockAsEntity(Blocks.PURPUR_PILLAR.getDefaultState(), m, vcp, light, OverlayTexture.DEFAULT_UV);
                m.pop();
            }
            m.pop();
        }
    }
}
