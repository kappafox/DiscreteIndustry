package kappafox.di.decorative.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import kappafox.di.base.SubBlockRenderer;

public class SubRendererLadder extends SubBlockRenderer
{

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean renderInventoryBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
