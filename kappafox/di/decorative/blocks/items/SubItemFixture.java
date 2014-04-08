package kappafox.di.decorative.blocks.items;

import kappafox.di.base.items.SubItem;
import kappafox.di.decorative.tileentities.TileEntityFixtureBlock;
import net.minecraft.tileentity.TileEntity;

public class SubItemFixture implements SubItem 
{

	@Override
	public TileEntity getTileEntity(int type_, int dir_, int var1_, int blockID_, int blockMeta_, int side_, float hitx_, float hity_) 
	{
		TileEntityFixtureBlock tile = new TileEntityFixtureBlock();
		
		tile.setFullColour(true);
		tile.setSubtype(type_);
		tile.setDirection((short)dir_);
		
		tile.setAllTexturesFromSource(blockID_, type_);
		tile.setOriginalID(blockID_);
		tile.setAllConnections(true);
		
		return tile;
	}
}
