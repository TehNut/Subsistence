package com.cyanidex.subsistence.compat.jei.compost;

import com.cyanidex.subsistence.compat.jei.JEIPluginSubsistence;
import com.cyanidex.subsistence.core.recipe.RecipeCompost;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CompostRecipeHandler implements IRecipeHandler<RecipeCompost> {

    @Override
    public Class<RecipeCompost> getRecipeClass() {
        return RecipeCompost.class;
    }

    @Deprecated
    @Override
    public String getRecipeCategoryUid() {
        return JEIPluginSubsistence.ID_COMPOST;
    }

    @Override
    public String getRecipeCategoryUid(RecipeCompost recipe) {
        return JEIPluginSubsistence.ID_COMPOST;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(RecipeCompost recipe) {
        return new CompostRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(RecipeCompost recipe) {
        return true;
    }
}
