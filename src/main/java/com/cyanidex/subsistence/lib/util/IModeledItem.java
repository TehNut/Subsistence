package com.cyanidex.subsistence.lib.util;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IModeledItem {

    /**
     * Used to register inventory models for blocks and items.
     *
     * The index a variant is at determines the meta the variant will be used for. Loaded from a blockstate file using
     * the object's registry name.
     *
     * @param variants - List of variants
     */
    void getVariants(List<String> variants);

    interface Advanced extends IModeledItem {

        ModelResourceLocation getModelLocation(ItemStack stack);
    }
}
