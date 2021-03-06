package kappafox.di.decorative.blocks.items;

import net.minecraft.tileentity.TileEntity;
import kappafox.di.base.items.SubItem;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.decorative.tileentities.TileEntityFixtureBlock;

public class SubItemLadder implements SubItem
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

		return tile;
	}

}
