package com.cyanidex.subsistence.core.recipe;

import com.cyanidex.subsistence.core.json.SerializerBase;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class RecipeMetalPress implements IRecipeType<RecipeMetalPress> {

    private final ItemStack input;
    private final ItemStack output;
    private final int pressCount;

    public RecipeMetalPress(ItemStack input, ItemStack output, int pressCount) {
        this.input = input;
        this.output = output;
        this.pressCount = pressCount;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getPressCount() {
        return pressCount;
    }

    @Nonnull
    @Override
    public String getRecipeDirectory() {
        return "metal_press";
    }

    @Nonnull
    @Override
    public SerializerBase<?> getSerializer() {
        // TODO - this
        return null;
    }
}
