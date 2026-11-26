package com.example.memecraft.items;

import com.example.memecraft.blocks.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item TIRE_ITEM, MEME_DECK, MEME_ENCYCLOPEDIA, CASINO_TICKET, MEME_COIN,
            RK_PHONE, BANANA_ITEM, ANOMALY_SPAWNER, WORM_SPAWNER, MIMIC_SPAWNER,
            HAMSTER_SPAWNER, BEGGAR_SPAWNER, MONKEY_SPAWNER;
    public static ItemGroup TAB;

    public static void register() {
        TIRE_ITEM = reg("tire_item", new Item(new Item.Settings()));
        MEME_DECK = reg("meme_deck", new MemeDeckItem(new Item.Settings().maxCount(1)));
        MEME_ENCYCLOPEDIA = reg("meme_encyclopedia", new MemeEncyclopediaItem(new Item.Settings().maxCount(1)));
        CASINO_TICKET = reg("casino_ticket", new CasinoTicketItem(new Item.Settings()));
        MEME_COIN = reg("meme_coin", new Item(new Item.Settings()));
        RK_PHONE = reg("rk_phone", new RkPhoneItem(new Item.Settings().maxCount(1)));
        BANANA_ITEM = reg("banana_item", new Item(new Item.Settings()));
        ANOMALY_SPAWNER = reg("anomaly_spawner", new SpawnEggWrapper("void_anomaly"));
        WORM_SPAWNER = reg("worm_spawner", new SpawnEggWrapper("worm_parasite"));
        MIMIC_SPAWNER = reg("mimic_spawner", new SpawnEggWrapper("player_mimic"));
        HAMSTER_SPAWNER = reg("hamster_spawner", new SpawnEggWrapper("hamster"));
        BEGGAR_SPAWNER = reg("beggar_spawner", new SpawnEggWrapper("beggar"));
        MONKEY_SPAWNER = reg("monkey_spawner", new SpawnEggWrapper("banana_monkey"));

        TAB = Registry.register(Registries.ITEM_GROUP, new Identifier("memecraft", "memecraft_tab"),
                FabricItemGroup.builder().displayName(Text.translatable("itemGroup.memecraft.memecraft_tab"))
                        .icon(() -> new ItemStack(MEME_COIN))
                        .entries((ctx, entries) -> {
                            entries.add(ModBlocks.INDUS_MOTOR.asItem());
                            entries.add(TIRE_ITEM);
                            entries.add(ModBlocks.RED_BUTTON_BOX.asItem());
                            entries.add(MEME_DECK);
                            entries.add(MEME_ENCYCLOPEDIA);
                            entries.add(ModBlocks.PINEAPPLE_BLOCK.asItem());
                            entries.add(ModBlocks.SLOT_MACHINE.asItem());
                            entries.add(CASINO_TICKET);
                            entries.add(MEME_COIN);
                            entries.add(RK_PHONE);
                            entries.add(BANANA_ITEM);
                            entries.add(ANOMALY_SPAWNER);
                            entries.add(WORM_SPAWNER);
                            entries.add(MIMIC_SPAWNER);
                            entries.add(HAMSTER_SPAWNER);
                            entries.add(BEGGAR_SPAWNER);
                            entries.add(MONKEY_SPAWNER);
                        }).build());
    }

    private static Item reg(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier("memecraft", name), item);
    }
}
