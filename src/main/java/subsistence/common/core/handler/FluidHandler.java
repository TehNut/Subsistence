package subsistence.common.core.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import subsistence.common.fluid.SubsistenceFluids;
import subsistence.common.item.ItemWoodenBucket;
import subsistence.common.item.SubsistenceItems;

/**
 * @author dmillerw
 */
public class FluidHandler {

    private static boolean canAccept(FluidStack fluidStack) {
        if (fluidStack.getFluid() == null)
            return false;

        if (fluidStack.getFluid() != FluidRegistry.WATER || fluidStack.getFluid() != SubsistenceFluids.boilingWaterFluid)
            return false;

        if (fluidStack.amount != 1000)
            return false;

        return true;
    }

    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event) {
        /*Block block = event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
        int meta = event.world.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ);
        if (block == SubsistenceBlocks.boilingWater && meta == 0) {
            if (event.current.getItem() == Items.bucket) {
                event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
                event.result = new ItemStack(SubsistenceItems.boilingBucket);
                event.setResult(Event.Result.ALLOW);
            }
        }*/
    }

    @SubscribeEvent
    public void onFluidRegistered(FluidRegistry.FluidRegisterEvent event) {
        if (canAccept(new FluidStack(event.fluidID, 1000)))
            return;

        Fluid fluid = FluidRegistry.getFluid(event.fluidName);
        ItemWoodenBucket item = new ItemWoodenBucket(fluid, "empty");
        item.setUnlocalizedName("wooden_bucket." + event.fluidName);

        FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(item), new ItemStack(SubsistenceItems.woodenBucket));

        GameRegistry.registerItem(item, "wooden_bucket." + event.fluidName);
    }
}