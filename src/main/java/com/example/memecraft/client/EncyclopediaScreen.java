package com.example.memecraft.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class EncyclopediaScreen extends Screen {
    private int page = 0;
    private static final String[] PAGES = {
        "§6Индусский Моторчик\n\n3D-агрегат. ПКМ — вкл/выкл.\nПКМ Покрышкой — надеть колесо.\nShift+ПКМ — сорвать колесо.",
        "§6Бешеное Колесо\n\nЛетит по земле, сбивает мобов.\nЧерез 20 секунд перегрева —\nвзлетает в огне.",
        "§6Фиолетовая Аномалия\n\nШар 5x5 с вращающимися кольцами.\nКинь палку — появится\n6 человечков.",
        "§6Кнопка Удачи\n\n6 случайных мем-событий.\nНаковальня, левитация, курицы,\nаномалия, еда, БАБАХ.",
        "§6Колода Карт\n\n12 карт. Каждая даёт 3 эффекта.\nСова на Скакалке —\nлегендарная беда!",
        "§6Телефон РКН\n\nПри ПКМ открывается экран.\nДвойное «Обновить» →\nвзрыв в руке.",
        "§6Хомяк и Бродяга\n\nХомяк [Hamster Kombat] даёт монетку.\nБродяга даёт Удачу III.",
        "§6Червь и Мимик\n\nЧервь длиной 3 блока кусается.\nМимик копирует игрока.",
        "§6Казино\n\nБилетик строит здание 13x7x13\nс 3 автоматами.",
    };

    public EncyclopediaScreen() { super(Text.literal("Мемная Энциклопедия")); }

    @Override
    protected void init() {
        super.init();
        int cx = width / 2, cy = height / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("<"), b -> { if (page > 0) page--; })
                .dimensions(cx - 120, cy + 100, 40, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal(">"), b -> {
            if (page < PAGES.length - 1) page++;
        }).dimensions(cx + 80, cy + 100, 40, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        int w = 260, h = 220;
        int left = (width - w) / 2, top = (height - h) / 2;
        context.fill(left, top, left + w, top + h, 0xFFF4E2C0);
        context.drawBorder(left, top, w, h, 0xFF603020);
        context.drawCenteredTextWithShadow(textRenderer, "Мемная Энциклопедия", left + w / 2, top + 10, 0xFF603020);
        var lines = textRenderer.wrapLines(Text.literal(PAGES[page]), 230);
        int y = top + 40;
        for (var line : lines) {
            context.drawText(textRenderer, line, left + 15, y, 0xFF442200, false);
            y += 12;
        }
        context.drawCenteredTextWithShadow(textRenderer, (page + 1) + "/" + PAGES.length,
                left + w / 2, top + h - 20, 0xFF603200);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override public boolean shouldPause() { return false; }
}
