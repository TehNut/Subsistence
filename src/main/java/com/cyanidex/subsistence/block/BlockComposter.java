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

public class BlockComposter extends BlockMachine<BlockComposter.ComposterType> {

    public BlockComposter() {
        super(Material.WOOD, ComposterType.class);

        setUnlocalizedName(Subsistence.ID + ".composter");
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
        return state.getValue(getProperty()) == ComposterType.WOOD ? super.getMaterial(state) : Material.ROCK;
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(getProperty()) == ComposterType.WOOD ? super.getSoundType(state, world, pos, entity) : SoundType.STONE;
    }

    public enum ComposterType implements IStringSerializable {
        WOOD,
        STONE,
        NETHER
        ;

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
