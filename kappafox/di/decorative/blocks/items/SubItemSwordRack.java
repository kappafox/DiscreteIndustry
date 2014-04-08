package kappafox.di.decorative.blocks.items;

import kappafox.di.base.items.SubItem;
import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import net.minecraft.tileentity.TileEntity;

public class SubItemSwordRack implements SubItem
{

	@Override
	public TileEntity getTileEntity(int type_, int dir_, int var1_, int blockID_, int blockMeta_, int side_, float hitx_, float hity_) 
	{
		TileEntitySwordRack tile = new TileEntitySwordRack(this.getInventorySlots(type_));
		
		tile.setFullColour(true);
		tile.setSubtype(type_);
		tile.setDirection((short)dir_);
		
		tile.setAllTexturesFromSource(blockID_, type_);
		tile.setOriginalID(blockID_);
		
		return tile;
	}
	
	private int getInventorySlots(int type_)
	{
    	switch(type_)
    	{
    		case 821:	//Sword Rest
    			return 1;
    		
    		case 822:	//Sword Rack
    			return 6;
    			
    		default:
    			return 0;
    			
    	}
	}

}
