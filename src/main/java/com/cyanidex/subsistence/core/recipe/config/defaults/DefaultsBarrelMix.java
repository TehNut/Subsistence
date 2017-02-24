package com.cyanidex.subsistence.core.recipe.config.defaults;

import com.cyanidex.subsistence.core.recipe.RecipeBarrel;
import com.google.common.collect.Lists;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class DefaultsBarrelMix {

    public static List<RecipeBarrel.Mix> getDefaults() {
        List<RecipeBarrel.Mix> defaults = Lists.newArrayList();
        defaults.add(new RecipeBarrel.Mix(RecipeBarrel.Mix.BarrelType.WOOD, OreDictionary.getOres("sand"), new FluidStack(FluidRegistry.getFluid("water"), 1000), new ItemStack(Items.CLAY_BALL), 100));
        return defaults;
    }
}
