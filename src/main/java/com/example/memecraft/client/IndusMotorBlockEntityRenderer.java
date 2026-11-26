package com.example.memecraft.client;

import com.example.memecraft.blocks.IndusMotorBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class IndusMotorBlockEntityRenderer implements BlockEntityRenderer<IndusMotorBlockEntity> {
    private final BlockRenderManager brm;

    public IndusMotorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.brm = MinecraftClient.getInstance().getBlockRenderManager();
    }

    @Override
    public void render(IndusMotorBlockEntity be, float tickDelta, MatrixStack m, VertexConsumerProvider vcp, int light, int overlay) {
        // Shaft
        m.push();
        m.translate(0.5, 0.4, 0.5);
        m.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(be.shaftRotation));
        m.push();
        m.translate(0.8, 0.05, 0);
        m.scale(0.4f, 0.4f, 0.4f);
        brm.renderBlockAsEntity(Blocks.IRON_BLOCK.getDefaultState(), m, vcp, light, OverlayTexture.DEFAULT_UV);
        m.pop();
        m.pop();
        // Lever
        m.push();
        m.translate(0.5, 0.9, 0.5);
        m.multiply(RotationAxis.POSITIVE_X.rotationDegrees(be.running ? 35f : -35f));
        m.scale(0.15f, 0.4f, 0.15f);
        brm.renderBlockAsEntity(Blocks.LEVER.getDefaultState(), m, vcp, light, OverlayTexture.DEFAULT_UV);
        m.pop();
        // Tire on shaft
        if (be.hasTire) {
            m.push();
            m.translate(0.5, 0.45, 0.5);
            m.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(be.shaftRotation));
            m.translate(0.9, 0, 0);
            m.scale(0.5f, 0.5f, 0.5f);
            brm.renderBlockAsEntity(Blocks.BLACK_CONCRETE.getDefaultState(), m, vcp, light, OverlayTexture.DEFAULT_UV);
            m.pop();
        }
    }
}
