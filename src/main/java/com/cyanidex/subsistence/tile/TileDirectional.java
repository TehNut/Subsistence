package com.cyanidex.subsistence.tile;

import com.cyanidex.subsistence.lib.tile.TileBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileDirectional extends TileBase {

    private EnumFacing facing;

    public TileDirectional(EnumFacing facing) {
        this.facing = facing;
    }

    public TileDirectional() {
        this(EnumFacing.NORTH);
    }

    @Override
    public void deserialize(NBTTagCompound tagCompound) {
        facing = EnumFacing.valueOf(tagCompound.getString("facing"));
    }

    @Override
    public NBTTagCompound serialize(NBTTagCompound tagCompound) {
        tagCompound.setString("facing", facing.name());
        return super.serialize(tagCompound);
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }
}
