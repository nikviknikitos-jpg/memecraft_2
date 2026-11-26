package com.example.memecraft.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static Block INDUS_MOTOR;
    public static Block RED_BUTTON_BOX;
    public static Block PINEAPPLE_BLOCK;
    public static Block SLOT_MACHINE;
    public static BlockEntityType<IndusMotorBlockEntity> INDUS_MOTOR_BE;

    public static void register() {
        INDUS_MOTOR = Registry.register(Registries.BLOCK, new Identifier("memecraft", "indus_motor"),
                new IndusMotorBlock(FabricBlockSettings.create().strength(3f).nonOpaque()));
        RED_BUTTON_BOX = Registry.register(Registries.BLOCK, new Identifier("memecraft", "red_button_box"),
                new RedButtonBoxBlock(FabricBlockSettings.create().strength(2f).nonOpaque()));
        PINEAPPLE_BLOCK = Registry.register(Registries.BLOCK, new Identifier("memecraft", "pineapple_block"),
                new PineappleBlock(FabricBlockSettings.create().strength(1f).nonOpaque()));
        SLOT_MACHINE = Registry.register(Registries.BLOCK, new Identifier("memecraft", "slot_machine"),
                new SlotMachineBlock(FabricBlockSettings.create().strength(3f).nonOpaque()));
        Registry.register(Registries.ITEM, new Identifier("memecraft", "indus_motor"),
                new BlockItem(INDUS_MOTOR, new Item.Settings()));
        Registry.register(Registries.ITEM, new Identifier("memecraft", "red_button_box"),
                new BlockItem(RED_BUTTON_BOX, new Item.Settings()));
        Registry.register(Registries.ITEM, new Identifier("memecraft", "pineapple_block"),
                new BlockItem(PINEAPPLE_BLOCK, new Item.Settings()));
        Registry.register(Registries.ITEM, new Identifier("memecraft", "slot_machine"),
                new BlockItem(SLOT_MACHINE, new Item.Settings()));
        INDUS_MOTOR_BE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier("memecraft", "indus_motor"),
                BlockEntityType.Builder.create(IndusMotorBlockEntity::new, INDUS_MOTOR).build(null));
    }
}
