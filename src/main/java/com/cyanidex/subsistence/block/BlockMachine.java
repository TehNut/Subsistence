package com.cyanidex.subsistence.block;

import com.cyanidex.subsistence.lib.block.BlockEnum;
import com.cyanidex.subsistence.lib.util.IModeledItem;
import com.cyanidex.subsistence.tile.TileDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockMachine<E extends Enum<E> & IStringSerializable> extends BlockEnum<E> implements IModeledItem {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockMachine(Material material, Class<E> enumClass) {
        super(material, enumClass);
    }

    @Override
    protected BlockStateContainer createStateContainer() {
        return new BlockStateContainer.Builder(this).add(getProperty(), FACING).build();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileDirectional)
            return state.withProperty(FACING, ((TileDirectional) tile).getFacing());

        return super.getActualState(state, world, pos);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity toPlace = world.getTileEntity(pos);
        if (toPlace instanceof TileDirectional)
            ((TileDirectional) toPlace).setFacing(placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileDirectional();
    }

    // IModeledItem

    @Override
    public void getVariants(List<String> variants) {
        for (E value : getProperty().getAllowedValues())
            variants.add("facing=north," + getProperty().getName() + "=" + value.getName());
    }
}
