package kappafox.di.base.items;

import net.minecraft.tileentity.TileEntity;

public interface SubItem 
{	
	public abstract TileEntity getTileEntity(int type_, int dir_, int var1_, int blockID_, int blockMeta_, int side_, float hitx_, float hity_);
}
