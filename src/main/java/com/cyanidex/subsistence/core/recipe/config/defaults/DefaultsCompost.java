package com.cyanidex.subsistence.core.recipe.config.defaults;

import com.cyanidex.subsistence.core.recipe.RecipeCompost;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class DefaultsCompost {

    public static List<RecipeCompost> getDefaults() {
        List<RecipeCompost> defaults = Lists.newArrayList();
        defaults.add(new RecipeCompost(RecipeCompost.BinType.WOOD, OreDictionary.getOres("treeLeaves"), null, new ItemStack(Blocks.DIRT), null, 100, 0));
        return defaults;
    }
}
