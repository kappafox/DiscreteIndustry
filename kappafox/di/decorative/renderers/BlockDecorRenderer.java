package kappafox.di.decorative.renderers;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import kappafox.di.base.BlockRenderingHandler;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.decorative.blocks.BlockDecor;

public class BlockDecorRenderer extends BlockRenderingHandler
{
	private static SubRendererLadder SUB_RENDERER_LADDER;
	
	public BlockDecorRenderer( )
	{
		 sub = new HashMap<Integer, SubBlockRenderingHandler>();
		 
		 SUB_RENDERER_LADDER = new SubRendererLadder();
		 
		 this.mapSubBlockRenderers();
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) 
	{
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		//All Decor Block are required to be based on TileEntitySubtype
		if(tile != null && tile instanceof TileEntitySubtype)
		{
			TileEntitySubtype te = (TileEntitySubtype)tile;
			if(sub.containsKey(te.getSubtype()))
			{
				return sub.get(te.getSubtype()).renderWorldBlock(world, x, y, z, block, modelID, renderer);
			}
		}
		
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer) 
	{
		if(sub.containsKey(subtype))
		{
			sub.get(subtype).renderInventoryBlock(block, subtype, modelID, renderer);
		}
	}
	
	private void mapSubBlockRenderers( )
	{	
		super.registerHandlerRange(BlockDecor.RANGE_LADDER.lowerEndpoint(), BlockDecor.RANGE_LADDER.upperEndpoint(), SUB_RENDERER_LADDER);
	}
	
	
}
