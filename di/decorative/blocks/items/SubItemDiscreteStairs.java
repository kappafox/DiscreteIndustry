package kappafox.di.decorative.blocks.items;

import kappafox.di.base.items.SubItem;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.tileentity.TileEntity;

public class SubItemDiscreteStairs implements SubItem 
{

	@Override
	public TileEntity getTileEntity(int type_, int dir_, int var1_, int blockID_, int blockMeta_, int side_, float hitx_, float hity_) 
	{
		TileEntityDiscreteBlock tile = new TileEntityDiscreteBlock();
		
		tile.setFullColour(true);
		tile.setSubType(type_);
		tile.setDirection((short)dir_);
		
		tile.setAllTexturesFromSource(blockID_, type_);
		tile.setOriginalID(blockID_);
		
		//don't invert if looking at the ground
		if(hity_ > 0.5 && side_ != 1)
		{
			tile.setVariable(1);
		}
		
		return tile;
	}

}
