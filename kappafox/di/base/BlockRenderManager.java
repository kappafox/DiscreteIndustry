package kappafox.di.base;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public abstract class BlockRenderManager 
{
	
	protected HashMap<Integer, SubBlockRenderer> sub;
	
	public abstract boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer);
	public abstract boolean renderInventoryBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_);
	
	public void registerHandler(int key, SubBlockRenderer handler)
	{
		if(!sub.containsKey(key))
		{
			sub.put(key, handler);
		}
	}
	
	public void registerHandlerRange(int keyStart, int keyEnd, SubBlockRenderer handler)
	{
		if(keyStart < keyEnd)
		{
			for(int i = keyStart; i <= keyEnd; i++)
			{
				if(!sub.containsKey(i))
				{
					sub.put(i, handler);
				}
			}
		}
	}
}
