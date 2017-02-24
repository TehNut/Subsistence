package com.cyanidex.subsistence.lib.block;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockEnum<E extends Enum<E> & IStringSerializable> extends Block {

    private final E[] types;
    private final PropertyEnum<E> property;
    private final BlockStateContainer realStateContainer;

    public BlockEnum(Material material, Class<E> enumClass, String propName, Predicate<E> valueFilter) {
        super(material);

        this.types = enumClass.getEnumConstants();
        this.property = PropertyEnum.create(propName, enumClass, valueFilter);
        this.realStateContainer = createStateContainer();
        setDefaultState(getBlockState().getBaseState());
    }

    public BlockEnum(Material material, Class<E> enumClass, String propName) {
        this(material, enumClass, propName, Predicates.<E>alwaysTrue());
    }

    public BlockEnum(Material material, Class<E> enumClass) {
        this(material, enumClass, "type");
    }

    @Override
    protected final BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).build(); // Blank to avoid crashes
    }

    @Override
    public final BlockStateContainer getBlockState() {
        return realStateContainer;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(property, types[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(property).ordinal();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> subBlocks) {
        for (E type : types)
            subBlocks.add(new ItemStack(item, 1, type.ordinal()));
    }

    protected BlockStateContainer createStateContainer() {
        return new BlockStateContainer.Builder(this).add(property).build();
    }

    public E[] getTypes() {
        return types;
    }

    public PropertyEnum<E> getProperty() {
        return property;
    }

    public BlockStateContainer getRealStateContainer() {
        return realStateContainer;
    }
}
