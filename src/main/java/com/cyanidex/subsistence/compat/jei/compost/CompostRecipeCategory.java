package com.cyanidex.subsistence.compat.jei.compost;

import com.cyanidex.subsistence.Subsistence;
import com.cyanidex.subsistence.compat.jei.JEIPluginSubsistence;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class CompostRecipeCategory extends BlankRecipeCategory<CompostRecipeWrapper> {

    private static final int OUTPUT_SLOT = 0;
    private static final int CONDENSATE_SLOT = 1;
    private static final int CATALYST_SLOT = 2;
    private static final int INPUT_SLOT = 3;

    private final IDrawable background = JEIPluginSubsistence.helpers.getGuiHelper().createDrawable(new ResourceLocation(Subsistence.ID, "jei/compost.png"), 0, 0, 100, 40);
    private final String localizedName = I18n.translateToLocalFormatted("jei.subsistence.recipe.compost");

    @Override
    public String getUid() {
        return JEIPluginSubsistence.ID_COMPOST;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CompostRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiItemStacks.init(OUTPUT_SLOT, false, 73, 13);
        if (!ingredients.getOutputs(FluidStack.class).isEmpty())
            guiFluidStacks.init(CONDENSATE_SLOT, false, 0, 0, 16, 16, 1000, false, null);
        if (recipeWrapper.hasCatalyst())
            guiItemStacks.init(CATALYST_SLOT, true, 42, 0);
        guiItemStacks.init(INPUT_SLOT, true, 42, 13);

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        guiItemStacks.set(OUTPUT_SLOT, ingredients.getOutputs(ItemStack.class));
        if (!ingredients.getOutputs(FluidStack.class).isEmpty())
            guiFluidStacks.set(CONDENSATE_SLOT, ingredients.getOutputs(FluidStack.class));
        if (recipeWrapper.hasCatalyst())
            guiItemStacks.set(CATALYST_SLOT, inputs.get(inputs.size() - 1));
        guiItemStacks.set(INPUT_SLOT, inputs.get(0));
    }
}
