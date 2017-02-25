package com.cyanidex.subsistence.core;

import com.cyanidex.subsistence.Subsistence;
import com.cyanidex.subsistence.block.BlockComposter;
import com.cyanidex.subsistence.block.BlockTable;
import com.cyanidex.subsistence.lib.block.BlockEnum;
import com.cyanidex.subsistence.lib.util.RegistryHelper;
import com.cyanidex.subsistence.tile.TileDirectional;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModObjects {

    public static final BlockEnum<BlockTable.TableType> TABLE = new BlockTable();
    public static final BlockEnum<BlockComposter.ComposterType> COMPOSTER = new BlockComposter();

    public static void init() {
        RegistryHelper.register(TABLE, "table");
        RegistryHelper.register(COMPOSTER, "composter");

        GameRegistry.registerTileEntity(TileDirectional.class, Subsistence.ID + ":directional");
    }
}
