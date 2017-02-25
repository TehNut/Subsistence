package com.cyanidex.subsistence.lib.util;

import com.cyanidex.subsistence.Subsistence;
import com.cyanidex.subsistence.lib.block.BlockEnum;
import com.cyanidex.subsistence.lib.block.ItemBlockEnum;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.common.registry.RegistryBuilder;

public class RegistryHelper {

    public static <T extends IForgeRegistryEntry<T>> FMLControlledNamespacedRegistry<T> newRegistry(Class<T> type, String id, int min, int max) {
        RegistryBuilder<T> registryBuilder = new RegistryBuilder<T>();
        registryBuilder.setType(type);
        registryBuilder.setName(new ResourceLocation(Loader.instance().activeModContainer().getModId(), id));
        registryBuilder.setIDRange(min, max);
        return (FMLControlledNamespacedRegistry<T>) registryBuilder.create();
    }

    @SuppressWarnings("unchecked")
    public static void register(Block block, String name) {
        GameRegistry.register(block.setRegistryName(name));
        if (block instanceof BlockEnum && !((BlockEnum) block).hasCustomItem())
            GameRegistry.register(new ItemBlockEnum((BlockEnum) block).setRegistryName(name));

        if (block instanceof IModeledItem)
            Subsistence.PROXY.handleInventoryModel(block);
    }

    public static void register(Item item, String name) {
        GameRegistry.register(item.setRegistryName(name));

        if (item instanceof IModeledItem)
            Subsistence.PROXY.handleInventoryModel(item);
    }
}
