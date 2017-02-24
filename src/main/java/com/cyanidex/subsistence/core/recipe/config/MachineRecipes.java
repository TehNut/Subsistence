package com.cyanidex.subsistence.core.recipe.config;

import com.cyanidex.subsistence.Subsistence;
import com.cyanidex.subsistence.compat.jei.JEIPluginSubsistence;
import com.cyanidex.subsistence.core.recipe.config.defaults.*;
import com.cyanidex.subsistence.core.json.Serializers;
import com.cyanidex.subsistence.core.recipe.*;
import com.google.common.collect.*;
import com.google.gson.Gson;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public class MachineRecipes {

    public static final Map<Class<? extends IRecipeType>, String> RECIPE_TYPES;
    public static final ArrayListMultimap<Class<? extends IRecipeType>, IRecipeType> RECIPES = ArrayListMultimap.create();
    public static final ArrayListMultimap<Class<? extends IRecipeType>, IRecipeType> DEFAULTS = ArrayListMultimap.create();

    static {
        // Because ImmutableMap.of() doesn't want to cooperate
        Map<Class<? extends IRecipeType>, String> temp = Maps.newHashMap();
        temp.put(RecipeBarrel.Mix.class, "barrel/mix");
        temp.put(RecipeBarrel.Melt.class, "barrel/melt");
        temp.put(RecipeCompost.class, "compost");
        temp.put(RecipeHammerMill.class, "hammer_mill");
        temp.put(RecipeMetalPress.class, "metal_press");
        temp.put(RecipeSieve.class, "sieve");

        RECIPE_TYPES = ImmutableMap.copyOf(temp);

        DEFAULTS.putAll(RecipeBarrel.Mix.class, DefaultsBarrelMix.getDefaults());
        DEFAULTS.putAll(RecipeBarrel.Melt.class, DefaultsBarrelMelt.getDefaults());
        DEFAULTS.putAll(RecipeCompost.class, DefaultsCompost.getDefaults());
        DEFAULTS.putAll(RecipeHammerMill.class, DefaultsHammerMill.getDefaults());
        DEFAULTS.putAll(RecipeMetalPress.class, DefaultsMetalPress.getDefaults());
        DEFAULTS.putAll(RecipeSieve.class, DefaultsSieve.getDefaults());
    }

    public static void registerRecipe(Class<? extends IRecipeType> recipeType, IRecipeType recipe) {
        RECIPES.put(recipeType, recipe);
        if (Loader.isModLoaded("JEI"))
            JEIPluginSubsistence.addRecipe(recipe);
    }

    public static void removeRecipe(Class<? extends IRecipeType> recipeType, IRecipeType recipe) {
        RECIPES.get(recipeType).remove(recipe);
    }

    private static void clearRecipes() {
        for (Class<? extends IRecipeType> recipeType : RECIPE_TYPES.keySet()) {
            if (Loader.isModLoaded("JEI"))
                for (IRecipeType recipe : RECIPES.get(recipeType))
                    JEIPluginSubsistence.removeRecipe(recipe);

            RECIPES.get(recipeType).clear();
        }
    }

    public static void initRecipes(boolean reloaded) {
        Gson gson = Serializers.getStdGson();
        for (Map.Entry<Class<? extends IRecipeType>, String> recipeType : RECIPE_TYPES.entrySet()) {
            File recipeDir = new File(Subsistence.recipeRoot, recipeType.getValue());
            if (!reloaded) {
                if (!recipeDir.exists() && recipeDir.mkdirs()) {
                    try {
                        List<IRecipeType> defaultRecipes = DEFAULTS.get(recipeType.getKey());
                        int recipeNum = 0;
                        for (IRecipeType recipe : defaultRecipes) {
                            String json = gson.toJson(recipe, recipeType.getKey());
                            FileWriter writer = new FileWriter(new File(recipeDir, recipeType.getValue().replace("/", "_") + recipeNum++ + ".json"));
                            writer.write(json);
                            writer.close();
                        }
                    } catch (Exception e) {
                        // No default recipes to add or error writing defaults
                        e.printStackTrace();
                    }
                }
            }

            if (reloaded)
                clearRecipes();

            if (!recipeDir.exists())
                continue;

            File[] recipeFiles = recipeDir.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
            if (recipeFiles == null)
                continue;

            try {
                for (File recipeFile : recipeFiles) {
                    IRecipeType recipe = gson.fromJson(new FileReader(recipeFile), recipeType.getKey());
                    registerRecipe(recipeType.getKey(), recipe);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}