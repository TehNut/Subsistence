package com.cyanidex.subsistence.proxy;

import com.cyanidex.subsistence.Subsistence;
import com.cyanidex.subsistence.lib.util.IModeledItem;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        OBJLoader.INSTANCE.addDomain(Subsistence.ID);

        super.preInit(event);
    }

    @Override
    public void handleInventoryModel(Item item) {
        List<String> variants = Lists.newArrayList();
        ((IModeledItem) item).getVariants(variants);
        ResourceLocation stateLoc = new ResourceLocation(item.getRegistryName().getResourceDomain(), "item/" + item.getRegistryName().getResourcePath());

        if (item instanceof IModeledItem.Advanced) {
            for (String variant : variants)
                ModelLoader.registerItemVariants(item, new ModelResourceLocation(stateLoc, variant));

            ModelLoader.setCustomMeshDefinition(item, new MeshDefinitionWrapper((IModeledItem.Advanced) item));
            return;
        }

        for (int i = 0; i < variants.size(); i++)
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(stateLoc, variants.get(i)));
    }

    @Override
    public void handleInventoryModel(Block block) {
        List<String> variants = Lists.newArrayList();
        ((IModeledItem) block).getVariants(variants);

        if (block instanceof IModeledItem.Advanced) {
            for (String variant : variants)
                ModelLoader.registerItemVariants(Item.getItemFromBlock(block), new ModelResourceLocation(block.getRegistryName(), variant));

            ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), new MeshDefinitionWrapper((IModeledItem.Advanced) block));
            return;
        }

        for (int i = 0; i < variants.size(); i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(block.getRegistryName(), variants.get(i)));
    }

    public static class MeshDefinitionWrapper implements ItemMeshDefinition {

        private final IModeledItem.Advanced modeledItem;

        public MeshDefinitionWrapper(IModeledItem.Advanced modeledItem) {
            this.modeledItem = modeledItem;
        }

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack) {
            return modeledItem.getModelLocation(stack);
        }
    }
}
