package com.cyanidex.subsistence.compat.jei;

import com.cyanidex.subsistence.Subsistence;
import com.cyanidex.subsistence.compat.jei.compost.CompostRecipeCategory;
import com.cyanidex.subsistence.compat.jei.compost.CompostRecipeHandler;
import com.cyanidex.subsistence.core.recipe.config.MachineRecipes;
import com.cyanidex.subsistence.core.recipe.RecipeCompost;
import mezz.jei.api.*;

@JEIPlugin
public class JEIPluginSubsistence extends BlankModPlugin {

    public static final String ID_COMPOST = Subsistence.ID + ":compost";

    public static IJeiHelpers helpers;
    public static IJeiRuntime runtime;

    @Override
    public void register(IModRegistry registry) {
        helpers = registry.getJeiHelpers();

        registry.addRecipeCategories(
                new CompostRecipeCategory()
        );

        registry.addRecipeHandlers(
                new CompostRecipeHandler()
        );

        registry.addRecipes(MachineRecipes.RECIPES.get(RecipeCompost.class));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }

    public static void removeRecipe(Object recipe) {
        if (runtime != null)
            runtime.getRecipeRegistry().removeRecipe(recipe);
    }

    public static void addRecipe(Object recipe) {
        if (runtime != null)
            runtime.getRecipeRegistry().addRecipe(recipe);
    }
}
