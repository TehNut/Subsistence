package com.cyanidex.subsistence;

import com.cyanidex.subsistence.command.CommandSubsistence;
import com.cyanidex.subsistence.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Subsistence.ID, name = Subsistence.NAME, version = Subsistence.VERSION)
public class Subsistence {

    public static final String ID = "subsistence";
    public static final String NAME = "Subsistence";
    public static final String VERSION = "@VERSION@";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final CreativeTabs TAB_SUB = new CreativeTabs("subsistence") {
        @Override
        public Item getTabIconItem() {
            return Items.COOKED_BEEF;
        }
    };

    @SidedProxy(clientSide = "com.cyanidex.subsistence.proxy.ClientProxy", serverSide = "com.cyanidex.subsistence.proxy.CommonProxy")
    public static CommonProxy PROXY;

    public static File configRoot;
    public static File recipeRoot;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configRoot = new File(event.getModConfigurationDirectory(), ID);
        recipeRoot = new File(configRoot, "recipe");

        PROXY.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PROXY.postInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSubsistence());
    }
}
