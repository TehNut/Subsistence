package com.cyanidex.subsistence.core.recipe;

import com.cyanidex.subsistence.core.json.SerializerBase;

import javax.annotation.Nonnull;

public interface IRecipeType<T extends IRecipeType> {

    @Nonnull
    String getRecipeDirectory();

    @Nonnull
    SerializerBase<?> getSerializer();
}
