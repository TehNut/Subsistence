package com.cyanidex.subsistence.lib.item;

import com.cyanidex.subsistence.lib.util.IModeledItem;
import com.google.common.base.Function;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemEnum<T extends Enum<T> & IStringSerializable> extends Item {

    protected final T[] types;

    public ItemEnum(Class<T> enumClass, String baseName) {
        super();

        this.types = enumClass.getEnumConstants();

        setUnlocalizedName(baseName);
        setHasSubtypes(types.length > 1);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + getItemType(stack).getName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (T type : types)
            subItems.add(new ItemStack(this, 1, type.ordinal()));
    }

    public T getItemType(ItemStack stack) {
        return types[MathHelper.clamp(stack.getItemDamage(), 0, types.length)];
    }

    public static class Modeled<T extends Enum<T> & IStringSerializable> extends ItemEnum<T> implements IModeledItem {

        private final String variantName;

        public Modeled(Class<T> enumClass, String baseName, String variantName) {
            super(enumClass, baseName);

            this.variantName = variantName;
        }

        @Override
        public void getVariants(List<String> variants) {
            for (T type : types)
                variants.add(variantName + "=" + type.getName());
        }
    }

    public static class ModeledAdvanced<T extends Enum<T> & IStringSerializable> extends Modeled<T> implements IModeledItem.Advanced {

        private final Function<ItemStack, ModelResourceLocation> modelFunction;

        public ModeledAdvanced(Class<T> enumClass, String baseName, String variantName, Function<ItemStack, ModelResourceLocation> modelFunction) {
            super(enumClass, baseName, variantName);

            this.modelFunction = modelFunction;
        }

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack) {
            return modelFunction.apply(stack);
        }
    }
}