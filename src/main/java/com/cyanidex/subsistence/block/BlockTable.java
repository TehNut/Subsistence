package com.cyanidex.subsistence.block;

import com.cyanidex.subsistence.Subsistence;
import com.cyanidex.subsistence.lib.block.BlockEnum;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public class BlockTable extends BlockEnum<BlockTable.TableType> {

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

    public enum TableType implements IStringSerializable {
        BASIC,
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
