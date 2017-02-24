package com.cyanidex.subsistence.core.json;

import com.cyanidex.subsistence.Subsistence;
import com.google.common.collect.Sets;
import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

public class Serializers {

    @Nonnull
    public static Gson getGson(Collection<SerializerBase<?>> serializers) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        gsonBuilder.serializeNulls();
        for (SerializerBase<?> serializer : serializers)
            gsonBuilder.registerTypeAdapter(serializer.getType(), serializer);
        return gsonBuilder.create();
    }

    @Nonnull
    public static Gson getGson(SerializerBase<?>... serializers) {
        return getGson(Sets.newHashSet(serializers));
    }

    @Nonnull
    public static Gson getStdGson(Collection<SerializerBase<?>> serializers) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        gsonBuilder.serializeNulls();
        for (SerializerBase<?> serializer : ALL_SERIALIZERS)
            gsonBuilder.registerTypeAdapter(serializer.getType(), serializer);
        for (SerializerBase<?> serializer : serializers)
            gsonBuilder.registerTypeAdapter(serializer.getType(), serializer);
        return gsonBuilder.create();
    }

    @Nonnull
    public static Gson getStdGson(SerializerBase<?>... serializers) {
        return getStdGson(Sets.newHashSet(serializers));
    }

    public static final SerializerBase<ResourceLocation> RESOURCELOCATION = new SerializerBase<ResourceLocation>() {
        @Override
        public ResourceLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String domain = JsonHelper.getString(json.getAsJsonObject(), "domain", "minecraft");
            String path = json.getAsJsonObject().get("path").getAsString();
            return new ResourceLocation(domain, path);
        }

        @Override
        public JsonElement serialize(ResourceLocation src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("domain", src.getResourceDomain());
            jsonObject.addProperty("path", src.getResourcePath());
            return jsonObject;
        }

        @Override
        public Type getType() {
            return ResourceLocation.class;
        }
    };
    public static final SerializerBase<ItemStack> ITEMSTACK = new SerializerBase<ItemStack>() {
        @Override
        public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation registryName = context.deserialize(json.getAsJsonObject().get("id"), ResourceLocation.class);
            int amount = JsonHelper.getInt(json.getAsJsonObject(), "amount", 1);
            int meta = JsonHelper.getInt(json.getAsJsonObject(), "meta", 0);
            ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(registryName), amount, meta);
            try {
                if (json.getAsJsonObject().has("nbt"))
                    stack.setTagCompound(JsonToNBT.getTagFromJson(json.getAsJsonObject().get("nbt").toString()));
            } catch (Exception e) {
                Subsistence.LOGGER.error("Error handling json for ItemStack containing {}", registryName);
            }
            return stack;
        }

        @Override
        public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("id", context.serialize(src.getItem().getRegistryName()));
            jsonObject.addProperty("amount", src.stackSize);
            jsonObject.addProperty("meta", src.getItemDamage());
            if (src.hasTagCompound())
                jsonObject.addProperty("nbt", src.getTagCompound().toString());
            return jsonObject;
        }

        @Override
        public Type getType() {
            return ItemStack.class;
        }
    };
    public static final SerializerBase<FluidStack> FLUID = new SerializerBase<FluidStack>() {
        @Override
        public FluidStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Fluid fluid = FluidRegistry.getFluid(json.getAsJsonObject().get("fluid").getAsString());
            int amount = JsonHelper.getInt(json.getAsJsonObject(), "amount", 1000);
            return new FluidStack(fluid, amount);
        }

        @Override
        public JsonElement serialize(FluidStack src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("fluid", src.getFluid().getName());
            jsonObject.addProperty("amount", src.amount);
            return jsonObject;
        }

        @Override
        public Type getType() {
            return FluidStack.class;
        }
    };
    private static Set<SerializerBase<?>> ALL_SERIALIZERS = Sets.<SerializerBase<?>>newHashSet(
            RESOURCELOCATION,
            ITEMSTACK,
            FLUID
    );
}
