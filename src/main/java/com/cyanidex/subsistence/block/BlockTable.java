package com.cyanidex.subsistence.block;

import com.cyanidex.subsistence.Subsistence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Locale;

public class BlockTable extends BlockMachine<BlockTable.TableType> {

    public BlockTable() {
        super(Material.WOOD, TableType.class);

        setUnlocalizedName(Subsistence.ID + ".table");
        setCreativeTab(Subsistence.TAB_SUB);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public Material getMaterial(IBlockState state) {
        return state.getValue(getProperty()) == TableType.WOOD ? super.getMaterial(state) : Material.ROCK;
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(getProperty()) == TableType.WOOD ? super.getSoundType(state, world, pos, entity) : SoundType.STONE;
    }

    public enum TableType implements IStringSerializable {
        WOOD,
        STONE,
        COBBLE,
        NETHER,
        ;

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
