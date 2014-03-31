package kappafox.di.transport.renderers;

import kappafox.di.transport.DiscreteTransport;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class DiscreteTransportRenderManager implements ISimpleBlockRenderingHandler
{

	private int renderID;
	private ISimpleBlockRenderingHandler ductRenderer;
	private ISimpleBlockRenderingHandler hopperRenderer;
	
	public DiscreteTransportRenderManager(int rID_)
	{
		//renderID = rID_;
		hopperRenderer = new HopperRenderer(rID_);
		//plugSocket = new PlugSocketRenderer(renderID);
	}
	
	@Override
	public void renderInventoryBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_)
	{
		
		if(modelID_ == DiscreteTransport.hopperRenderID)
		{
			hopperRenderer.renderInventoryBlock(block_, meta_, modelID_, renderer_);
		}
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_)
	{
		
		if(modelID_ == DiscreteTransport.hopperRenderID)
		{
			return hopperRenderer.renderWorldBlock(world_, x_, y_, z_, block_, modelID_, renderer_);
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
