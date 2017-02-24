package com.cyanidex.subsistence.core;

import com.cyanidex.subsistence.block.BlockTable;
import com.cyanidex.subsistence.lib.block.BlockEnum;
import com.cyanidex.subsistence.lib.block.ItemBlockEnum;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModObjects {

    public static final BlockEnum<BlockTable.TableType> TABLE = new BlockTable();

    public static void init() {
        GameRegistry.register(TABLE.setRegistryName("table"));
        GameRegistry.register(new ItemBlockEnum<BlockTable.TableType>(TABLE).setRegistryName("table"));
    }
}
