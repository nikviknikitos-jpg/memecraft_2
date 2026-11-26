package com.example.memecraft.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class PhoneScreen extends Screen {
    private int refreshes = 0;

    public PhoneScreen() { super(Text.literal("RK Phone")); }

    @Override
    protected void init() {
        super.init();
        int cx = width / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Обновить страницу"), b -> {
            refreshes++;
            if (refreshes >= 2) {
                this.close();
            }
        }).dimensions(cx - 60, height / 2 + 60, 120, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        int cx = width / 2, cy = height / 2;
        int w = 180, h = 240;
        int x = cx - w / 2, y = cy - h / 2;
        context.fill(x, y, x + w, y + h, 0xFF202020);
        context.fill(x + 10, y + 30, x + w - 10, y + h - 40, refreshes >= 2 ? 0xFF800000 : 0xFFCC2222);
        context.drawCenteredTextWithShadow(textRenderer,
                refreshes >= 2 ? "ПЕРЕГРЕВ СЕРВЕРОВ!" : "Доступ к YouTube ограничен",
                cx, y + 60, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer,
                refreshes >= 2 ? "..." : "Попытка соединения...",
                cx, y + 90, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override public boolean shouldPause() { return false; }
}
