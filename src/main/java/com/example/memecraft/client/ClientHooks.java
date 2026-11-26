package com.example.memecraft.client;

import net.minecraft.client.MinecraftClient;

public class ClientHooks {
    public static void openDeck() { MinecraftClient.getInstance().setScreen(new DeckScreen()); }
    public static void openEncyclopedia() { MinecraftClient.getInstance().setScreen(new EncyclopediaScreen()); }
    public static void openPhone() { MinecraftClient.getInstance().setScreen(new PhoneScreen()); }
}
