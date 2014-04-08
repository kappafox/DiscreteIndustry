package kappafox.di.decorative.renderers;
import kappafox.di.base.DiscreteRenderHelper;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.util.PixelSet;
import kappafox.di.base.util.TextureOffset;
import kappafox.di.decorative.tileentities.TileEntityFixtureBlock;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DecorRenderer implements ISimpleBlockRenderingHandler
{
	
	private static final PixelSet px = PixelSet.getInstance();	
	private static final DiscreteRenderHelper drh = new DiscreteRenderHelper();
	
	private int renderID;
	
	//included purely as a legacy helper while converting to the new px method, Always use px.<num> from now on!
	private static final double zeroPx = px.dzero;
	private static final double onePx = px.done;
	private static final double twoPx = px.dtwo;
	private static final double threePx = px.dthree;
	private static final double fourPx = px.dfour;
	private static final double fivePx = px.dfive;
	private static final double sixPx = px.dsix;
	private static final double sevenPx = px.dseven;
	private static final double eightPx = px.deight;
	private static final double ninePx = px.dnine;
	private static final double tenPx = px.dten;
	private static final double elevenPx = px.deleven;
	private static final double twelvePx = px.dtwelve;
	private static final double thirteenPx = px.dthirteen;
	private static final double fourteenPx = px.dfourteen;
	private static final double fifteenPx = px.dfifteen;
	private static final double sixteenPx = px.dsixteen;

	
	@SideOnly(Side.CLIENT)
	private static Tessellator tessellator;
	
	@SideOnly(Side.CLIENT)
	private static ResourceLocation flagSheet;
	
	@SideOnly(Side.CLIENT)
	private static TextureManager textureManager;
	
	
	public DecorRenderer(int rid_)
	{
		renderID = rid_;
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
		{
			tessellator = Tessellator.instance;
			flagSheet = new ResourceLocation("discreteindustry", "textures/blocks/flagPart.png");
			
			textureManager = Minecraft.getMinecraft().getTextureManager();
		}
	}

	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_)
	{
			
		switch(meta_)
		{
			//Fixture
			case 0:
				this.renderInventoryFixture(block_, meta_, modelID_, renderer_);
				break;
							
			case 861:
				this.renderInventoryDiscreteStairsBlock(block_, meta_, modelID_, renderer_);
				break;
				
			case 862:
				this.renderInventoryDiscreteSmallStairsBlock(block_, meta_, modelID_, renderer_);
				break;
				
			case 871:
				this.renderInventoryFixture(block_, meta_, modelID_, renderer_);
				break;
				
			case 872:
				this.renderInventoryFixture(block_, meta_, modelID_, renderer_);
				break;
				
			case 873:
				this.renderInventoryFixture(block_, meta_, modelID_, renderer_);
				break;
				
			default:
				//this.renderInventoryFixture(block_, meta_, modelID_, renderer_);
					
		}
	}

	private void renderInventoryDiscreteSmallStairsBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_) 
	{
		// TODO Auto-generated method stub
		tessellator.startDrawingQuads();
		
		float y1 = px.zero;
		float y2 = px.four;
		float z1 = px.zero;
		float z2 = px.sixteen;
		
		for(int i = 0; i < 4; i++)
		{
			renderer_.setRenderBounds(px.zero, y1, z1, px.sixteen, y2, z2);
			drh.tessellateInventoryBlock(renderer_, block_, meta_);
			
			y1 += px.four;
			y2 += px.four;		
			z2 -= px.four;
		}
		
		tessellator.draw();		
	}


	private void renderInventoryDiscreteStairsBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_) 
	{
		tessellator.startDrawingQuads();
		
		renderer_.setRenderBounds(zeroPx, zeroPx, zeroPx, sixteenPx, eightPx, sixteenPx);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		renderer_.setRenderBounds(0, eightPx, 0, sixteenPx, sixteenPx, eightPx);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		tessellator.draw();			
	}

	@Deprecated
	private void renderInventorySwordRestBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_) 
	{
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		drh.setGL11Scale(1.2);
		tessellator.startDrawingQuads();

		
		double x1 = twoPx;
		double x2 = fourteenPx;
		double y1 = zeroPx;
		double y2 = twoPx;
		double z1 = fourPx;
		double z2 = twelvePx;
		
		
		for(int i = 0; i < 3; i++)
		{
			renderer_.setRenderBounds(x1, y1, z1, x2, y2, z2);
			drh.tessellateInventoryBlock(renderer_, block_, meta_);		


			
			x1 += twoPx;
			y1 += twoPx;
			z1 += onePx;
			
			x2 -= twoPx;
			y2 += twoPx;
			z2 -= onePx;
			
			if(i == 1)
			{
				y2 -= onePx;
			}
		}
		
		tessellator.draw();
		drh.resetGL11Scale();

	}
	
	@Deprecated
	private void renderInventorySwordRackBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_) 
	{
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		drh.setGL11Scale(1.2);
		tessellator.startDrawingQuads();
		
		double yoffset = onePx;
		
		//baseplate
		renderer_.setRenderBounds(zeroPx, zeroPx + yoffset, fourPx, sixteenPx, onePx + yoffset, twelvePx);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
	
		double half = onePx / 2.0;
		
		
		//side struts
		renderer_.setRenderBounds(onePx, onePx + yoffset, sixPx, twoPx, thirteenPx + yoffset, tenPx);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		renderer_.setRenderBounds(fourteenPx, onePx + yoffset, sixPx, fifteenPx, thirteenPx + yoffset, tenPx);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		//rear support bars
		renderer_.setRenderBounds(twoPx, tenPx + half + yoffset, sixPx, fourteenPx, elevenPx + half + yoffset, sevenPx - half);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		renderer_.setRenderBounds(twoPx, twoPx + half + yoffset, sixPx, fourteenPx, threePx + half + yoffset, sevenPx - half);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		renderer_.setRenderBounds(twoPx, twoPx + half + yoffset, ninePx + half, fourteenPx, threePx + half + yoffset, tenPx);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		tessellator.draw();
		drh.resetGL11Scale();

	}


	private void renderInventoryFixture(Block block_, int meta_, int modelID_, RenderBlocks renderer_)
	{
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		
		double y1 = 0;
		double y2 = 0;
		double z1 = 0;
		double z2 = 0;
		
		switch(meta_)
		{
			case 871:
			{
				y1 = sevenPx;
				y2 = ninePx;
				break;
			}
			
			case 872:
			{
				y1 = sixPx;
				y2 = tenPx;
				break;
			}
			
			case 873:
			{
				y1 = fivePx;
				y2 = elevenPx;
				break;
			}
		}
		
        renderer_.setRenderBounds(zeroPx, y1, y1, sixteenPx, y2, y2);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
        renderer_.setRenderBounds(zeroPx, y1 - onePx, y1 - onePx, onePx, y2 + onePx, y2 + onePx);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
        renderer_.setRenderBounds(fifteenPx, y1 - onePx, y1 - onePx, sixteenPx, y2 + onePx, y2 + onePx);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		tessellator.draw();		
	}

	
	private static SubRendererWeaponRack newsys = new SubRendererWeaponRack();
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderWorldBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_)
	{		
		
		int meta = world_.getBlockMetadata(x_, y_, z_);
		TileEntity t = world_.getBlockTileEntity(x_, y_, z_);
		
		
		if(t instanceof TileEntityLoomBlock)
		{
			return renderWorldLoomBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
		}
		

		if(t instanceof TileEntitySwordRack)
		{
			return newsys.renderWorldBlock(world_, x_, y_, z_, block_, modelID_, renderer_);			
		}
		
		
		if(t instanceof TileEntityDiscreteBlock)
		{
			TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)t;
			int type = tile.getSubtype();
			
			switch(meta)
			{
				case 0:
					return renderWorldFixtureBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
				

					
				case 2:
				{
					if(tile != null)
					{
				
						if(type == 888)
						{
							return renderWorldTestBlock2(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
						}
					}
				}
				
				case 5:
				{
					if(type == 861)
					{
						return renderWorldDiscreteStairsBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
					}
					
					if(type == 862)
					{
						return renderWorldDiscreteSmallStairsBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
					}
				}
				
				case 6:
				{
					return renderWorldFixtureBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
				}
			}
		}
		

        return true;
	}

	private boolean renderWorldDiscreteSmallStairsBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta) 
	{

		
		//drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);

		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
		
		TileEntity n = world_.getBlockTileEntity(x_, y_, z_ - 1);
		TileEntity s = world_.getBlockTileEntity(x_, y_, z_ + 1);
		TileEntity e = world_.getBlockTileEntity(x_ + 1, y_, z_);
		TileEntity w = world_.getBlockTileEntity(x_ - 1, y_, z_);
		
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
		int subtype = tile.getSubtype();
		
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
		
		renderer_.setRenderBounds(zeroPx, y1, zeroPx, sixteenPx, y2, sixteenPx);
		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
		


		
		
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
					renderer_.setRenderBounds(px.zero, y3  + (i * px.four), min, px.sixteen, y4  + (i * px.four), max);
					drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					
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
					renderer_.setRenderBounds(px.zero, y3 + (i * px.four), min, px.sixteen, y4  + (i * px.four), max);
					drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					
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
					renderer_.setRenderBounds(min, y3  + (i * px.four), zeroPx, max, y4  + (i * px.four), sixteenPx);
					drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					
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
					renderer_.setRenderBounds(min, y3  + (i * px.four), zeroPx, max, y4  + (i * px.four), sixteenPx);
					drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					
					off.setOffsetU(2, off.getOffsetU(2) - 4);
					min += px.four;
				}
				break;
			}
			
		}

        return true;
	}


	private boolean renderWorldDiscreteStairsBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta_) 
	{

		
		//drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);

		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
		
		TileEntity n = world_.getBlockTileEntity(x_, y_, z_ - 1);
		TileEntity s = world_.getBlockTileEntity(x_, y_, z_ + 1);
		TileEntity e = world_.getBlockTileEntity(x_ + 1, y_, z_);
		TileEntity w = world_.getBlockTileEntity(x_ - 1, y_, z_);
		
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
		int subtype = tile.getSubtype();
		
		float y1 = (float)zeroPx; 
		float y2 = (float)eightPx;
		float y3 = (float)eightPx;
		float y4 = (float)sixteenPx;
		
		if(ori == 1)
		{
			float f = y1;
			
			y1 = y3;
			y3 = f;
			f = y2;
			y2 = y4;
			y4 = f;
		}
		
		renderer_.setRenderBounds(zeroPx, y1, zeroPx, sixteenPx, y2, sixteenPx);
		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
		
		switch(dir)
		{
			//-Z
			case 2:
			{

				TextureOffset off = new TextureOffset();
				off.setOffsetU(5, 8);	//Offsetting +X
				
				double x1 = zeroPx;
				double x2 = sixteenPx;
				boolean connected = false;
				
				if(north != null)
				{
					if(north.getSubtype() == subtype)
					{
						int dir2 = north.getDirection();
						
						if(dir2 == 4)
						{
							x2 = eightPx;
							connected = true;
						}
						
						if(dir2 == 5)
						{
							x1 = eightPx;
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
						renderer_.setRenderBounds(x1, y3, px.eight, px.eight, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					}
					
					if(dir3 == 5)
					{
						renderer_.setRenderBounds(px.eight, y3, px.eight, px.sixteen, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					}
					
					off.setOffsetU(5, 8);
				}
				
				renderer_.setRenderBounds(x1, y3, px.zero, x2, y4, px.eight);
				drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
				break;
			}
			
			//+Z
			case 3:
			{
				TextureOffset off = new TextureOffset();
				off.setOffsetU(5, -8);	//Offsetting -X

				double x1 = zeroPx;
				double x2 = sixteenPx;
				boolean connected = false;
				if(south != null)
				{
					if(south.getSubtype() == subtype)
					{
						int dir2 = south.getDirection();
						
						if(dir2 == 4)
						{
							connected = true;
							x2 = eightPx;
						}
						
						if(dir2 == 5)
						{
							connected = true;
							x1 = eightPx;
						}
					}
					
				}
				
				if(north != null  && connected == false && north.getSubtype() == subtype)
				{
					int dir3 = north.getDirection();
					off.setOffsetU(5, 8);
					if(dir3 == 4)
					{
						renderer_.setRenderBounds(px.zero, y3, px.zero, px.eight, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					}
					
					if(dir3 == 5)
					{
						renderer_.setRenderBounds(px.eight, y3, px.zero, px.sixteen, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					}
					
					off.setOffsetU(5, -8);
				}
				
				renderer_.setRenderBounds(x1, y3, eightPx, x2, y4, sixteenPx);
				drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
				break;
			}
			
			//-X
			case 4:
			{
				TextureOffset off = new TextureOffset();
				off.setOffsetU(2, 8);	//Offsetting -Z
				
				double z1 = zeroPx;
				double z2 = sixteenPx;
				boolean connected = false;
				
				if(west != null)
				{
					if(west.getSubtype() == subtype)
					{
						int dir2 = west.getDirection();
						
						if(dir2 == 2)
						{
							connected = true;
							z2 = eightPx;
						}
						
						if(dir2 == 3)
						{
							connected = true;
							z1 = eightPx;
						}
					}
					
				}
				
				if(east != null && connected == false && east.getSubtype() == subtype)
				{
					int dir3 = east.getDirection();
					off.setOffsetU(2, -8);
					if(dir3 == 2)
					{
						renderer_.setRenderBounds(px.eight, y3, px.zero, px.sixteen, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					}
					
					if(dir3 == 3)
					{
						renderer_.setRenderBounds(px.eight, y3, px.eight, px.sixteen, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					}
					
					off.setOffsetU(2, 8);
				}
				
				
				renderer_.setRenderBounds(zeroPx, y3, z1, eightPx, y4, z2);
				drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
				break;
			}
			
			//+X
			case 5:
			{
				TextureOffset off = new TextureOffset();
				off.setOffsetU(2, -8);	//Offsetting -Z
				
				double z1 = zeroPx;
				double z2 = sixteenPx;
				boolean connected = false;
				
				
				if(east != null)
				{
					if(east.getSubtype() == subtype)
					{
						int dir2 = east.getDirection();
						if(dir2 == 2)
						{
							connected = true;
							z2 = eightPx;
						}
						
						if(dir2 == 3)
						{
							connected = true;
							z1 = eightPx;
						}
					}
					
				}
				
				if(west != null && connected == false && west.getSubtype() == subtype)
				{
					off.setOffsetU(2, 8);
					int dir3 = west.getDirection();
					
					if(dir3 == 2)
					{
						renderer_.setRenderBounds(px.zero, y3, px.zero, px.eight, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					}
					
					if(dir3 == 3)
					{
						renderer_.setRenderBounds(px.zero, y3, px.eight, px.eight, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
					}
					off.setOffsetU(2, -8);
				}
				
				renderer_.setRenderBounds(eightPx, y3, z1, sixteenPx, y4, z2);
				drh.renderDiscreteQuadWithTextureOffsets(world_, renderer_, block_, x_, y_, z_, off);
				break;
			}
		}
		//drh.renderDiscreteQuadWithFlip(world_, renderer_, block_, x_, y_, z_, new boolean[]{false, false, false, false, false, false});
		

        return true;
	}


	@Deprecated
	private boolean renderWorldSwordRest(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta) 
	{	
		
		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
		
		//drh.recalculateTESRLighting(tile);
		int direction = tile.getDirection();
		boolean rotate = false;
		
		if(direction == 4 || direction == 5)
		{
			rotate = true;
		}
		
		double x1 = twoPx;
		double x2 = fourteenPx;
		double y1 = zeroPx;
		double y2 = twoPx;
		double z1 = fourPx;
		double z2 = twelvePx;
		
		
		for(int i = 0; i < 3; i++)
		{
			if(rotate == true)
			{
				renderer_.setRenderBounds(z1, y1, x1, z2, y2, x2);
				drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);				
			}
			else
			{
				renderer_.setRenderBounds(x1, y1, z1, x2, y2, z2);
				drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
			}
			
			x1 += twoPx;
			y1 += twoPx;
			z1 += onePx;
			
			x2 -= twoPx;
			y2 += twoPx;
			z2 -= onePx;
			
			if(i == 1)
			{
				y2 -= onePx;
			}
		}
		
		return false;
	}

	
	@Deprecated
	private boolean renderWorldSwordRack(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta) 
	{
		
		TileEntitySwordRack tile = (TileEntitySwordRack)world_.getBlockTileEntity(x_, y_, z_);
		
		if(tile != null)
		{
			drh.recalculateTESRLighting(tile);
			int dir = tile.getDirection();
			
			double[] c = {zeroPx, zeroPx, fourPx, sixteenPx, onePx, twelvePx};
			
			double half = onePx / 2.0;
			
			if(dir == 2 || dir == 3)
			{

				//baseplate
				renderer_.setRenderBounds(c[0], c[1], c[2], c[3], c[4], c[5]);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				
				
				//side struts
				renderer_.setRenderBounds(onePx, onePx, sixPx, twoPx, thirteenPx, tenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				renderer_.setRenderBounds(fourteenPx, onePx, sixPx, fifteenPx, thirteenPx, tenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				if(dir == 2)
				{
					//rear support bars
					renderer_.setRenderBounds(twoPx, tenPx + half, sixPx, fourteenPx, elevenPx + half, sevenPx - half);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(twoPx, twoPx + half, sixPx, fourteenPx, threePx + half, sevenPx - half);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(twoPx, twoPx + half, ninePx + half, fourteenPx, threePx + half, tenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				}
				else
				{
					//rear support bars
					renderer_.setRenderBounds(twoPx, tenPx + half, ninePx + half, fourteenPx, elevenPx + half, tenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(twoPx, twoPx + half, ninePx + half, fourteenPx, threePx + half, tenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					//front support bar
					renderer_.setRenderBounds(twoPx, twoPx + half, sixPx, fourteenPx, threePx + half, sevenPx - half);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);					
				}
			}
			else
			{
				drh.translationNorth(c);
				
				//baseplate
				renderer_.setRenderBounds(c[0], c[1], c[2], c[3], c[4], c[5]);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				
				
				//side struts
				renderer_.setRenderBounds(sixPx, onePx, onePx, tenPx, thirteenPx, twoPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				renderer_.setRenderBounds(sixPx, onePx, fourteenPx, tenPx, thirteenPx, fifteenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				if(dir == 4)
				{
					//rear support bars
					renderer_.setRenderBounds(sixPx, tenPx + half, twoPx, sevenPx - half, elevenPx + half, fourteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(sixPx, twoPx + half, twoPx, sevenPx - half, threePx + half, fourteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(ninePx + half, twoPx + half, twoPx, tenPx, threePx + half, fourteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				}
				else
				{
					//rear support bars
					renderer_.setRenderBounds(ninePx + half, tenPx + half, twoPx, tenPx, elevenPx + half, fourteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(ninePx + half, twoPx + half, twoPx, tenPx, threePx + half, fourteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					//front support bar
					renderer_.setRenderBounds(sixPx, twoPx + half, twoPx, sevenPx - half, threePx + half, fourteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);					
				}
			}	
		}
		
		return false;
	}


	private boolean renderWorldLoomBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta) 
	{
		renderer_.renderStandardBlock(block_, x_, y_, z_);
		return true;
	}

	private boolean renderWorldTestBlock2(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta) 
	{
		renderer_.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer_.renderStandardBlock(block_, x_, y_, z_);
		
		if(true == true)
		{
			return true;
		}
		
		tessellator.draw();

		this.bindTexture(TextureMap.locationItemsTexture);

		Item item = Item.swordDiamond;
		ItemStack istack = new ItemStack(item);
		Icon icon = item.getIcon(istack, 0);
		
		float offset = 1.0F;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, 0.0F);
		GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F);
		
		//tessellator.startDrawingQuads();
		/*
	    tessellator.addVertexWithUV(x_, y_, z_+ offset, 0, 1);
	    tessellator.addVertexWithUV(x_+offset, y_, z_+offset, 1, 1);
	    tessellator.addVertexWithUV(x_+offset, y_+offset, z_+offset, 1, 0);
	    tessellator.addVertexWithUV(x_, y_+offset, z_+offset, 0, 0);
	    */
	    
	    //drh.renderItemIn2D(tessellator, x_, y_, z_, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
	    //tessellator.draw();
    	GL11.glPopMatrix();
    	renderer_.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    	
		float angle = 5.0F;
	    for(int i = 5; i < 360; i += 10)
	    {    	
			GL11.glPushMatrix();
			GL11.glRotatef(i, 0.0F, 1.0F, 0.0F);
			//GL11.glTranslatef(2.0F, 0.0F, 9.0F);
			//Tessellator
			
			//drh.renderItemIn2D(tessellator, x_, y_, z_, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
		    GL11.glPopMatrix();
	    }
		/*
	    tessellator.addVertexWithUV(x_, y_, z_+offset, 0, 1);
	    tessellator.addVertexWithUV(x_+offset, y_, z_+offset, 1, 1);
	    tessellator.addVertexWithUV(x_+offset, y_+offset, z_+offset, 1, 0);
	    tessellator.addVertexWithUV(x_, y_+offset, z_+offset, 0, 0);
		*/

		/*
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(2F, 0.0F, 1.0F, 0.0F);
		
		
		tessellator.startDrawingQuads();
		
	    tessellator.addVertexWithUV(x_, y_, z_+offset, 0, 1);
	    tessellator.addVertexWithUV(x_+offset, y_, z_+offset, 1, 1);
	    tessellator.addVertexWithUV(x_+offset, y_+offset, z_+offset, 1, 0);
	    tessellator.addVertexWithUV(x_, y_+offset, z_+offset, 0, 0);
		
	    tessellator.draw();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(3F, 0.0F, 1.0F, 0.0F);
		
		tessellator.startDrawingQuads();
		
	    tessellator.addVertexWithUV(x_, y_, z_+1, 0, 1);
	    tessellator.addVertexWithUV(x_+1, y_, z_+1, 1, 1);
	    tessellator.addVertexWithUV(x_+1, y_+1, z_+1, 1, 0);
	    tessellator.addVertexWithUV(x_, y_+1, z_+1, 0, 0);
		
	    tessellator.draw();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(4F, 0.0F, 1.0F, 0.0F);
		
		tessellator.startDrawingQuads();
		
	    tessellator.addVertexWithUV(x_, y_, z_+1, 0, 1);
	    tessellator.addVertexWithUV(x_+1, y_, z_+1, 1, 1);
	    tessellator.addVertexWithUV(x_+1, y_+1, z_+1, 1, 0);
	    tessellator.addVertexWithUV(x_, y_+1, z_+1, 0, 0);
		
	    tessellator.draw();
		GL11.glPopMatrix();
		*/
		
		//renderer_.setRenderBounds(0.5, 0.5, 0.5, 0.6, 0.6, 0.6);
		//renderer_.renderStandardBlock(block_, x_, y_, z_);
		renderer_.clearOverrideBlockTexture();
		
		this.bindTexture(TextureMap.locationBlocksTexture);
		tessellator.startDrawingQuads();

		return true;
	}

	private boolean renderWorldTestBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta) 
	{
		
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		
		double res = Math.sin(System.currentTimeMillis());
		
		
		float r = 0.0F;
		float b = 0.0F;
		float g = 0.0F;
		
		int rows = 32;
		int cols = 60;
		
		double pixelWidth = onePx / 2.0;
		double pixelHeight = onePx / 2.0;
		
		if(block_ != null)
		{
			for(int i = 0; i <= rows; i++)
			{
				for(int j = 0; j <= cols; j++)
				{
					renderer_.setRenderBounds(j * pixelWidth, (i * pixelHeight), sevenPx, ((j + 1) * pixelWidth), ((i + 1) * pixelHeight), eightPx);
					//renderer_.setRenderBounds(j * onePx, (i * onePx), sevenPx, ((j + 1) * onePx), ((i + 1) * onePx), eightPx);
					renderer_.renderStandardBlockWithColorMultiplier(block_, x_, y_, z_, r, g, b);
					//renderer_.renderFaceZPos(block_, x_, y_, z_, block_.getIcon(0, 805));
					
					if(r == 0.0)
					{
						r = 1.0F;
						g = 1.0F;
						b = 1.0F;
					}
					else
					{
						r = 0.0F;
						g = 0.0F;
						b = 0.0F;
					}
					
				}
			}
			

		}
		return true;
	}
	
	private boolean renderWorldFixtureBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta_) 
	{		
		double ymin = sixPx;
		double ymax = tenPx;
		
		double start = zeroPx;
		double end = sixteenPx;
		
		double plugStart = fivePx;
		double plugEnd = elevenPx;
		
		double coreMin = ymin;
		double coreMax = ymax;
		
		double length = sixPx;
		
		boolean[] connections = new boolean[6];
		
		for(int i = 0; i < connections.length; i++)
		{
			connections[i] = true;
		}
		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
		
		//we need this legacy because of the old fixtures not using this tileentity
		if(tile != null && tile instanceof TileEntityFixtureBlock)
		{
			int type = tile.getSubtype();
			
			TileEntityFixtureBlock t2 = (TileEntityFixtureBlock)tile;
			
			switch(type)
			{
	
				case 871:	//2x2
				{
					ymin = sevenPx;
					ymax = ninePx;
					
					plugStart = sixPx;
					plugEnd = tenPx;
					
					coreMin = sevenPx;
					coreMax = ninePx;
					
					length = sevenPx;
					break;
				}
				
				case 873:	//6x6
				{
					ymin = fourPx;
					ymax = twelvePx;
					plugStart = threePx;
					plugEnd = thirteenPx;
					
					coreMin = fourPx;
					coreMax = twelvePx;
					
					length = fourPx;
					break;
				}
			}
			
			if(t2.getAllConnections().length > 0)
			{
				connections = t2.getAllConnections();
			}
		}
			
		boolean[] flips;

		renderer_.flipTexture = false;
		
		DiscreteRenderHelper drh = new DiscreteRenderHelper();
		
		int id = world_.getBlockId(x_, y_, z_);
		int meta = world_.getBlockMetadata(x_, y_, z_);
		
        //SOUTH Arm +Z 3
        if(this.shouldConnect(world_, x_, y_, z_ + 1) == true && connections[3] == true)
        {
        	if(!this.blockEquals(id, meta, world_.getBlockId(x_, y_, z_ + 1), world_.getBlockMetadata(x_, y_, z_ + 1)))
        	{
        		renderer_.setRenderBounds(plugStart, plugStart, fifteenPx, plugEnd , plugEnd, sixteenPx);
        		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
        	}

            renderer_.setRenderBounds(ymin, ymin, sixteenPx - length, ymax, ymax, end);
            drh.renderDiscreteQuadWithFlip(world_, renderer_, block_, x_, y_, z_, new boolean[]{false,true,false,false,false,false});
        }


        //NORTH Arm -Z 2
        if(this.shouldConnect(world_, x_, y_, z_ - 1) == true  && connections[2] == true)
        {
        	if(!this.blockEquals(id, meta, world_.getBlockId(x_, y_, z_ - 1), world_.getBlockMetadata(x_, y_, z_ - 1)))
        	{
        		renderer_.setRenderBounds(plugStart, plugStart, zeroPx, plugEnd , plugEnd, onePx);
        		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
        	}
        	//renderer_.uvRotateSouth = 1;
            renderer_.setRenderBounds(ymin, ymin, zeroPx, ymax, ymax, length);
            drh.renderDiscreteQuadWithFlip(world_, renderer_, block_, x_, y_, z_, new boolean[]{false,true,false,false,false,false});
            //renderer_.uvRotateSouth = 0;
        }
        
        
        //EAST + X 5
        if(this.shouldConnect(world_, x_ + 1, y_, z_) == true  && connections[5] == true)
        { 
        	if(!this.blockEquals(id, meta, world_.getBlockId(x_ + 1, y_, z_), world_.getBlockMetadata(x_ + 1, y_, z_)))
        	{
        		renderer_.setRenderBounds(fifteenPx, plugStart, plugStart, sixteenPx , plugEnd, plugEnd);
        		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
        	}
        	renderer_.setRenderBounds(sixteenPx - length, ymin, ymin, end, ymax, ymax);
        	drh.renderDiscreteQuadWithFlip(world_, renderer_, block_, x_, y_, z_, new boolean[]{false,false,false,false,true,false});

        }

        //WEST 4 - X
        if(this.shouldConnect(world_, x_ - 1, y_, z_) == true  && connections[4] == true)
        {
        	if(!this.blockEquals(id, meta, world_.getBlockId(x_ - 1, y_, z_), world_.getBlockMetadata(x_ - 1, y_, z_)))
        	{
        		renderer_.setRenderBounds(zeroPx, plugStart, plugStart, onePx , plugEnd, plugEnd);
        		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
        	}
            renderer_.setRenderBounds(start, ymin, ymin, length, ymax, ymax);
            //renderer_.renderStandardBlock(block_, x_, y_, z_);
            drh.renderDiscreteQuadWithFlip(world_, renderer_, block_, x_, y_, z_, new boolean[]{false,false,false,false,true,false});
        }
       
        //TOP 1
        if(this.shouldConnect(world_, x_, y_ + 1, z_) == true  && connections[1] == true)
        {
        	if(!this.blockEquals(id, meta, world_.getBlockId(x_, y_ + 1, z_), world_.getBlockMetadata(x_, y_ + 1, z_)))
        	{
        		renderer_.setRenderBounds(plugStart, fifteenPx, plugStart, plugEnd , sixteenPx, plugEnd);
        		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
        	}
            renderer_.setRenderBounds(ymin, sixteenPx - length, ymin, ymax, end, ymax);
            drh.renderDiscreteQuadWithFlip(world_, renderer_, block_, x_, y_, z_, new boolean[]{false,true,false,false,true,false});
        }
        
        //BOTTOM 0
        if(this.shouldConnect(world_, x_, y_ - 1, z_) == true  && connections[0] == true)
        {
        	if(!this.blockEquals(id, meta, world_.getBlockId(x_, y_ - 1, z_), world_.getBlockMetadata(x_, y_ - 1, z_)))
        	{
        		renderer_.setRenderBounds(plugStart, zeroPx, plugStart, plugEnd , onePx, plugEnd);
        		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
        	}
        	
            renderer_.setRenderBounds(ymin, start, ymin, ymax, length, ymax);
            drh.renderDiscreteQuadWithFlip(world_, renderer_, block_, x_, y_, z_, new boolean[]{false,true,false,false,true,false});
        }
        
        //core block
        renderer_.setRenderBounds(coreMin, coreMin, coreMin, coreMax, coreMax, coreMax);
        drh.renderDiscreteQuadWithFlip(world_, renderer_, block_, x_, y_, z_, new boolean[]{false,true,false,false,false,false});
        
        return true;
	}

	@Override
	public boolean shouldRender3DInInventory( )
	{
		return false;
	}

	@Override
	public int getRenderId( )
	{
		return renderID;
	}
	
    protected void bindTexture(ResourceLocation par1ResourceLocation)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().renderEngine;

        if (texturemanager != null)
        {
            texturemanager.bindTexture(par1ResourceLocation);
        }
    }
    
    private boolean shouldConnect(IBlockAccess world_, int x_, int y_, int z_)
    {
    	if(world_.isAirBlock(x_, y_, z_) == true)
    	{
    		return false;
    	}
    	
    	return true;
    }
    
    private boolean blockEquals(int ida_, int metaa_, int idb_, int metab_)
    {
    	if(ida_ == idb_ && metaa_ == metab_)
    	{
    		return true;
    	}
    	
    	return false;
    }
}
