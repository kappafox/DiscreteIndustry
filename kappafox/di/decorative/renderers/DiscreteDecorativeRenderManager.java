package kappafox.di.decorative.renderers;

import kappafox.di.decorative.DiscreteDecorative;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class DiscreteDecorativeRenderManager implements ISimpleBlockRenderingHandler
{

	private int renderID;
	private ISimpleBlockRenderingHandler stripRenderer;
	private ISimpleBlockRenderingHandler decorRenderer;
	
	public DiscreteDecorativeRenderManager(int rID_)
	{
		renderID = rID_;
		stripRenderer = new StripRenderer(rID_);
		decorRenderer = new DecorRenderer(rID_);
	}
	
	@Override
	public void renderInventoryBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_)
	{
		if(modelID_ == DiscreteDecorative.hazardRenderID)
		{
			stripRenderer.renderInventoryBlock(block_, meta_, modelID_, renderer_);
		}
		
		if(modelID_ == DiscreteDecorative.decorRenderID)
		{
			decorRenderer.renderInventoryBlock(block_, meta_, modelID_, renderer_);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_)
	{
		int meta = world_.getBlockMetadata(x_, y_, z_);
		
		if(modelID_ == DiscreteDecorative.hazardRenderID)
		{
			if(meta >= 0 && meta <= 5)
			{
				renderer_.renderStandardBlock(block_, x_, y_, z_);
				return true;
			}
			
			return stripRenderer.renderWorldBlock(world_, x_, y_, z_, block_, modelID_, renderer_);
		}
		
		if(modelID_ == DiscreteDecorative.decorRenderID)
		{
			return decorRenderer.renderWorldBlock(world_, x_, y_, z_, block_, modelID_, renderer_);	
		}
		
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return renderID;
	}

	
}
