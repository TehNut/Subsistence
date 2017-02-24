package com.cyanidex.subsistence.recipe;

import com.cyanidex.subsistence.json.SerializerBase;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.List;

public class RecipeBarrel {

    public static class Mix implements IRecipeType<Mix> {

        private final BarrelType barrelType;
        private final List<ItemStack> inputs;
        @Nullable
        private final FluidStack fluidInput;
        private final ItemStack output;
        private final int ticksRequired;

        public Mix(BarrelType barrelType, List<ItemStack> inputs, FluidStack fluidInput, ItemStack output, int ticksRequired) {
            this.barrelType = barrelType;
            this.inputs = inputs;
            this.fluidInput = fluidInput;
            this.output = output;
            this.ticksRequired = ticksRequired;
        }

        public BarrelType getBarrelType() {
            return barrelType;
        }

        public List<ItemStack> getInputs() {
            return inputs;
        }

        @Nullable
        public FluidStack getFluidInput() {
            return fluidInput;
        }

        public ItemStack getOutput() {
            return output;
        }

        public int getTicksRequired() {
            return ticksRequired;
        }

        @Nonnull
        @Override
        public String getRecipeDirectory() {
            return "barrel/mix";
        }

        @Nonnull
        @Override
        public SerializerBase<?> getSerializer() {
            return new SerializerBase<Mix>() {
                @Override
                public Mix deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    BarrelType barrelType = BarrelType.valueOf(json.getAsJsonObject().get("barrelType").getAsString());
                    List<ItemStack> inputs = context.deserialize(json.getAsJsonObject().get("inputs"), new TypeToken<List<ItemStack>>() {
                    }.getType());
                    FluidStack fluidInput = null;
                    if (json.getAsJsonObject().has("fluidInput"))
                        fluidInput = context.deserialize(json.getAsJsonObject().get("fluidInput").getAsJsonObject(), FluidStack.class);
                    ItemStack output = context.deserialize(json.getAsJsonObject().get("output"), ItemStack.class);
                    int ticksRequired = json.getAsJsonObject().get("ticksRequired").getAsInt();
                    return new Mix(barrelType, inputs, fluidInput, output, ticksRequired);
                }

                @Override
                public JsonElement serialize(Mix src, Type typeOfSrc, JsonSerializationContext context) {
                    JsonObject object = new JsonObject();
                    object.addProperty("barrelType", src.getBarrelType().name());
                    object.add("inputs", context.serialize(src.getInputs()));
                    object.add("fluidInput", context.serialize(src.getFluidInput()));
                    object.add("output", context.serialize(src.getOutput()));
                    object.addProperty("ticksRequired", src.getTicksRequired());
                    return super.serialize(src, typeOfSrc, context);
                }

                @Override
                public Type getType() {
                    return Mix.class;
                }
            };
        }

        public enum BarrelType {
            WOOD,
            STONE,;
        }
    }

    public static class Melt implements IRecipeType {

        private final List<ItemStack> inputs;
        @Nullable
        private final FluidStack fluidInput;
        private final FluidStack fluidOuput;

        public Melt(List<ItemStack> inputs, FluidStack fluidInput, FluidStack fluidOuput) {
            this.inputs = inputs;
            this.fluidInput = fluidInput;
            this.fluidOuput = fluidOuput;
        }

        public List<ItemStack> getInputs() {
            return inputs;
        }

        public FluidStack getFluidInput() {
            return fluidInput;
        }

        public FluidStack getFluidOuput() {
            return fluidOuput;
        }

        @Nonnull
        @Override
        public String getRecipeDirectory() {
            return "barrel/melt";
        }

        @Nonnull
        @Override
        public SerializerBase<?> getSerializer() {
            return new SerializerBase<Melt>() {
                @Override
                public Melt deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    // TODO - this
                    return super.deserialize(json, typeOfT, context);
                }

                @Override
                public JsonElement serialize(Melt src, Type typeOfSrc, JsonSerializationContext context) {
                    // TODO - this
                    return super.serialize(src, typeOfSrc, context);
                }

                @Override
                public Type getType() {
                    return Melt.class;
                }
            };
        }
    }
}
