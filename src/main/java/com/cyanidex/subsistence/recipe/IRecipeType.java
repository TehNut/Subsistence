package com.cyanidex.subsistence.recipe;

import com.cyanidex.subsistence.json.SerializerBase;

import javax.annotation.Nonnull;

public interface IRecipeType<T extends IRecipeType> {

    @Nonnull
    String getRecipeDirectory();

    @Nonnull
    SerializerBase<?> getSerializer();
}
