package com.cyanidex.subsistence.recipe;

import com.cyanidex.subsistence.json.SerializerBase;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class RecipeHammerMill implements IRecipeType<RecipeHammerMill> {

    private final List<ItemStack> stageItem;

    public RecipeHammerMill(List<ItemStack> stageItem) {
        this.stageItem = stageItem;
    }

    public List<ItemStack> getStageItem() {
        return stageItem;
    }

    @Nonnull
    @Override
    public String getRecipeDirectory() {
        return "hammer_mill";
    }

    @Nonnull
    @Override
    public SerializerBase<?> getSerializer() {
        // TODO - this
        return null;
    }
}
