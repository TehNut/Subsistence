package com.cyanidex.subsistence.proxy;

import com.cyanidex.subsistence.core.ModObjects;
import com.cyanidex.subsistence.core.recipe.config.MachineRecipes;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModObjects.init();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {
        MachineRecipes.initRecipes(false);
    }
}
