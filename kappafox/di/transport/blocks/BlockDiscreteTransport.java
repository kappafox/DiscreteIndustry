package kappafox.di.transport.blocks;

import com.google.common.collect.Range;

import net.minecraft.block.material.Material;
import kappafox.di.base.blocks.BaseBlockDiscreteSubtype;

public class BlockDiscreteTransport extends BaseBlockDiscreteSubtype
{

	
	public static final int ID_STORAGERACK_SINGLE = 100;
	public static final int ID_STORAGERACK_DUAL = 101;
	public static final int ID_STORAGERACK_QUAD = 102;
	
	public static final Range<Integer> RANGE_STORAGE_RACK = Range.closed(100, 120);
	
	public BlockDiscreteTransport(int id, Material mat, int renderID)
	{
		super(id, mat, renderID);
		this.registerSubBlocks();
	}
	
	private void registerSubBlocks( )
	{
		blocks.put(0, new SubBlockStorageRack());
	}
}
