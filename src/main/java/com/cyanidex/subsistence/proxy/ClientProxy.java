package com.cyanidex.subsistence.proxy;

import com.cyanidex.subsistence.Subsistence;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        OBJLoader.INSTANCE.addDomain(Subsistence.ID);

        super.preInit(event);
    }
}
