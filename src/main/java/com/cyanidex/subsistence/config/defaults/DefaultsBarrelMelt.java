package com.cyanidex.subsistence.config.defaults;

import com.cyanidex.subsistence.recipe.RecipeBarrel;
import com.google.common.collect.Lists;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class DefaultsBarrelMelt {

    public static List<RecipeBarrel.Melt> getDefaults() {
        List<RecipeBarrel.Melt> defaults = Lists.newArrayList();
        defaults.add(new RecipeBarrel.Melt(OreDictionary.getOres("cobblestone"), null, new FluidStack(FluidRegistry.getFluid("lava"), 1000)));
        return defaults;
    }
}
