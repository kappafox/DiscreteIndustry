package kappafox.di.decorative.blocks.items;

import kappafox.di.base.items.SubItem;
import kappafox.di.decorative.tileentities.TileEntityFixtureBlock;
import net.minecraft.tileentity.TileEntity;

public class SubItemFixture implements SubItem 
{

	@Override
	public TileEntity getTileEntity(int type, int dir, int var1, int blockID, int blockMeta, int side, float hitx, float hity, float hitz) 
	{
		TileEntityFixtureBlock tile = new TileEntityFixtureBlock();
		
		tile.setFullColour(true);
		tile.setSubtype(type);
		tile.setDirection((short)dir);
		
		tile.setAllTexturesFromSource(blockID, type);
		tile.setOriginalID(blockID);
		tile.setAllConnections(true);
		
		return tile;
	}
}
