package com.cyanidex.subsistence.recipe;

import com.cyanidex.subsistence.json.SerializerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class RecipeCompost implements IRecipeType<RecipeCompost> {

    private final BinType binType;
    private final List<ItemStack> validItems;
    @Nullable
    private final ItemStack catalyst;
    private final ItemStack output;
    @Nullable
    private final FluidStack condensate;
    private final int compostTime;
    private final int condensateTime;

    public RecipeCompost(BinType binType, List<ItemStack> validItems, @Nullable ItemStack catalyst, ItemStack output, @Nullable FluidStack condensate, int compostTime, int condensateTime) {
        this.binType = binType;
        this.validItems = validItems;
        this.catalyst = catalyst;
        this.output = output;
        this.condensate = condensate;
        this.compostTime = compostTime;
        this.condensateTime = condensateTime;
    }

    public BinType getBinType() {
        return binType;
    }

    public List<ItemStack> getValidItems() {
        return validItems;
    }

    @Nullable
    public ItemStack getCatalyst() {
        return catalyst;
    }

    public ItemStack getOutput() {
        return output;
    }

    @Nullable
    public FluidStack getCondensate() {
        return condensate;
    }

    public int getCompostTime() {
        return compostTime;
    }

    public int getCondensateTime() {
        return condensateTime;
    }

    @Nonnull
    @Override
    public String getRecipeDirectory() {
        return "compost";
    }

    @Nonnull
    @Override
    public SerializerBase<?> getSerializer() {
        // TODO - this
        return null;
    }

    public enum BinType {
        WOOD,
        STONE,;
    }
}
