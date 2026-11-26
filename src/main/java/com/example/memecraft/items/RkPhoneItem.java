package com.example.memecraft.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RkPhoneItem extends Item {
    public RkPhoneItem(Settings s) { super(s); }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) com.example.memecraft.client.ClientHooks.openPhone();
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
