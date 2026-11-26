package com.example.memecraft.client;

import com.example.memecraft.entities.FlyingTireEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class FlyingTireRenderer extends EntityRenderer<FlyingTireEntity> {
    private final net.minecraft.client.render.item.ItemRenderer itemRenderer;

    public FlyingTireRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public Identifier getTexture(FlyingTireEntity e) {
        return new Identifier("memecraft", "textures/item/tire_item.png");
    }

    @Override
    public void render(FlyingTireEntity e, float yaw, float tickDelta, MatrixStack m,
                       VertexConsumerProvider vcp, int light) {
        m.push();
        m.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(e.age * 20));
        m.multiply(RotationAxis.POSITIVE_X.rotationDegrees(e.age * 10));
        ItemStack s = new ItemStack(com.example.memecraft.items.ModItems.TIRE_ITEM);
        itemRenderer.renderItem(s, ModelTransformationMode.GROUND, light,
                OverlayTexture.DEFAULT_UV, m, vcp, e.getWorld(), e.getId());
        m.pop();
    }
}
