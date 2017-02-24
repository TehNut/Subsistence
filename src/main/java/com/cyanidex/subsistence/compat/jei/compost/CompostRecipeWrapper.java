package com.cyanidex.subsistence.compat.jei.compost;

import com.cyanidex.subsistence.compat.jei.JEIPluginSubsistence;
import com.cyanidex.subsistence.core.recipe.RecipeCompost;
import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class CompostRecipeWrapper extends BlankRecipeWrapper {

    private final RecipeCompost recipe;

    public CompostRecipeWrapper(RecipeCompost recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> expandedInputs = JEIPluginSubsistence.helpers.getStackHelper().expandRecipeItemStackInputs(recipe.getValidItems());
        if (recipe.getCatalyst() != null)
            expandedInputs.add(Lists.newArrayList(recipe.getCatalyst()));

        ingredients.setInputLists(ItemStack.class, expandedInputs);
        ingredients.setOutput(ItemStack.class, recipe.getOutput());
        if (recipe.getCondensate() != null)
            ingredients.setOutput(FluidStack.class, recipe.getCondensate());
    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> ret = Lists.newArrayList();
        if (mouseX >= 40 && mouseX <= 60 && mouseY >= 21 && mouseY <= 34) { // TODO - Proper location
            ret.add(I18n.translateToLocalFormatted("jei.subsistence.recipe.compost.compostTime", recipe.getCompostTime()));
            if (recipe.getCondensate() != null)
                ret.add(I18n.translateToLocalFormatted("jei.subsistence.recipe.compost.condensateTime", recipe.getCondensateTime()));
            return ret;
        }
        return null;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        // TODO - Draw bin type
    }

    public boolean hasCatalyst() {
        return recipe.getCatalyst() != null;
    }
}
