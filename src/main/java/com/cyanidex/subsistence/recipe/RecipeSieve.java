package com.cyanidex.subsistence.recipe;

import com.cyanidex.subsistence.json.SerializerBase;

import javax.annotation.Nonnull;

public class RecipeSieve implements IRecipeType<RecipeSieve> {

    @Nonnull
    @Override
    public String getRecipeDirectory() {
        return "sieve";
    }

    @Nonnull
    @Override
    public SerializerBase<?> getSerializer() {
        // TODO - this
        return null;
    }
}
