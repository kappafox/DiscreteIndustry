package kappafox.di.decorative.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.TextureOffset;
import kappafox.di.decorative.blocks.BlockDecor;

public class SubRendererStairs extends SubBlockRenderingHandler
{

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		TileEntityDiscreteBlock t = (TileEntityDiscreteBlock)world.getBlockTileEntity(x, y, z);
		int subtype = t.getSubtype();
		
		switch(subtype)
		{
			case BlockDecor.ID_STAIRS_NORMAL:
				return this.renderWorldDiscreteStairsBlock(world, x, y, z, block, t, renderer, subtype);
			
			case BlockDecor.ID_STAIRS_SMALL:
				return this.renderWorldDiscreteSmallStairsBlock(world, x, y, z, block, t, renderer, subtype);
		}
		
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		tessellator.startDrawingQuads();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		switch(subtype)
		{
			
			case BlockDecor.ID_STAIRS_NORMAL:
			{
				this.renderInventoryDiscreteStairs(block, subtype, modelID, renderer);
				break;
			}
			
			case BlockDecor.ID_STAIRS_SMALL:
			{
				this.renderInventorySmallDiscreteStairs(block, subtype, modelID, renderer);
				break;
			}
		}
		
		drh.resetGL11Scale();		
		tessellator.draw();	
	}

	private void renderInventoryDiscreteStairs(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		renderer.setRenderBounds(px.zero, px.zero, px.zero, px.sixteen, px.eight, px.sixteen);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(0, px.eight, 0, px.sixteen, px.sixteen, px.eight);
		drh.tessellateInventoryBlock(renderer, block, subtype);	
	}
	
	
	private void renderInventorySmallDiscreteStairs(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		float y1 = px.zero;
		float y2 = px.four;
		float z1 = px.zero;
		float z2 = px.sixteen;
		
		for(int i = 0; i < 4; i++)
		{
			renderer.setRenderBounds(px.zero, y1, z1, px.sixteen, y2, z2);
			drh.tessellateInventoryBlock(renderer, block, subtype);
			
			y1 += px.four;
			y2 += px.four;		
			z2 -= px.four;
		}
	}
	
	private boolean renderWorldDiscreteSmallStairsBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype)
	{
		TileEntity n = world.getBlockTileEntity(x, y, z - 1);
		TileEntity s = world.getBlockTileEntity(x, y, z + 1);
		TileEntity e = world.getBlockTileEntity(x + 1, y, z);
		TileEntity w = world.getBlockTileEntity(x - 1, y, z);
		
		TileEntityDiscreteBlock north = null;
		TileEntityDiscreteBlock south = null;
		TileEntityDiscreteBlock east = null;
		TileEntityDiscreteBlock west = null;
		
		
		if(n != null && n instanceof TileEntityDiscreteBlock)
		{
			north = (TileEntityDiscreteBlock)n;
		}
		
		if(s != null && s instanceof TileEntityDiscreteBlock)
		{
			south = (TileEntityDiscreteBlock)s;
		}
		
		if(e != null && e instanceof TileEntityDiscreteBlock)
		{
			east = (TileEntityDiscreteBlock)e;
		}
		
		if(w != null && w instanceof TileEntityDiscreteBlock)
		{
			west = (TileEntityDiscreteBlock)w;
		}
		
		
		int dir = tile.getDirection();
		int ori = tile.getVariable();
		
		float y1 = px.zero;
		float y2 = px.four;
		float y3 = px.four;
		float y4 = px.eight;
		
		float x1 = px.zero;
		float x2 = px.four;
		
		if(ori == 1)
		{
			float f = y1;
			
			y1 = y3;
			y3 = f;
			f = y2;
			y2 = y4;
			y4 = f;
		}
		
		renderer.setRenderBounds(px.zero, y1, px.zero, px.sixteen, y2, px.sixteen);
		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		


		
		
		switch(dir)
		{
			//-Z
			case 2:
			{
				int offset = 4;
				TextureOffset off = new TextureOffset();
				off.setOffsetU(5, offset);	//Offsetting +X
				
				float min = px.zero;
				float max = px.twelve;
				for(int i = 0; i < 3; i++)
				{
					renderer.setRenderBounds(px.zero, y3  + (i * px.four), min, px.sixteen, y4  + (i * px.four), max);
					drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					
					off.setOffsetU(5, off.getOffsetU(5) + 4);
					max -= px.four;
				}
				
				break;
			}
			
			//+Z
			case 3:
			{
				int offset = -4;
				TextureOffset off = new TextureOffset();
				off.setOffsetU(5, offset);	//Offsetting +X
				
				float min = px.four;
				float max = px.sixteen;
				for(int i = 0; i < 3; i++)
				{
					renderer.setRenderBounds(px.zero, y3 + (i * px.four), min, px.sixteen, y4  + (i * px.four), max);
					drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					
					off.setOffsetU(5, off.getOffsetU(5) - 4);
					min += px.four;
				}
				break;
			}
			
			//-X
			case 4:
			{
				
				int offset = 4;
				TextureOffset off = new TextureOffset();
				off.setOffsetU(2, offset);	//Offsetting +X
				
				float min = px.zero;
				float max = px.twelve;
				for(int i = 0; i < 3; i++)
				{
					renderer.setRenderBounds(min, y3  + (i * px.four), px.zero, max, y4  + (i * px.four), px.sixteen);
					drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					
					off.setOffsetU(2, off.getOffsetU(2) + 4);
					max -= px.four;
				}
				break;
			}
			
			//+X
			case 5:
			{
				int offset = -4;
				TextureOffset off = new TextureOffset();
				off.setOffsetU(2, offset);	//Offsetting +X
				
				float min = px.four;
				float max = px.sixteen;
				for(int i = 0; i < 3; i++)
				{
					renderer.setRenderBounds(min, y3  + (i * px.four), px.zero, max, y4  + (i * px.four), px.sixteen);
					drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					
					off.setOffsetU(2, off.getOffsetU(2) - 4);
					min += px.four;
				}
				break;
			}
			
		}

        return true;	
	}
	
	private boolean renderWorldDiscreteStairsBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype)
	{
		
		TileEntity n = world.getBlockTileEntity(x, y, z - 1);
		TileEntity s = world.getBlockTileEntity(x, y, z + 1);
		TileEntity e = world.getBlockTileEntity(x + 1, y, z);
		TileEntity w = world.getBlockTileEntity(x - 1, y, z);
		
		TileEntityDiscreteBlock north = null;
		TileEntityDiscreteBlock south = null;
		TileEntityDiscreteBlock east = null;
		TileEntityDiscreteBlock west = null;
		
		
		if(n != null && n instanceof TileEntityDiscreteBlock)
		{
			north = (TileEntityDiscreteBlock)n;
		}
		
		if(s != null && s instanceof TileEntityDiscreteBlock)
		{
			south = (TileEntityDiscreteBlock)s;
		}
		
		if(e != null && e instanceof TileEntityDiscreteBlock)
		{
			east = (TileEntityDiscreteBlock)e;
		}
		
		if(w != null && w instanceof TileEntityDiscreteBlock)
		{
			west = (TileEntityDiscreteBlock)w;
		}
		
		
		int dir = tile.getDirection();
		int ori = tile.getVariable();
		
		float y1 = px.zero;
		float y2 = px.eight;
		float y3 = px.eight;
		float y4 = px.sixteen;
		
		if(ori == 1)
		{
			float f = y1;
			
			y1 = y3;
			y3 = f;
			f = y2;
			y2 = y4;
			y4 = f;
		}
		
		renderer.setRenderBounds(px.zero, y1, px.zero, px.sixteen, y2, px.sixteen);
		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		
		switch(dir)
		{
			//-Z
			case 2:
			{

				TextureOffset off = new TextureOffset();
				off.setOffsetU(5, 8);	//Offsetting +X
				
				double x1 = px.zero;
				double x2 = px.sixteen;
				boolean connected = false;
				
				if(north != null)
				{
					if(north.getSubtype() == subtype)
					{
						int dir2 = north.getDirection();
						
						if(dir2 == 4)
						{
							x2 = px.eight;
							connected = true;
						}
						
						if(dir2 == 5)
						{
							x1 = px.eight;
							connected = true;
						}
					}				
				}
				
				if(south != null  && connected == false && south.getSubtype() == subtype)
				{
					int dir3 = south.getDirection();
					off.setOffsetU(5, -8);
					if(dir3 == 4)
					{
						renderer.setRenderBounds(x1, y3, px.eight, px.eight, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					if(dir3 == 5)
					{
						renderer.setRenderBounds(px.eight, y3, px.eight, px.sixteen, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					off.setOffsetU(5, 8);
				}
				
				renderer.setRenderBounds(x1, y3, px.zero, x2, y4, px.eight);
				drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
				break;
			}
			
			//+Z
			case 3:
			{
				TextureOffset off = new TextureOffset();
				off.setOffsetU(5, -8);	//Offsetting -X

				double x1 = px.zero;
				double x2 = px.sixteen;
				boolean connected = false;
				if(south != null)
				{
					if(south.getSubtype() == subtype)
					{
						int dir2 = south.getDirection();
						
						if(dir2 == 4)
						{
							connected = true;
							x2 = px.eight;
						}
						
						if(dir2 == 5)
						{
							connected = true;
							x1 = px.eight;
						}
					}
					
				}
				
				if(north != null  && connected == false && north.getSubtype() == subtype)
				{
					int dir3 = north.getDirection();
					off.setOffsetU(5, 8);
					if(dir3 == 4)
					{
						renderer.setRenderBounds(px.zero, y3, px.zero, px.eight, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					if(dir3 == 5)
					{
						renderer.setRenderBounds(px.eight, y3, px.zero, px.sixteen, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					off.setOffsetU(5, -8);
				}
				
				renderer.setRenderBounds(x1, y3, px.eight, x2, y4, px.sixteen);
				drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
				break;
			}
			
			//-X
			case 4:
			{
				TextureOffset off = new TextureOffset();
				off.setOffsetU(2, 8);	//Offsetting -Z
				
				double z1 = px.zero;
				double z2 = px.sixteen;
				boolean connected = false;
				
				if(west != null)
				{
					if(west.getSubtype() == subtype)
					{
						int dir2 = west.getDirection();
						
						if(dir2 == 2)
						{
							connected = true;
							z2 = px.eight;
						}
						
						if(dir2 == 3)
						{
							connected = true;
							z1 = px.eight;
						}
					}
					
				}
				
				if(east != null && connected == false && east.getSubtype() == subtype)
				{
					int dir3 = east.getDirection();
					off.setOffsetU(2, -8);
					if(dir3 == 2)
					{
						renderer.setRenderBounds(px.eight, y3, px.zero, px.sixteen, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					if(dir3 == 3)
					{
						renderer.setRenderBounds(px.eight, y3, px.eight, px.sixteen, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					off.setOffsetU(2, 8);
				}
				
				
				renderer.setRenderBounds(px.zero, y3, z1, px.eight, y4, z2);
				drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
				break;
			}
			
			//+X
			case 5:
			{
				TextureOffset off = new TextureOffset();
				off.setOffsetU(2, -8);	//Offsetting -Z
				
				double z1 = px.zero;
				double z2 = px.sixteen;
				boolean connected = false;
				
				
				if(east != null)
				{
					if(east.getSubtype() == subtype)
					{
						int dir2 = east.getDirection();
						if(dir2 == 2)
						{
							connected = true;
							z2 = px.eight;
						}
						
						if(dir2 == 3)
						{
							connected = true;
							z1 = px.eight;
						}
					}
					
				}
				
				if(west != null && connected == false && west.getSubtype() == subtype)
				{
					off.setOffsetU(2, 8);
					int dir3 = west.getDirection();
					
					if(dir3 == 2)
					{
						renderer.setRenderBounds(px.zero, y3, px.zero, px.eight, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					if(dir3 == 3)
					{
						renderer.setRenderBounds(px.zero, y3, px.eight, px.eight, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					off.setOffsetU(2, -8);
				}
				
				renderer.setRenderBounds(px.eight, y3, z1, px.sixteen, y4, z2);
				drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
				break;
			}
		}

        return true;
	}
}
