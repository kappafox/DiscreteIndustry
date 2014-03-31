package kappafox.di.decorative.renderers;
import kappafox.di.base.DiscreteRenderHelper;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.util.PixelSet;
import kappafox.di.base.util.TextureOffsets;
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
	
	private int renderID;
	//included purely as a legacy helper, Always use px.<num> from now on!
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
	
	private static DiscreteRenderHelper drh = new DiscreteRenderHelper();
	
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
			
			case 821:
				this.renderInventorySwordRestBlock(block_, meta_, modelID_, renderer_);
				break;
				
			//6 Sword Rack
			case 822:
				this.renderInventorySwordRackBlock(block_, meta_, modelID_, renderer_);
				break;
				
				
			//Foothold Ladder
			case 800:
				this.renderInventoryFootholdLadderBlock(block_, meta_, modelID_, renderer_);
				break;
				
			//Pole Ladder
			case 801:
				this.renderInventoryPoleLadderBlock(block_, meta_, modelID_, renderer_);
				break;
				
			//Simple Ladder
			case 802:
				this.renderInventorySimpleLadderBlock(block_, meta_, modelID_, renderer_);
				break;
			
			//Rope Ladder
			case 803:
				this.renderInventoryRopeLadderBlock(block_, meta_, modelID_, renderer_);
				break;
				
			//Rope Ladder
			case 804:
				this.renderInventoryFixedPlankLadderBlock(block_, meta_, modelID_, renderer_);
				break;
				
			//Classic Ladder
			case 806:
				this.renderInventoryClassicLadderBlock(block_, meta_, modelID_, renderer_);
				break;
				
			//Industrial Ladder
			case 807:
				this.renderInventoryIndustrialLadderBlock(block_, meta_, modelID_, renderer_);
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


	private void renderInventoryFixedPlankLadderBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_) 
	{
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		drh.setGL11Scale(1.3);
		tessellator.startDrawingQuads();
		
		double ymin = twoPx;
		double ymax = sixPx;
		double inc = eightPx;
		
		for(int i = 0; i < 2; i++)
		{
			renderer_.setRenderBounds(onePx, ymin, sevenPx, fifteenPx, ymax, eightPx);
			drh.tessellateInventoryBlock(renderer_, block_, meta_);
			
			ymin += inc;
			ymax += inc;
		}
		
		tessellator.draw();
		drh.resetGL11Scale();
	}


	private void renderInventoryRopeLadderBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_) 
	{
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		drh.setGL11Scale(1.3);
		tessellator.startDrawingQuads();
		
		renderer_.setOverrideBlockTexture(block_.getIcon(3, 2));
		renderer_.setRenderBounds(sixPx, fifteenPx, sixPx, tenPx, sixteenPx, ninePx);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		renderer_.clearOverrideBlockTexture();
		
		renderer_.setRenderBounds(sevenPx, zeroPx, sevenPx, ninePx, fifteenPx, eightPx);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		tessellator.draw();
		drh.resetGL11Scale();
	}


	private void renderInventorySimpleLadderBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_) 
	{
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		drh.setGL11Scale(1.3);
		tessellator.startDrawingQuads();
		
		double ymin = threePx;
		double ymax = fourPx;
		double inc = eightPx;
		
		double zmin = sevenPx;
		double zmax = eightPx;


		renderer_.setRenderBounds(twoPx, zeroPx, zmin, threePx, sixteenPx, zmax);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		renderer_.setRenderBounds(thirteenPx, zeroPx, zmin, fourteenPx, sixteenPx, zmax);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		for(int i = 0; i < 2; i++)
		{
			renderer_.setRenderBounds(threePx, ymin, zmin, thirteenPx, ymax, zmax);
			drh.tessellateInventoryBlock(renderer_, block_, meta_);
			
			ymin += inc;
			ymax += inc;
		}
		
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
	
	private void renderInventoryPoleLadderBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_) 
	{
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		drh.setGL11Scale(1.3);
		
		tessellator.startDrawingQuads();
		
		renderer_.setRenderBounds(sevenPx, zeroPx, sevenPx, ninePx, sixteenPx, ninePx);
		drh.tessellateInventoryBlock(renderer_, block_, meta_);
		
		double ymin = onePx;
		double ymax = twoPx;
		double inc = fourPx;
		
		double xmin = twoPx;
		double xmax = sevenPx;
		
		
		for(int i = 0; i < 4; i++)
		{
			
			if(i % 2 == 0)
			{
				xmin = ninePx;
				xmax = fifteenPx;
				

				renderer_.setRenderBounds((xmax - onePx), ymax, sevenPx, xmax, (ymax + onePx), eightPx);
				drh.tessellateInventoryBlock(renderer_, block_, meta_);
			}
			else
			{
				xmin = onePx;
				xmax = sevenPx;
				
				renderer_.setRenderBounds(xmin, ymax, sevenPx, (xmin + onePx), (ymax + onePx), eightPx);
				drh.tessellateInventoryBlock(renderer_, block_, meta_);

			}
			
			renderer_.setRenderBounds(xmin, ymin, sevenPx, xmax, ymax, eightPx);
			drh.tessellateInventoryBlock(renderer_, block_, meta_);
			
			ymin += inc;
			ymax += inc;
		}
		
		tessellator.draw();	
		drh.resetGL11Scale();
		//this.renderInventoryClassicLadderBlock(block_, meta_, modelID_, renderer_);
	}
	
	private void renderInventoryFootholdLadderBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_) 
	{
		//renderer_.renderBlockAsItem(block_, meta_, 1.0F);
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		GL11.glScaled(1.5, 1.5, 1.5);
		tessellator.startDrawingQuads();
		
		double ymin = onePx;
		double ymax = threePx;
		double inc = fourPx;
		
		double xmin = twoPx;
		double xmax = sevenPx;
		
		
			for(int i = 0; i < 4; i++)
			{
				if(i % 2 == 0)
				{
					xmin = twoPx;
					xmax = sevenPx;					
				}
				else
				{
					xmin = ninePx;
					xmax = fourteenPx;
				}
				
				renderer_.setRenderBounds(xmin, ymin, sevenPx, xmax, ymax, eightPx);
				drh.tessellateInventoryBlock(renderer_, block_, meta_);
				
				renderer_.setRenderBounds(xmin, ymin, sixPx, (xmin + onePx), ymax, sevenPx);
				drh.tessellateInventoryBlock(renderer_, block_, meta_);
				
				
				renderer_.setRenderBounds((xmax - onePx), ymin, sixPx, xmax, ymax, sevenPx);
				drh.tessellateInventoryBlock(renderer_, block_, meta_);
				
				ymin += inc;
				ymax += inc;
			}
		
		tessellator.draw();
		GL11.glScaled(1.0, 1.0, 1.0);
		
	}


	private void renderInventoryIndustrialLadderBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_)
	{
		GL11.glScaled(1.3, 1.3, 1.3);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		
		double ymin = onePx;
		double ymax = threePx;
		double inc = fourPx;
		
			for(int i = 0; i < 4; i++)
			{
				renderer_.setRenderBounds(twoPx, ymin, sevenPx, fourteenPx, ymax, eightPx);
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				renderer_.renderFaceYNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderer_.renderFaceYPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				renderer_.renderFaceZNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderer_.renderFaceZPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				renderer_.renderFaceXNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderer_.renderFaceXPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				
				renderer_.setRenderBounds(twoPx, ymin, sixPx, threePx, ymax, sevenPx);
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				renderer_.renderFaceYNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderer_.renderFaceYPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				renderer_.renderFaceZNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderer_.renderFaceZPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				renderer_.renderFaceXNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderer_.renderFaceXPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				
				
				renderer_.setRenderBounds(thirteenPx, ymin, sixPx, fourteenPx, ymax, sevenPx);
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				renderer_.renderFaceYNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderer_.renderFaceYPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				renderer_.renderFaceZNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderer_.renderFaceZPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				renderer_.renderFaceXNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderer_.renderFaceXPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				
				ymin += inc;
				ymax += inc;
			}
		
		tessellator.draw();
		GL11.glScaled(1.0, 1.0, 1.0);
	}
	
	private void renderInventoryClassicLadderBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_)
	{
		GL11.glScaled(1.3, 1.3, 1.3);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		
		double ymin = onePx;
		double ymax = threePx;
		double inc = fourPx;
			
			renderer_.setRenderBounds(twoPx, zeroPx, sixPx, threePx, sixteenPx, ninePx);
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer_.renderFaceYNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer_.renderFaceYPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer_.renderFaceZNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer_.renderFaceZPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer_.renderFaceXNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer_.renderFaceXPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
			
			
			renderer_.setRenderBounds(thirteenPx, zeroPx, sixPx, fourteenPx, sixteenPx, ninePx);
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer_.renderFaceYNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer_.renderFaceYPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer_.renderFaceZNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer_.renderFaceZPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer_.renderFaceXNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer_.renderFaceXPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
		
			for(int i = 0; i < 4; i++)
			{
				renderer_.setRenderBounds(twoPx, ymin, sevenPx, fourteenPx, ymax, eightPx);
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				renderer_.renderFaceYNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderer_.renderFaceYPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				renderer_.renderFaceZNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderer_.renderFaceZPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				renderer_.renderFaceXNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderer_.renderFaceXPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
				

				
				ymin += inc;
				ymax += inc;
			}
		
		tessellator.draw();
		GL11.glScaled(1.0, 1.0, 1.0);
	}

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
			TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)t;
			int type = tile.getSubType();
			
			switch(type)
			{
				//Single Sword Rest
				case 821:
					return renderWorldSwordRest(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
				
				//6 Sword Rack
				case 822:
					return renderWorldSwordRack(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);				
			}
			
			
					
		}
		
		
		if(t instanceof TileEntityDiscreteBlock)
		{
			TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)t;
			int type = tile.getSubType();
			
			switch(meta)
			{
				case 0:
					return renderWorldFixtureBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
				

					
				case 2:
				{
					if(tile != null)
					{
						if(type == 800)
						{
							return renderWorldFootholdLadderBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
						}
						
						if(type == 801)
						{
							return renderWorldPoleLadderBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
						}
						
						if(type == 802)
						{
							return renderWorldSimpleLadderBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
						}
						
						if(type == 803)
						{
							return renderWorldRopeLadderBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
						}
						
						if(type == 804)
						{
							return renderWorldFixedPlankLadderBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
						}
						
						if(type == 805)
						{
							return renderWorldTestBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
						}
						
						if(type == 806)
						{
							return renderWorldClassicLadderBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
						}
						
						if(type == 807)
						{
							return renderWorldIndustrialLadderBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
						}
					
						
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
		int subtype = tile.getSubType();
		
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
				TextureOffsets off = new TextureOffsets();
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
				TextureOffsets off = new TextureOffsets();
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
				TextureOffsets off = new TextureOffsets();
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
				TextureOffsets off = new TextureOffsets();
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
		int subtype = tile.getSubType();
		
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

				TextureOffsets off = new TextureOffsets();
				off.setOffsetU(5, 8);	//Offsetting +X
				
				double x1 = zeroPx;
				double x2 = sixteenPx;
				boolean connected = false;
				
				if(north != null)
				{
					if(north.getSubType() == subtype)
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
				
				if(south != null  && connected == false && south.getSubType() == subtype)
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
				TextureOffsets off = new TextureOffsets();
				off.setOffsetU(5, -8);	//Offsetting -X

				double x1 = zeroPx;
				double x2 = sixteenPx;
				boolean connected = false;
				if(south != null)
				{
					if(south.getSubType() == subtype)
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
				
				if(north != null  && connected == false && north.getSubType() == subtype)
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
				TextureOffsets off = new TextureOffsets();
				off.setOffsetU(2, 8);	//Offsetting -Z
				
				double z1 = zeroPx;
				double z2 = sixteenPx;
				boolean connected = false;
				
				if(west != null)
				{
					if(west.getSubType() == subtype)
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
				
				if(east != null && connected == false && east.getSubType() == subtype)
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
				TextureOffsets off = new TextureOffsets();
				off.setOffsetU(2, -8);	//Offsetting -Z
				
				double z1 = zeroPx;
				double z2 = sixteenPx;
				boolean connected = false;
				
				
				if(east != null)
				{
					if(east.getSubType() == subtype)
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
				
				if(west != null && connected == false && west.getSubType() == subtype)
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
		
		
		/*
		renderer_.setRenderBounds(twoPx, zeroPx, fourPx, fourteenPx, twoPx, twelvePx);
		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
		
		renderer_.setRenderBounds(fourPx, twoPx, fivePx, twelvePx, fivePx, elevenPx);
		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
			
		renderer_.setRenderBounds(sixPx, fivePx, sixPx, tenPx, sixPx, tenPx);
		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
		*/
		
		
		return false;
	}


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
		

		
		/*
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderer_.setRenderBounds(zeroPx, zeroPx, sevenPx, onePx * 32, sixteenPx, eightPx);
		
		double px = zeroPx;
		double py = onePx;
		
		this.bindTexture(flagSheet);
		
		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 28; j++)
			{
				double ymin = (y_ + (i * onePx));
				double ymax = (y_ + (i * onePx)) + onePx;
				double xmin = (x_ + (j * onePx));
				double xmax = (x_ + (j * onePx)) + onePx;
				
				tessellator.addVertexWithUV(xmin, ymax, z_ + 1, px, px);
				tessellator.addVertexWithUV(xmin, ymin, z_ + 1, px, py);		
				tessellator.addVertexWithUV(xmax, ymin, z_ + 1, py, py);		
				tessellator.addVertexWithUV(xmax, ymax, z_ + 1, py, px);
				
				px = px + onePx;
				py = px + onePx;
				if(px > 15)
				{
					px = zeroPx;
					py = onePx;
				}
			}
		}
		






	   
	    //flush the Tessellator and rebind the default texture
		tessellator.draw();
        this.bindTexture(TextureMap.locationBlocksTexture);
        
		tessellator.startDrawingQuads();

		//renderer_.renderFaceZPos(block_, x_, y_, z_, block_.getIcon(0, 805));
		//renderer_.renderStandardBlockWithColorMultiplier(block_, x_, y_, z_, 1.0F, 1.0F, 1.0F);
		
		
		//tessellator.

		//renderer_.setRenderBounds(zeroPx, zeroPx, sevenPx, 29 * onePx, sixteenPx, eightPx);
		//renderer_.renderStandardBlock(block_, x_, y_, z_);
		//renderer_.renderStandardBlock(block_, x_, y_, z_);
		
		*/
		return true;
	}


	private boolean renderWorldFixedPlankLadderBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta) 
	{

		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);

		
		if(tile == null)
		{
			return true;
		}
		
		
		int side = tile.getVariable();
		
		boolean renderEnd = false;
		if(world_.isAirBlock(x_, y_ + 1, z_) == false && world_.isBlockNormalCube(x_, y_ + 1, z_) == true)
		{
			renderEnd = true;
		}
		
		double ymin = twoPx;
		double ymax = sixPx;
		double inc = eightPx;
		
		switch(side)
		{
		
			//North
			case 2:
			{					
				for(int i = 0; i < 2; i++)
				{
					renderer_.setRenderBounds(onePx, ymin, zeroPx, fifteenPx, ymax, onePx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				
				break;
			}
			
			//South
			case 3:
			{
				for(int i = 0; i < 2; i++)
				{
					renderer_.setRenderBounds(onePx, ymin, fifteenPx, fifteenPx, ymax, sixteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				
				break;
			}
			
			//East
			case 5:
			{
				for(int i = 0; i < 2; i++)
				{
					renderer_.setRenderBounds(fifteenPx, ymin, onePx, sixteenPx, ymax, fifteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				
				break;
			}
			
			//West
			case 4:
			{
				for(int i = 0; i < 2; i++)
				{
					renderer_.setRenderBounds(zeroPx, ymin, onePx, onePx, ymax, fifteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				
				break;
			}
		}

		return true;
	}


	private boolean renderWorldRopeLadderBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta) 
	{

		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);

		
		if(tile == null)
		{
			return true;
		}
		
		
		int side = tile.getVariable();
		double ymin = zeroPx;
		double ymax = sixteenPx;
		double xmin = sevenPx;
		double xmax = ninePx;
		double zmin = onePx;
		double zmax = threePx;
		
		boolean renderEnd = false;
		if(world_.isAirBlock(x_, y_ + 1, z_) == false && world_.isBlockNormalCube(x_, y_ + 1, z_) == true)
		{
			renderEnd = true;
		}
		
		switch(side)
		{
		
			//North
			case 2:
			{	
				if(renderEnd)
				{
					renderer_.setOverrideBlockTexture(block_.getIcon(3, 2));
					renderer_.setRenderBounds(sixPx, fifteenPx, zeroPx, tenPx, sixteenPx, fourPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					renderer_.clearOverrideBlockTexture();
					
					renderer_.setRenderBounds(sevenPx, zeroPx, onePx, ninePx, fifteenPx, threePx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				}
				else
				{
					renderer_.setRenderBounds(sevenPx, zeroPx, onePx, ninePx, sixteenPx, threePx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);							
				}
				break;
			}
			
			//South
			case 3:
			{
				if(renderEnd)
				{
					renderer_.setOverrideBlockTexture(block_.getIcon(3, 2));
					renderer_.setRenderBounds(sixPx, fifteenPx, twelvePx, tenPx, sixteenPx, sixteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					renderer_.clearOverrideBlockTexture();
					
					renderer_.setRenderBounds(sevenPx, zeroPx, thirteenPx, ninePx, fifteenPx, fifteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				}
				else
				{
					renderer_.setRenderBounds(sevenPx, zeroPx, thirteenPx, ninePx, sixteenPx, fifteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);							
				}
				break;
			}
			
			//East
			case 5:
			{
				if(renderEnd)
				{
					renderer_.setOverrideBlockTexture(block_.getIcon(3, 2));
					renderer_.setRenderBounds(twelvePx, fifteenPx, sixPx, sixteenPx, sixteenPx, tenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					renderer_.clearOverrideBlockTexture();
					
					renderer_.setRenderBounds(thirteenPx, zeroPx, sevenPx, fifteenPx, fifteenPx, ninePx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				}
				else
				{
					renderer_.setRenderBounds(thirteenPx, zeroPx, sevenPx, fifteenPx, sixteenPx, ninePx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);							
				}
				break;	
			}
			
			//West
			case 4:
			{
				if(renderEnd)
				{
					renderer_.setOverrideBlockTexture(block_.getIcon(3, 2));
					renderer_.setRenderBounds(zeroPx, fifteenPx, sixPx, fourPx, sixteenPx, tenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					renderer_.clearOverrideBlockTexture();
					
					renderer_.setRenderBounds(onePx, zeroPx, sevenPx, threePx, fifteenPx, ninePx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				}
				else
				{
					renderer_.setRenderBounds(onePx, zeroPx, sevenPx, threePx, sixteenPx, ninePx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);							
				}
				break;	
			}
			
			
		}

		return true;
	}


	private boolean renderWorldSimpleLadderBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_,int meta) 
	{

		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);

		
		if(tile == null)
		{
			return true;
		}
				
		double ymin = threePx;
		double ymax = fourPx;
		double inc = eightPx;
		
		
		int side = tile.getVariable();
		switch(side)
		{
		
			//North
			case 2:
			{
				renderer_.setRenderBounds(twoPx, zeroPx, zeroPx, threePx, sixteenPx, onePx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				renderer_.setRenderBounds(thirteenPx, zeroPx, zeroPx, fourteenPx, sixteenPx, onePx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 2; i++)
				{
					renderer_.setRenderBounds(threePx, ymin, zeroPx, thirteenPx, ymax, onePx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//South
			case 3:
			{
				
				renderer_.setRenderBounds(twoPx, zeroPx, fifteenPx, threePx, sixteenPx, sixteenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				renderer_.setRenderBounds(thirteenPx, zeroPx, fifteenPx, fourteenPx, sixteenPx, sixteenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 2; i++)
				{
					renderer_.setRenderBounds(threePx, ymin, fifteenPx, thirteenPx, ymax, sixteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				
				break;
			}
			
			//East
			case 5:
			{
				renderer_.setRenderBounds(fifteenPx, zeroPx, twoPx, sixteenPx, sixteenPx, threePx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				renderer_.setRenderBounds(fifteenPx, zeroPx, thirteenPx, sixteenPx, sixteenPx, fourteenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 2; i++)
				{
					renderer_.setRenderBounds(fifteenPx, ymin, threePx, sixteenPx, ymax, thirteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;				
			}
			
			//West
			case 4:
			{
				renderer_.setRenderBounds(zeroPx, zeroPx, twoPx, onePx, sixteenPx, threePx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				renderer_.setRenderBounds(zeroPx, zeroPx, thirteenPx, onePx, sixteenPx, fourteenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 2; i++)
				{
					renderer_.setRenderBounds(zeroPx, ymin, threePx, onePx, ymax, thirteenPx);
					drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
		}
		return true;
	}


	private boolean renderWorldPoleLadderBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta_)
	{

		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);

		
		if(tile == null)
		{
			return true;
		}
				
		double ymin = onePx;
		double ymax = twoPx;
		double inc = fourPx;

		
		int side = tile.getVariable();
		switch(side)
		{
		
			//North
			case 2:
			{
				
				double xmin = onePx;
				double xmax = fifteenPx;
				
				renderer_.setRenderBounds(sevenPx, zeroPx, onePx, ninePx, sixteenPx, threePx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 4; i++)
				{
					
					if(i % 2 == 0)
					{
						xmin = ninePx;
						xmax = fifteenPx;
						

						renderer_.setRenderBounds((xmax - onePx), ymax, onePx, xmax, (ymax + onePx), twoPx);
						drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					}
					else
					{
						xmin = onePx;
						xmax = sevenPx;
						
						renderer_.setRenderBounds(xmin, ymax, onePx, (xmin + onePx), (ymax + onePx), twoPx);
						drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);

					}
					
					renderer_.setRenderBounds(xmin, ymin, onePx, xmax, ymax, twoPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//South
			case 3:
			{
				
				double xmin = onePx;
				double xmax = fifteenPx;
				
				renderer_.setRenderBounds(sevenPx, zeroPx, thirteenPx, ninePx, sixteenPx, fifteenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 4; i++)
				{
					
					if(i % 2 == 0)
					{
						xmin = ninePx;
						xmax = fifteenPx;
						

						renderer_.setRenderBounds((xmax - onePx), ymax, fourteenPx, xmax, (ymax + onePx), fifteenPx);
						drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					}
					else
					{
						xmin = onePx;
						xmax = sevenPx;
						
						renderer_.setRenderBounds(xmin, ymax, fourteenPx, (xmin + onePx), (ymax + onePx), fifteenPx);
						drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);

					}
					
					renderer_.setRenderBounds(xmin, ymin, fourteenPx, xmax, ymax, fifteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//East
			case 5:
			{
				double xmin = onePx;
				double xmax = fifteenPx;
				
				renderer_.setRenderBounds(thirteenPx, zeroPx, sevenPx, fifteenPx, sixteenPx, ninePx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 4; i++)
				{
					
					if(i % 2 == 0)
					{
						xmin = ninePx;
						xmax = fifteenPx;
						

						renderer_.setRenderBounds(fourteenPx, ymax, (xmax - onePx), fifteenPx, (ymax + onePx), xmax);
						drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					}
					else
					{
						xmin = onePx;
						xmax = sevenPx;
						
						renderer_.setRenderBounds(fourteenPx, ymax, xmin, fifteenPx, (ymax + onePx), (xmin + onePx));
						drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);

					}
					
					renderer_.setRenderBounds(fourteenPx, ymin, xmin, fifteenPx, ymax, xmax);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//West
			case 4:
			{
				
				double xmin = onePx;
				double xmax = fifteenPx;
				
				renderer_.setRenderBounds(onePx, zeroPx, sevenPx, threePx, sixteenPx, ninePx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 4; i++)
				{
					
					if(i % 2 == 0)
					{
						xmin = ninePx;
						xmax = fifteenPx;
						

						renderer_.setRenderBounds(onePx, ymax, (xmax - onePx), twoPx, (ymax + onePx), xmax);
						drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					}
					else
					{
						xmin = onePx;
						xmax = sevenPx;
						
						renderer_.setRenderBounds(onePx, ymax, xmin, twoPx, (ymax + onePx), (xmin + onePx));
						drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);

					}
					
					renderer_.setRenderBounds(onePx, ymin, xmin, twoPx, ymax, xmax);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
		}
		return true;
	}
	
	private boolean renderWorldFootholdLadderBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta_)
	{
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
		
		if(tile == null)
		{
			return true;
		}
		
		
		double ymin = onePx;
		double ymax = twoPx;
		double inc = fourPx;
		
		renderer_.setRenderBounds(twoPx, zeroPx, onePx, fourteenPx, sixteenPx, twoPx);
		
		int side = tile.getVariable();
		switch(side)
		{
			//North
			case 2:
			{
				
				double xmin = twoPx;
				double xmax = sevenPx;
				for(int i = 0; i < 4; i++)
				{
					if(i % 2 == 0)
					{
						xmin = twoPx;
						xmax = sevenPx;					
					}
					else
					{
						xmin = ninePx;
						xmax = fourteenPx;
					}
					
					renderer_.setRenderBounds(xmin, ymin, onePx, xmax, ymax, twoPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(xmin, ymin, zeroPx, (xmin + onePx), ymax, onePx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds((xmax - onePx), ymin, zeroPx, xmax, ymax, onePx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//South
			case 3:
			{
				double xmin = twoPx;
				double xmax = sevenPx;
				
				for(int i = 0; i < 4; i++)
				{
					
					if(i % 2 == 0)
					{
						xmin = twoPx;
						xmax = sevenPx;					
					}
					else
					{
						xmin = ninePx;
						xmax = fourteenPx;
					}
					
					renderer_.setRenderBounds(xmin, ymin, fourteenPx, xmax, ymax, fifteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(xmin, ymin, fifteenPx, (xmin + onePx), ymax, sixteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds((xmax - onePx), ymin, fifteenPx, xmax, ymax, sixteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//East
			case 5:
			{
				double zmin = twoPx;
				double zmax = sevenPx;
				for(int i = 0; i < 4; i++)
				{
					if(i % 2 == 0)
					{
						zmin = twoPx;
						zmax = sevenPx;					
					}
					else
					{
						zmin = ninePx;
						zmax = fourteenPx;
					}
					
					renderer_.setRenderBounds(fourteenPx, ymin, zmin, fifteenPx, ymax, zmax);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(fifteenPx, ymin, zmin, sixteenPx, ymax, (zmin + onePx));
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(fifteenPx, ymin, (zmax - onePx), sixteenPx, ymax, zmax);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//West
			case 4:
			{
				double zmin = twoPx;
				double zmax = sevenPx;
				
				for(int i = 0; i < 4; i++)
				{
					if(i % 2 == 0)
					{
						zmin = twoPx;
						zmax = sevenPx;					
					}
					else
					{
						zmin = ninePx;
						zmax = fourteenPx;
					}
					
					renderer_.setRenderBounds(onePx, ymin, zmin, twoPx, ymax, zmax);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(zeroPx, ymin, zmin, onePx, ymax, (zmin + onePx));
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(zeroPx, ymin, (zmax - onePx), onePx, ymax, zmax);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
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
			int type = tile.getSubType();
			
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
	
	private boolean renderWorldClassicLadderBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta_) 
	{

		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);

		
		if(tile == null)
		{
			return true;
		}
				
		double ymin = onePx;
		double ymax = twoPx;
		double inc = fourPx;

		
		int side = tile.getVariable();
		switch(side)
		{
			//North
			case 2:
			{
				
				renderer_.setRenderBounds(twoPx, zeroPx, zeroPx, threePx, sixteenPx, threePx);

				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				renderer_.setRenderBounds(thirteenPx, zeroPx, zeroPx, fourteenPx, sixteenPx, threePx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 4; i++)
				{
					
					renderer_.setRenderBounds(threePx, ymin, onePx, thirteenPx, ymax, twoPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//South
			case 3:
			{
				
				renderer_.setRenderBounds(twoPx, zeroPx, thirteenPx, threePx, sixteenPx, sixteenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);

				renderer_.setRenderBounds(fourteenPx, zeroPx, sixteenPx, thirteenPx, sixteenPx, thirteenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 4; i++)
				{
					renderer_.setRenderBounds(threePx, ymin, fourteenPx, thirteenPx, ymax, fifteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//East
			case 5:
			{
				renderer_.setRenderBounds(thirteenPx, zeroPx, twoPx, sixteenPx, sixteenPx, threePx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				renderer_.setRenderBounds(thirteenPx, zeroPx, thirteenPx, sixteenPx, sixteenPx, fourteenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 4; i++)
				{
					renderer_.setRenderBounds(fourteenPx, ymin, threePx, fifteenPx, ymax, thirteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//West
			case 4:
			{
				
				renderer_.setRenderBounds(zeroPx, zeroPx, twoPx, threePx, sixteenPx, threePx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				renderer_.setRenderBounds(zeroPx, zeroPx, thirteenPx, threePx, sixteenPx, fourteenPx);
				drh.renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
				
				for(int i = 0; i < 4; i++)
				{
					renderer_.setRenderBounds(onePx, ymin, threePx, twoPx, ymax, thirteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
		}
		return true;
	}
	
	private boolean renderWorldIndustrialLadderBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta_) 
	{	
		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
		
		if(tile == null)
		{
			return true;
		}
		
		
		double ymin = onePx;
		double ymax = threePx;
		double inc = fourPx;
		
		renderer_.setRenderBounds(twoPx, zeroPx, onePx, fourteenPx, sixteenPx, twoPx);
		
		int side = tile.getVariable();
		switch(side)
		{
			//North
			case 2:
			{
				

				
				for(int i = 0; i < 4; i++)
				{
					renderer_.setRenderBounds(twoPx, ymin, onePx, fourteenPx, ymax, twoPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(twoPx, ymin, zeroPx, threePx, ymax, onePx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(thirteenPx, ymin, zeroPx, fourteenPx, ymax, onePx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//South
			case 3:
			{
				for(int i = 0; i < 4; i++)
				{
					renderer_.setRenderBounds(twoPx, ymin, fourteenPx, fourteenPx, ymax, fifteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(twoPx, ymin, fifteenPx, threePx, ymax, sixteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(thirteenPx, ymin, fifteenPx, fourteenPx, ymax, sixteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//East
			case 5:
			{
				for(int i = 0; i < 4; i++)
				{
					renderer_.setRenderBounds(fourteenPx, ymin, twoPx, fifteenPx, ymax, fourteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(fifteenPx, ymin, twoPx, sixteenPx, ymax, threePx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(fifteenPx, ymin, thirteenPx, sixteenPx, ymax, fourteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
			
			//West
			case 4:
			{
				for(int i = 0; i < 4; i++)
				{
					renderer_.setRenderBounds(onePx, ymin, twoPx, twoPx, ymax, fourteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(zeroPx, ymin, twoPx, onePx, ymax, threePx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					renderer_.setRenderBounds(zeroPx, ymin, thirteenPx, onePx, ymax, fourteenPx);
					drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
					
					ymin += inc;
					ymax += inc;
				}
				break;
			}
		}
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
