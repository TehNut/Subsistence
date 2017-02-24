package com.cyanidex.subsistence.core.recipe;

import com.cyanidex.subsistence.core.json.SerializerBase;

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
