package kappafox.di.decorative.blocks.items;

import kappafox.di.base.items.SubItem;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.tileentity.TileEntity;

public class SubItemDiscreteStairs implements SubItem 
{

	@Override
	public TileEntity getTileEntity(int type, int dir, int var1, int blockID, int blockMeta, int side, float hitx, float hity, float hitz) 
	{
		TileEntityDiscreteBlock tile = new TileEntityDiscreteBlock();
		
		tile.setFullColour(true);
		tile.setSubtype(type);
		tile.setDirection((short)dir);
		
		tile.setAllTexturesFromSource(blockID, type);
		tile.setOriginalID(blockID);
		
		//don't invert if looking at the ground
		if(hity > 0.5 && side != 1)
		{
			tile.setVariable(1);
		}
		
		return tile;
	}

}
