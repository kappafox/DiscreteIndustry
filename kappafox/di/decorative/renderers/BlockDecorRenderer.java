package kappafox.di.decorative.renderers;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import kappafox.di.base.BlockRenderManager;
import kappafox.di.base.SubBlockRenderer;

public class BlockDecorRenderer extends BlockRenderManager
{
	
	public BlockDecorRenderer( )
	{
		 sub = new HashMap<Integer, SubBlockRenderer>();
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) 
	{
		return false;
	}

	@Override
	public boolean renderInventoryBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_) 
	{
		return false;
	}
	
	
}
