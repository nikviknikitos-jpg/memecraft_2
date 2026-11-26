package com.example.memecraft.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class DeckScreen extends Screen {
    private boolean flipped = false;
    private int card = 0;
    private static final String[] NAMES = {
            "Сова на Скакалке", "Собака-Гольфач", "Жираф с Пузырями", "Змейка на Сёрфе",
            "Скунс-Гонщик", "Дельфин-Боксёр", "Медведь на Скейте", "Тигр-Фокусник",
            "Обезьяна с Шариками", "Фламинго в Маркете", "Осьминог с Мороженым", "Индюк-Пират"
    };

    public DeckScreen() { super(Text.literal("Колода Карт")); }

    @Override
    protected void init() {
        super.init();
        int cx = width / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.literal(flipped ? "Ещё карту" : "Открыть карту"), b -> {
            if (!flipped) {
                flipped = true;
                card = (int) (Math.random() * 12);
            } else {
                flipped = false;
            }
        }).dimensions(cx - 60, height - 40, 120, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        int cx = width / 2, cy = height / 2;
        int w = 135, h = 195;
        int x = cx - w / 2, y = cy - h / 2;
        if (!flipped) {
            context.fill(x, y, x + w, y + h, 0xFF1A2A80);
            context.drawBorder(x, y, w, h, 0xFFFFC030);
            context.drawCenteredTextWithShadow(textRenderer, "Нажми на карту!", cx, y - 20, 0xFFFFD060);
        } else {
            context.fill(x, y, x + w, y + h, 0xFFF5F0DC);
            context.drawBorder(x, y, w, h, 0xFF603020);
            context.drawCenteredTextWithShadow(textRenderer, NAMES[card], cx, y + 100, 0xFF603020);
            context.drawCenteredTextWithShadow(textRenderer, "§7(расклад судьбы)", cx, y + 130, 0xFF603020);
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override public boolean shouldPause() { return false; }
}
