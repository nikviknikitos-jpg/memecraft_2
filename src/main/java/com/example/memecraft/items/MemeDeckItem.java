package com.example.memecraft.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MemeDeckItem extends Item {
    public MemeDeckItem(Settings s) { super(s); }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) com.example.memecraft.client.ClientHooks.openDeck();
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
