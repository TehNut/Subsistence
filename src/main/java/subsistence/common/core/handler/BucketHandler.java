package subsistence.common.core.handler;

import subsistence.common.block.SubsistenceBlocks;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Thlayli
 */
public class BucketHandler {

    public static BucketHandler INSTANCE = new BucketHandler();

    public Map<Block, Item> buckets = new HashMap<Block, Item>();

    private BucketHandler() {

    }

    @SubscribeEvent
    public void replaceWater(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (event.entityPlayer.dimension == -1 && event.entityPlayer.getHeldItem().getItem() == Items.water_bucket) {
                switch (event.face) {

                }
                event.world.setBlock(event.x, event.y, event.z, SubsistenceBlocks.boilingWater);
            }
        }
    }

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {
        ItemStack result = fillCustomBucket(event.world, event.target);

        if (result == null)
            return;

        event.result = result;
        event.setResult(Event.Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
        Item bucket = buckets.get(block);

        if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
            world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
            return new ItemStack(bucket);
        } else
            return null;
    }
}