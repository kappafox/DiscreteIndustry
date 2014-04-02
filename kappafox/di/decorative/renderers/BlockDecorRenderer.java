package kappafox.di.decorative.renderers;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import kappafox.di.base.BlockRenderManager;
import kappafox.di.base.SubBlockRenderer;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;

public class BlockDecorRenderer extends BlockRenderManager
{
	
	public BlockDecorRenderer( )
	{
		 sub = new HashMap<Integer, SubBlockRenderer>();
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) 
	{
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		int subType = -1;
		
		//All Decor Blocks are based on TileEntityDiscreteBlock
		if(tile != null && tile instanceof TileEntityDiscreteBlock)
		{
			
		}
		
		return false;
	}

	@Override
	public boolean renderInventoryBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_) 
	{
		return false;
	}
	
	
}
