package kappafox.di.base;

import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.AdjustableIcon;
import kappafox.di.base.util.BoundSet;
import kappafox.di.base.util.PointSet;
import kappafox.di.base.util.TextureOffset;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
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
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class DiscreteRenderHelper 
{
	private static Tessellator tessellator = Tessellator.instance;
	private static TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
	
	private static final TranslationHelper translator = new TranslationHelper();
	
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    
    
    
    
    //For use with blocks that use the same code for inventory and world rendering
    public void renderDiscreteQuadWithColourMultiplier(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_, boolean isInventory)
    {
    	if(isInventory == true)
    	{
    		TileEntitySubtype tile = (TileEntitySubtype)world_.getBlockTileEntity(x_, y_, z_);
    		int subtype = tile.getSubtype();
    		tessellateInventoryBlock(renderer_, block_, subtype);
    	}
    	else
    	{
    		renderDiscreteQuadWithColourMultiplier(world_, renderer_, block_, x_, y_, z_);
    	}
    }
    
    
	public void renderDiscreteQuadWithColourMultiplier(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_)
	{
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);

		
		if(tile != null)
		{
			Block bl = Block.blocksList[tile.getTextureSource(0)];
			
	        float r = 1.0F;
	        float g = 1.0F;
	        float b = 1.0F;
	        
			if(bl != null)
			{
		        int l = Block.blocksList[tile.getTextureSource(0)].colorMultiplier(world_, x_, y_, z_);
		        r = (float)(l >> 16 & 255) / 255.0F;
		        g = (float)(l >> 8 & 255) / 255.0F;
		        b = (float)(l & 255) / 255.0F;
		
		        if (EntityRenderer.anaglyphEnable)
		        {
		            float f3 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
		            float f4 = (r * 30.0F + g * 70.0F) / 100.0F;
		            float f5 = (r * 30.0F + b * 70.0F) / 100.0F;
		            r = f3;
		            g = f4;
		            b = f5;
		        }
		        
		        
		        //System.out.println("ORIENT:" + tile.getTextureOrientation());
		        
		        if(tile.getTextureOrientation() == 3)
		        {
                    renderer_.uvRotateTop = 3;
		        }
		        
		        if(tile.getTextureOrientation() == 4)
		        {
                    renderer_.uvRotateTop = 2;
		        }
		        
		        if(tile.getTextureOrientation() == 5)
		        {
                    renderer_.uvRotateTop = 1;
		        }
		        
		        renderer_.renderStandardBlockWithColorMultiplier(block_, x_, y_, z_, r, g, b);
		        
                renderer_.uvRotateEast = 0;
                renderer_.uvRotateWest = 0;
                renderer_.uvRotateTop = 0;
                renderer_.uvRotateBottom = 0;
                renderer_.uvRotateNorth = 0;
                renderer_.uvRotateSouth = 0;
			}
		}
		

	}
	
	public void renderDiscreteQuadWithFlip(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_, boolean[] flip_)
	{
			tessellator.setBrightness(block_.getMixedBrightnessForBlock(world_, x_, y_, z_));
			

			renderer_.enableAO = true;
			if(flip_[0])
			{
				renderer_.flipTexture = true;
			}
	    	this.renderFaceAOXNeg(world_, renderer_, block_, x_, y_, z_);
	    	renderer_.flipTexture = false;
	    	
			if(flip_[1])
			{
				renderer_.flipTexture = true;
			}
	    	this.renderFaceAOXPos(world_, renderer_, block_, x_, y_, z_);		
	    	renderer_.flipTexture = false;
			
			if(flip_[2])
			{
				renderer_.flipTexture = true;
			}
			this.renderFaceAOYNeg(world_, renderer_, block_, x_, y_, z_);
			renderer_.flipTexture = false;
			
			if(flip_[3])
			{
				renderer_.flipTexture = true;
			}
	    	this.renderFaceAOYPos(world_, renderer_, block_, x_, y_, z_);
	    	renderer_.flipTexture = false;
	    	
			if(flip_[4])
			{
				renderer_.flipTexture = true;
			}
	    	this.renderFaceAOZNeg(world_, renderer_, block_, x_, y_, z_);
	    	renderer_.flipTexture = false;
	    	
			if(flip_[5])
			{
				renderer_.flipTexture = true;
			}
	    	this.renderFaceAOZPos(world_, renderer_, block_, x_, y_, z_);
	    	renderer_.flipTexture = false;
	    	
			renderer_.enableAO = false;
	}
	
	public void renderDiscreteQuad(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_)
	{				
			renderer_.enableAO = true;
			this.renderFaceAOYPos(world_, renderer_, block_, x_, y_, z_);
	    	this.renderFaceAOZPos(world_, renderer_, block_, x_, y_, z_);
	    	this.renderFaceAOZNeg(world_, renderer_, block_, x_, y_, z_);
	    	this.renderFaceAOYNeg(world_, renderer_, block_, x_, y_, z_);
	    	this.renderFaceAOXPos(world_, renderer_, block_, x_, y_, z_);
	    	this.renderFaceAOXNeg(world_, renderer_, block_, x_, y_, z_);
	    	renderer_.enableAO = false;
	}
	
	
	public void renderDiscreteQuadWithTextureOffsets(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_, TextureOffset off_)
	{				
			renderer_.enableAO = true;
			
			AdjustableIcon aico;
	    	
	    	if(off_.hasOffset(0))
	    	{
	    		aico = new AdjustableIcon(renderer_.getBlockIcon(block_, world_, x_, y_, z_, 0));
	    		aico.offsetU(off_.getOffsetU(0));
	    		aico.offsetV(off_.getOffsetV(0));
	    		this.renderFaceAOYNeg(world_, renderer_, block_, x_, y_, z_, aico);
	    	}	
	    	else
	    	{
	    		this.renderFaceAOYNeg(world_, renderer_, block_, x_, y_, z_);
	    	}
	    	
	    	if(off_.hasOffset(1))
	    	{
	    		aico = new AdjustableIcon(renderer_.getBlockIcon(block_, world_, x_, y_, z_, 1));
	    		aico.offsetU(off_.getOffsetU(1));
	    		aico.offsetV(off_.getOffsetV(1));
	    		this.renderFaceAOYPos(world_, renderer_, block_, x_, y_, z_, aico);
	    	}	
	    	else
	    	{
	    		this.renderFaceAOYPos(world_, renderer_, block_, x_, y_, z_);
	    	}
	    	
	    	if(off_.hasOffset(2))
	    	{
	    		aico = new AdjustableIcon(renderer_.getBlockIcon(block_, world_, x_, y_, z_, 2));
	    		aico.offsetU(off_.getOffsetU(2));
	    		aico.offsetV(off_.getOffsetV(2));
		    	this.renderFaceAOZNeg(world_, renderer_, block_, x_, y_, z_, aico);
	    	}	
	    	else
	    	{
		    	this.renderFaceAOZNeg(world_, renderer_, block_, x_, y_, z_);
	    	}
	    	
	    	if(off_.hasOffset(3))
	    	{
	    		aico = new AdjustableIcon(renderer_.getBlockIcon(block_, world_, x_, y_, z_, 3));
	    		aico.offsetU(off_.getOffsetU(3));
	    		aico.offsetV(off_.getOffsetV(3));
		    	this.renderFaceAOZPos(world_, renderer_, block_, x_, y_, z_, aico);
	    	}	
	    	else
	    	{
		    	this.renderFaceAOZPos(world_, renderer_, block_, x_, y_, z_);
	    	}
	    	
	    	
	    	if(off_.hasOffset(4))
	    	{
	    		aico = new AdjustableIcon(renderer_.getBlockIcon(block_, world_, x_, y_, z_, 4));
	    		aico.offsetU(off_.getOffsetU(4));
	    		aico.offsetV(off_.getOffsetV(4));
	    		this.renderFaceAOXNeg(world_, renderer_, block_, x_, y_, z_, aico);
	    	}	
	    	else
	    	{
	    		this.renderFaceAOXNeg(world_, renderer_, block_, x_, y_, z_);
	    	}
	    	
	    	
	    	if(off_.hasOffset(5))
	    	{
	    		aico = new AdjustableIcon(renderer_.getBlockIcon(block_, world_, x_, y_, z_, 5));
	    		aico.offsetU(off_.getOffsetU(5));
	    		aico.offsetV(off_.getOffsetV(5));
	    		this.renderFaceAOXPos(world_, renderer_, block_, x_, y_, z_, aico);
	    	}	
	    	else
	    	{
	    		this.renderFaceAOXPos(world_, renderer_, block_, x_, y_, z_);
	    	}

	    	renderer_.enableAO = false;
	}
	
	
	
	public int setupColour(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_, int side_)
	{
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);

		if(tile.getTextureSource(side_) == 0)
		{
			return block_.colorMultiplier(world_, x_, y_, z_);
		}
		else
		{
			return Block.blocksList[tile.getTextureSource(side_)].colorMultiplier(world_, x_, y_, z_);
		}		
	}
	
	
	public void renderFaceAOXNeg(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_)
	{
		this.renderFaceAOXNeg(world_, renderer_, block_, x_, y_, z_, null);
	}

	public void renderFaceAOXNeg(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_, Icon ico_)
	{
		int colourMultiplier = this.setupColour(world_, renderer_, block_, x_, y_, z_, 4);
        float par5 = (float)(colourMultiplier >> 16 & 255) / 255.0F;
        float par6 = (float)(colourMultiplier >> 8 & 255) / 255.0F;
        float par7 = (float)(colourMultiplier & 255) / 255.0F;
        
        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_, y_, z_);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer_.getBlockIcon(block_).getIconName().equals("grass_top"))
        {
            flag1 = false;
        }
        else if (renderer_.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;
        float f8;
        float f9;
        float f10;
        float f11;
        int j1;
        int k1;
        int l1;
        int i2;
        Icon icon;
        
        if (renderer_.renderAllFaces || block_.shouldSideBeRendered(world_, x_ - 1, y_, z_, 4))
        {
            if (renderer_.renderMinX <= 0.0D)
            {
                --x_;
            }

            renderer_.aoLightValueScratchXYNN = block_.getAmbientOcclusionLightValue(world_, x_, y_ - 1, z_);
            renderer_.aoLightValueScratchXZNN = block_.getAmbientOcclusionLightValue(world_, x_, y_, z_ - 1);
            renderer_.aoLightValueScratchXZNP = block_.getAmbientOcclusionLightValue(world_, x_, y_, z_ + 1);
            renderer_.aoLightValueScratchXYNP = block_.getAmbientOcclusionLightValue(world_, x_, y_ + 1, z_);
            renderer_.aoBrightnessXYNN = block_.getMixedBrightnessForBlock(world_, x_, y_ - 1, z_);
            renderer_.aoBrightnessXZNN = block_.getMixedBrightnessForBlock(world_, x_, y_, z_ - 1);
            renderer_.aoBrightnessXZNP = block_.getMixedBrightnessForBlock(world_, x_, y_, z_ + 1);
            renderer_.aoBrightnessXYNP = block_.getMixedBrightnessForBlock(world_, x_, y_ + 1, z_);
            flag3 = Block.canBlockGrass[world_.getBlockId(x_ - 1, y_ + 1, z_)];
            flag2 = Block.canBlockGrass[world_.getBlockId(x_ - 1, y_ - 1, z_)];
            flag5 = Block.canBlockGrass[world_.getBlockId(x_ - 1, y_, z_ - 1)];
            flag4 = Block.canBlockGrass[world_.getBlockId(x_ - 1, y_, z_ + 1)];

            if (!flag5 && !flag2)
            {
                renderer_.aoLightValueScratchXYZNNN = renderer_.aoLightValueScratchXZNN;
                renderer_.aoBrightnessXYZNNN = renderer_.aoBrightnessXZNN;
            }
            else
            {
                renderer_.aoLightValueScratchXYZNNN = block_.getAmbientOcclusionLightValue(world_, x_, y_ - 1, z_ - 1);
                renderer_.aoBrightnessXYZNNN = block_.getMixedBrightnessForBlock(world_, x_, y_ - 1, z_ - 1);
            }

            if (!flag4 && !flag2)
            {
                renderer_.aoLightValueScratchXYZNNP = renderer_.aoLightValueScratchXZNP;
                renderer_.aoBrightnessXYZNNP = renderer_.aoBrightnessXZNP;
            }
            else
            {
                renderer_.aoLightValueScratchXYZNNP = block_.getAmbientOcclusionLightValue(world_, x_, y_ - 1, z_ + 1);
                renderer_.aoBrightnessXYZNNP = block_.getMixedBrightnessForBlock(world_, x_, y_ - 1, z_ + 1);
            }

            if (!flag5 && !flag3)
            {
                renderer_.aoLightValueScratchXYZNPN = renderer_.aoLightValueScratchXZNN;
                renderer_.aoBrightnessXYZNPN = renderer_.aoBrightnessXZNN;
            }
            else
            {
                renderer_.aoLightValueScratchXYZNPN = block_.getAmbientOcclusionLightValue(world_, x_, y_ + 1, z_ - 1);
                renderer_.aoBrightnessXYZNPN = block_.getMixedBrightnessForBlock(world_, x_, y_ + 1, z_ - 1);
            }

            if (!flag4 && !flag3)
            {
                renderer_.aoLightValueScratchXYZNPP = renderer_.aoLightValueScratchXZNP;
                renderer_.aoBrightnessXYZNPP = renderer_.aoBrightnessXZNP;
            }
            else
            {
                renderer_.aoLightValueScratchXYZNPP = block_.getAmbientOcclusionLightValue(world_, x_, y_ + 1, z_ + 1);
                renderer_.aoBrightnessXYZNPP = block_.getMixedBrightnessForBlock(world_, x_, y_ + 1, z_ + 1);
            }

            if (renderer_.renderMinX <= 0.0D)
            {
                ++x_;
            }

            i1 = l;

            if (renderer_.renderMinX <= 0.0D || !world_.isBlockOpaqueCube(x_ - 1, y_, z_))
            {
                i1 = block_.getMixedBrightnessForBlock(world_, x_ - 1, y_, z_);
            }

            f7 = block_.getAmbientOcclusionLightValue(world_, x_ - 1, y_, z_);
            f9 = (renderer_.aoLightValueScratchXYNN + renderer_.aoLightValueScratchXYZNNP + f7 + renderer_.aoLightValueScratchXZNP) / 4.0F;
            f8 = (f7 + renderer_.aoLightValueScratchXZNP + renderer_.aoLightValueScratchXYNP + renderer_.aoLightValueScratchXYZNPP) / 4.0F;
            f11 = (renderer_.aoLightValueScratchXZNN + f7 + renderer_.aoLightValueScratchXYZNPN + renderer_.aoLightValueScratchXYNP) / 4.0F;
            f10 = (renderer_.aoLightValueScratchXYZNNN + renderer_.aoLightValueScratchXYNN + renderer_.aoLightValueScratchXZNN + f7) / 4.0F;
            f3 = (float)((double)f8 * renderer_.renderMaxY * renderer_.renderMaxZ + (double)f11 * renderer_.renderMaxY * (1.0D - renderer_.renderMaxZ) + (double)f10 * (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMaxZ) + (double)f9 * (1.0D - renderer_.renderMaxY) * renderer_.renderMaxZ);
            f4 = (float)((double)f8 * renderer_.renderMaxY * renderer_.renderMinZ + (double)f11 * renderer_.renderMaxY * (1.0D - renderer_.renderMinZ) + (double)f10 * (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMinZ) + (double)f9 * (1.0D - renderer_.renderMaxY) * renderer_.renderMinZ);
            f5 = (float)((double)f8 * renderer_.renderMinY * renderer_.renderMinZ + (double)f11 * renderer_.renderMinY * (1.0D - renderer_.renderMinZ) + (double)f10 * (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMinZ) + (double)f9 * (1.0D - renderer_.renderMinY) * renderer_.renderMinZ);
            f6 = (float)((double)f8 * renderer_.renderMinY * renderer_.renderMaxZ + (double)f11 * renderer_.renderMinY * (1.0D - renderer_.renderMaxZ) + (double)f10 * (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMaxZ) + (double)f9 * (1.0D - renderer_.renderMinY) * renderer_.renderMaxZ);
            k1 = renderer_.getAoBrightness(renderer_.aoBrightnessXYNN, renderer_.aoBrightnessXYZNNP, renderer_.aoBrightnessXZNP, i1);
            j1 = renderer_.getAoBrightness(renderer_.aoBrightnessXZNP, renderer_.aoBrightnessXYNP, renderer_.aoBrightnessXYZNPP, i1);
            i2 = renderer_.getAoBrightness(renderer_.aoBrightnessXZNN, renderer_.aoBrightnessXYZNPN, renderer_.aoBrightnessXYNP, i1);
            l1 = renderer_.getAoBrightness(renderer_.aoBrightnessXYZNNN, renderer_.aoBrightnessXYNN, renderer_.aoBrightnessXZNN, i1);
            renderer_.brightnessTopLeft = renderer_.mixAoBrightness(j1, i2, l1, k1, renderer_.renderMaxY * renderer_.renderMaxZ, renderer_.renderMaxY * (1.0D - renderer_.renderMaxZ), (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMaxZ), (1.0D - renderer_.renderMaxY) * renderer_.renderMaxZ);
            renderer_.brightnessBottomLeft = renderer_.mixAoBrightness(j1, i2, l1, k1, renderer_.renderMaxY * renderer_.renderMinZ, renderer_.renderMaxY * (1.0D - renderer_.renderMinZ), (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMinZ), (1.0D - renderer_.renderMaxY) * renderer_.renderMinZ);
            renderer_.brightnessBottomRight = renderer_.mixAoBrightness(j1, i2, l1, k1, renderer_.renderMinY * renderer_.renderMinZ, renderer_.renderMinY * (1.0D - renderer_.renderMinZ), (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMinZ), (1.0D - renderer_.renderMinY) * renderer_.renderMinZ);
            renderer_.brightnessTopRight = renderer_.mixAoBrightness(j1, i2, l1, k1, renderer_.renderMinY * renderer_.renderMaxZ, renderer_.renderMinY * (1.0D - renderer_.renderMaxZ), (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMaxZ), (1.0D - renderer_.renderMinY) * renderer_.renderMaxZ);

            if (flag1)
            {
                renderer_.colorRedTopLeft = renderer_.colorRedBottomLeft = renderer_.colorRedBottomRight = renderer_.colorRedTopRight = par5 * 0.6F;
                renderer_.colorGreenTopLeft = renderer_.colorGreenBottomLeft = renderer_.colorGreenBottomRight = renderer_.colorGreenTopRight = par6 * 0.6F;
                renderer_.colorBlueTopLeft = renderer_.colorBlueBottomLeft = renderer_.colorBlueBottomRight = renderer_.colorBlueTopRight = par7 * 0.6F;
            }
            else
            {
                renderer_.colorRedTopLeft = renderer_.colorRedBottomLeft = renderer_.colorRedBottomRight = renderer_.colorRedTopRight = 0.6F;
                renderer_.colorGreenTopLeft = renderer_.colorGreenBottomLeft = renderer_.colorGreenBottomRight = renderer_.colorGreenTopRight = 0.6F;
                renderer_.colorBlueTopLeft = renderer_.colorBlueBottomLeft = renderer_.colorBlueBottomRight = renderer_.colorBlueTopRight = 0.6F;
            }

            renderer_.colorRedTopLeft *= f3;
            renderer_.colorGreenTopLeft *= f3;
            renderer_.colorBlueTopLeft *= f3;
            renderer_.colorRedBottomLeft *= f4;
            renderer_.colorGreenBottomLeft *= f4;
            renderer_.colorBlueBottomLeft *= f4;
            renderer_.colorRedBottomRight *= f5;
            renderer_.colorGreenBottomRight *= f5;
            renderer_.colorBlueBottomRight *= f5;
            renderer_.colorRedTopRight *= f6;
            renderer_.colorGreenTopRight *= f6;
            renderer_.colorBlueTopRight *= f6;
            
            if(ico_ != null)
            {
            	icon = ico_;
            }
            else
            {
            	icon = renderer_.getBlockIcon(block_, world_, x_, y_, z_, 4);
            }
            
            renderer_.renderFaceXNeg(block_, (double)x_, (double)y_, (double)z_, icon);

            if (renderer_.fancyGrass && icon.getIconName().equals("grass_side") && !renderer_.hasOverrideBlockTexture())
            {
                renderer_.colorRedTopLeft *= par5;
                renderer_.colorRedBottomLeft *= par5;
                renderer_.colorRedBottomRight *= par5;
                renderer_.colorRedTopRight *= par5;
                renderer_.colorGreenTopLeft *= par6;
                renderer_.colorGreenBottomLeft *= par6;
                renderer_.colorGreenBottomRight *= par6;
                renderer_.colorGreenTopRight *= par6;
                renderer_.colorBlueTopLeft *= par7;
                renderer_.colorBlueBottomLeft *= par7;
                renderer_.colorBlueBottomRight *= par7;
                renderer_.colorBlueTopRight *= par7;
                renderer_.renderFaceXNeg(block_, (double)x_, (double)y_, (double)z_, BlockGrass.getIconSideOverlay());
            }
        }
        
	}
	
	public void renderFaceAOXPos(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_)
	{
		this.renderFaceAOXPos(world_, renderer_, block_, x_, y_, z_, null);
	}
	
	public void renderFaceAOXPos(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_, Icon ico_)
	{
        int l2 = this.setupColour(world_, renderer_, block_, x_, y_, z_, 5);
        float par5 = (float)(l2 >> 16 & 255) / 255.0F;
        float par6 = (float)(l2 >> 8 & 255) / 255.0F;
        float par7 = (float)(l2 & 255) / 255.0F;
        

        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_, y_, z_);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer_.getBlockIcon(block_).getIconName().equals("grass_top"))
        {
            flag1 = false;
        }
        else if (renderer_.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;
        float f8;
        float f9;
        float f10;
        float f11;
        int j1;
        int k1;
        int l1;
        int i2;
        
        Icon icon;
        
        if (renderer_.renderAllFaces || block_.shouldSideBeRendered(world_, x_ + 1, y_, z_, 5))
        {
            if (renderer_.renderMaxX >= 1.0D)
            {
                ++x_;
            }

            renderer_.aoLightValueScratchXYPN = block_.getAmbientOcclusionLightValue(world_, x_, y_ - 1, z_);
            renderer_.aoLightValueScratchXZPN = block_.getAmbientOcclusionLightValue(world_, x_, y_, z_ - 1);
            renderer_.aoLightValueScratchXZPP = block_.getAmbientOcclusionLightValue(world_, x_, y_, z_ + 1);
            renderer_.aoLightValueScratchXYPP = block_.getAmbientOcclusionLightValue(world_, x_, y_ + 1, z_);
            renderer_.aoBrightnessXYPN = block_.getMixedBrightnessForBlock(world_, x_, y_ - 1, z_);
            renderer_.aoBrightnessXZPN = block_.getMixedBrightnessForBlock(world_, x_, y_, z_ - 1);
            renderer_.aoBrightnessXZPP = block_.getMixedBrightnessForBlock(world_, x_, y_, z_ + 1);
            renderer_.aoBrightnessXYPP = block_.getMixedBrightnessForBlock(world_, x_, y_ + 1, z_);
            flag3 = Block.canBlockGrass[world_.getBlockId(x_ + 1, y_ + 1, z_)];
            flag2 = Block.canBlockGrass[world_.getBlockId(x_ + 1, y_ - 1, z_)];
            flag5 = Block.canBlockGrass[world_.getBlockId(x_ + 1, y_, z_ + 1)];
            flag4 = Block.canBlockGrass[world_.getBlockId(x_ + 1, y_, z_ - 1)];

            if (!flag2 && !flag4)
            {
                renderer_.aoLightValueScratchXYZPNN = renderer_.aoLightValueScratchXZPN;
                renderer_.aoBrightnessXYZPNN = renderer_.aoBrightnessXZPN;
            }
            else
            {
                renderer_.aoLightValueScratchXYZPNN = block_.getAmbientOcclusionLightValue(world_, x_, y_ - 1, z_ - 1);
                renderer_.aoBrightnessXYZPNN = block_.getMixedBrightnessForBlock(world_, x_, y_ - 1, z_ - 1);
            }

            if (!flag2 && !flag5)
            {
                renderer_.aoLightValueScratchXYZPNP = renderer_.aoLightValueScratchXZPP;
                renderer_.aoBrightnessXYZPNP = renderer_.aoBrightnessXZPP;
            }
            else
            {
                renderer_.aoLightValueScratchXYZPNP = block_.getAmbientOcclusionLightValue(world_, x_, y_ - 1, z_ + 1);
                renderer_.aoBrightnessXYZPNP = block_.getMixedBrightnessForBlock(world_, x_, y_ - 1, z_ + 1);
            }

            if (!flag3 && !flag4)
            {
                renderer_.aoLightValueScratchXYZPPN = renderer_.aoLightValueScratchXZPN;
                renderer_.aoBrightnessXYZPPN = renderer_.aoBrightnessXZPN;
            }
            else
            {
                renderer_.aoLightValueScratchXYZPPN = block_.getAmbientOcclusionLightValue(world_, x_, y_ + 1, z_ - 1);
                renderer_.aoBrightnessXYZPPN = block_.getMixedBrightnessForBlock(world_, x_, y_ + 1, z_ - 1);
            }

            if (!flag3 && !flag5)
            {
                renderer_.aoLightValueScratchXYZPPP = renderer_.aoLightValueScratchXZPP;
                renderer_.aoBrightnessXYZPPP = renderer_.aoBrightnessXZPP;
            }
            else
            {
                renderer_.aoLightValueScratchXYZPPP = block_.getAmbientOcclusionLightValue(world_, x_, y_ + 1, z_ + 1);
                renderer_.aoBrightnessXYZPPP = block_.getMixedBrightnessForBlock(world_, x_, y_ + 1, z_ + 1);
            }

            if (renderer_.renderMaxX >= 1.0D)
            {
                --x_;
            }

            i1 = l;

            if (renderer_.renderMaxX >= 1.0D || !world_.isBlockOpaqueCube(x_ + 1, y_, z_))
            {
                i1 = block_.getMixedBrightnessForBlock(world_, x_ + 1, y_, z_);
            }

            f7 = block_.getAmbientOcclusionLightValue(world_, x_ + 1, y_, z_);
            f9 = (renderer_.aoLightValueScratchXYPN + renderer_.aoLightValueScratchXYZPNP + f7 + renderer_.aoLightValueScratchXZPP) / 4.0F;
            f8 = (renderer_.aoLightValueScratchXYZPNN + renderer_.aoLightValueScratchXYPN + renderer_.aoLightValueScratchXZPN + f7) / 4.0F;
            f11 = (renderer_.aoLightValueScratchXZPN + f7 + renderer_.aoLightValueScratchXYZPPN + renderer_.aoLightValueScratchXYPP) / 4.0F;
            f10 = (f7 + renderer_.aoLightValueScratchXZPP + renderer_.aoLightValueScratchXYPP + renderer_.aoLightValueScratchXYZPPP) / 4.0F;
            f3 = (float)((double)f9 * (1.0D - renderer_.renderMinY) * renderer_.renderMaxZ + (double)f8 * (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMaxZ) + (double)f11 * renderer_.renderMinY * (1.0D - renderer_.renderMaxZ) + (double)f10 * renderer_.renderMinY * renderer_.renderMaxZ);
            f4 = (float)((double)f9 * (1.0D - renderer_.renderMinY) * renderer_.renderMinZ + (double)f8 * (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMinZ) + (double)f11 * renderer_.renderMinY * (1.0D - renderer_.renderMinZ) + (double)f10 * renderer_.renderMinY * renderer_.renderMinZ);
            f5 = (float)((double)f9 * (1.0D - renderer_.renderMaxY) * renderer_.renderMinZ + (double)f8 * (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMinZ) + (double)f11 * renderer_.renderMaxY * (1.0D - renderer_.renderMinZ) + (double)f10 * renderer_.renderMaxY * renderer_.renderMinZ);
            f6 = (float)((double)f9 * (1.0D - renderer_.renderMaxY) * renderer_.renderMaxZ + (double)f8 * (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMaxZ) + (double)f11 * renderer_.renderMaxY * (1.0D - renderer_.renderMaxZ) + (double)f10 * renderer_.renderMaxY * renderer_.renderMaxZ);
            k1 = renderer_.getAoBrightness(renderer_.aoBrightnessXYPN, renderer_.aoBrightnessXYZPNP, renderer_.aoBrightnessXZPP, i1);
            j1 = renderer_.getAoBrightness(renderer_.aoBrightnessXZPP, renderer_.aoBrightnessXYPP, renderer_.aoBrightnessXYZPPP, i1);
            i2 = renderer_.getAoBrightness(renderer_.aoBrightnessXZPN, renderer_.aoBrightnessXYZPPN, renderer_.aoBrightnessXYPP, i1);
            l1 = renderer_.getAoBrightness(renderer_.aoBrightnessXYZPNN, renderer_.aoBrightnessXYPN, renderer_.aoBrightnessXZPN, i1);
            renderer_.brightnessTopLeft = renderer_.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer_.renderMinY) * renderer_.renderMaxZ, (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMaxZ), renderer_.renderMinY * (1.0D - renderer_.renderMaxZ), renderer_.renderMinY * renderer_.renderMaxZ);
            renderer_.brightnessBottomLeft = renderer_.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer_.renderMinY) * renderer_.renderMinZ, (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMinZ), renderer_.renderMinY * (1.0D - renderer_.renderMinZ), renderer_.renderMinY * renderer_.renderMinZ);
            renderer_.brightnessBottomRight = renderer_.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer_.renderMaxY) * renderer_.renderMinZ, (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMinZ), renderer_.renderMaxY * (1.0D - renderer_.renderMinZ), renderer_.renderMaxY * renderer_.renderMinZ);
            renderer_.brightnessTopRight = renderer_.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer_.renderMaxY) * renderer_.renderMaxZ, (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMaxZ), renderer_.renderMaxY * (1.0D - renderer_.renderMaxZ), renderer_.renderMaxY * renderer_.renderMaxZ);

            if (flag1)
            {
                renderer_.colorRedTopLeft = renderer_.colorRedBottomLeft = renderer_.colorRedBottomRight = renderer_.colorRedTopRight = par5 * 0.6F;
                renderer_.colorGreenTopLeft = renderer_.colorGreenBottomLeft = renderer_.colorGreenBottomRight = renderer_.colorGreenTopRight = par6 * 0.6F;
                renderer_.colorBlueTopLeft = renderer_.colorBlueBottomLeft = renderer_.colorBlueBottomRight = renderer_.colorBlueTopRight = par7 * 0.6F;
            }
            else
            {
                renderer_.colorRedTopLeft = renderer_.colorRedBottomLeft = renderer_.colorRedBottomRight = renderer_.colorRedTopRight = 0.6F;
                renderer_.colorGreenTopLeft = renderer_.colorGreenBottomLeft = renderer_.colorGreenBottomRight = renderer_.colorGreenTopRight = 0.6F;
                renderer_.colorBlueTopLeft = renderer_.colorBlueBottomLeft = renderer_.colorBlueBottomRight = renderer_.colorBlueTopRight = 0.6F;
            }

            renderer_.colorRedTopLeft *= f3;
            renderer_.colorGreenTopLeft *= f3;
            renderer_.colorBlueTopLeft *= f3;
            renderer_.colorRedBottomLeft *= f4;
            renderer_.colorGreenBottomLeft *= f4;
            renderer_.colorBlueBottomLeft *= f4;
            renderer_.colorRedBottomRight *= f5;
            renderer_.colorGreenBottomRight *= f5;
            renderer_.colorBlueBottomRight *= f5;
            renderer_.colorRedTopRight *= f6;
            renderer_.colorGreenTopRight *= f6;
            renderer_.colorBlueTopRight *= f6;
            
            if(ico_ != null)
            {
            	icon = ico_;
            }
            else
            {
                icon = renderer_.getBlockIcon(block_, world_, x_, y_, z_, 5);	
            }
            

            renderer_.renderFaceXPos(block_, (double)x_, (double)y_, (double)z_, icon);

            if (renderer_.fancyGrass && icon.getIconName().equals("grass_side") && !renderer_.hasOverrideBlockTexture())
            {
                renderer_.colorRedTopLeft *= par5;
                renderer_.colorRedBottomLeft *= par5;
                renderer_.colorRedBottomRight *= par5;
                renderer_.colorRedTopRight *= par5;
                renderer_.colorGreenTopLeft *= par6;
                renderer_.colorGreenBottomLeft *= par6;
                renderer_.colorGreenBottomRight *= par6;
                renderer_.colorGreenTopRight *= par6;
                renderer_.colorBlueTopLeft *= par7;
                renderer_.colorBlueBottomLeft *= par7;
                renderer_.colorBlueBottomRight *= par7;
                renderer_.colorBlueTopRight *= par7;
                renderer_.renderFaceXPos(block_, (double)x_, (double)y_, (double)z_, BlockGrass.getIconSideOverlay());
            }

            flag = true;
        }
        

	}
	
	public void renderFaceAOZNeg(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_)
	{
		this.renderFaceAOZNeg(world_, renderer_, block_, x_, y_, z_, null);
	}
	
	public void renderFaceAOZNeg(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_, Icon ico_)
	{
        int l2 = this.setupColour(world_, renderer_, block_, x_, y_, z_, 2);
        float par5 = (float)(l2 >> 16 & 255) / 255.0F;
        float par6 = (float)(l2 >> 8 & 255) / 255.0F;
        float par7 = (float)(l2 & 255) / 255.0F;
        

        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_, y_, z_);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer_.getBlockIcon(block_).getIconName().equals("grass_top"))
        {
            flag1 = false;
        }
        else if (renderer_.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;
        float f8;
        float f9;
        float f10;
        float f11;
        int j1;
        int k1;
        int l1;
        int i2;
        Icon icon;
        
        if (renderer_.renderMinZ <= 0.0D)
        {
            --z_;
        }

        renderer_.aoLightValueScratchXZNN = block_.getAmbientOcclusionLightValue(world_, x_ - 1, y_, z_);
        renderer_.aoLightValueScratchYZNN = block_.getAmbientOcclusionLightValue(world_, x_, y_ - 1, z_);
        renderer_.aoLightValueScratchYZPN = block_.getAmbientOcclusionLightValue(world_, x_, y_ + 1, z_);
        renderer_.aoLightValueScratchXZPN = block_.getAmbientOcclusionLightValue(world_, x_ + 1, y_, z_);
        renderer_.aoBrightnessXZNN = block_.getMixedBrightnessForBlock(world_, x_ - 1, y_, z_);
        renderer_.aoBrightnessYZNN = block_.getMixedBrightnessForBlock(world_, x_, y_ - 1, z_);
        renderer_.aoBrightnessYZPN = block_.getMixedBrightnessForBlock(world_, x_, y_ + 1, z_);
        renderer_.aoBrightnessXZPN = block_.getMixedBrightnessForBlock(world_, x_ + 1, y_, z_);
        flag3 = Block.canBlockGrass[world_.getBlockId(x_ + 1, y_, z_ - 1)];
        flag2 = Block.canBlockGrass[world_.getBlockId(x_ - 1, y_, z_ - 1)];
        flag5 = Block.canBlockGrass[world_.getBlockId(x_, y_ + 1, z_ - 1)];
        flag4 = Block.canBlockGrass[world_.getBlockId(x_, y_ - 1, z_ - 1)];

        if (!flag2 && !flag4)
        {
            renderer_.aoLightValueScratchXYZNNN = renderer_.aoLightValueScratchXZNN;
            renderer_.aoBrightnessXYZNNN = renderer_.aoBrightnessXZNN;
        }
        else
        {
            renderer_.aoLightValueScratchXYZNNN = block_.getAmbientOcclusionLightValue(world_, x_ - 1, y_ - 1, z_);
            renderer_.aoBrightnessXYZNNN = block_.getMixedBrightnessForBlock(world_, x_ - 1, y_ - 1, z_);
        }

        if (!flag2 && !flag5)
        {
            renderer_.aoLightValueScratchXYZNPN = renderer_.aoLightValueScratchXZNN;
            renderer_.aoBrightnessXYZNPN = renderer_.aoBrightnessXZNN;
        }
        else
        {
            renderer_.aoLightValueScratchXYZNPN = block_.getAmbientOcclusionLightValue(world_, x_ - 1, y_ + 1, z_);
            renderer_.aoBrightnessXYZNPN = block_.getMixedBrightnessForBlock(world_, x_ - 1, y_ + 1, z_);
        }

        if (!flag3 && !flag4)
        {
            renderer_.aoLightValueScratchXYZPNN = renderer_.aoLightValueScratchXZPN;
            renderer_.aoBrightnessXYZPNN = renderer_.aoBrightnessXZPN;
        }
        else
        {
            renderer_.aoLightValueScratchXYZPNN = block_.getAmbientOcclusionLightValue(world_, x_ + 1, y_ - 1, z_);
            renderer_.aoBrightnessXYZPNN = block_.getMixedBrightnessForBlock(world_, x_ + 1, y_ - 1, z_);
        }

        if (!flag3 && !flag5)
        {
            renderer_.aoLightValueScratchXYZPPN = renderer_.aoLightValueScratchXZPN;
            renderer_.aoBrightnessXYZPPN = renderer_.aoBrightnessXZPN;
        }
        else
        {
            renderer_.aoLightValueScratchXYZPPN = block_.getAmbientOcclusionLightValue(world_, x_ + 1, y_ + 1, z_);
            renderer_.aoBrightnessXYZPPN = block_.getMixedBrightnessForBlock(world_, x_ + 1, y_ + 1, z_);
        }

        if (renderer_.renderMinZ <= 0.0D)
        {
            ++z_;
        }

        i1 = l;

        if (renderer_.renderMinZ <= 0.0D || !world_.isBlockOpaqueCube(x_, y_, z_ - 1))
        {
            i1 = block_.getMixedBrightnessForBlock(world_, x_, y_, z_ - 1);
        }

        f7 = block_.getAmbientOcclusionLightValue(world_, x_, y_, z_ - 1);
        f9 = (renderer_.aoLightValueScratchXZNN + renderer_.aoLightValueScratchXYZNPN + f7 + renderer_.aoLightValueScratchYZPN) / 4.0F;
        f8 = (f7 + renderer_.aoLightValueScratchYZPN + renderer_.aoLightValueScratchXZPN + renderer_.aoLightValueScratchXYZPPN) / 4.0F;
        f11 = (renderer_.aoLightValueScratchYZNN + f7 + renderer_.aoLightValueScratchXYZPNN + renderer_.aoLightValueScratchXZPN) / 4.0F;
        f10 = (renderer_.aoLightValueScratchXYZNNN + renderer_.aoLightValueScratchXZNN + renderer_.aoLightValueScratchYZNN + f7) / 4.0F;
        f3 = (float)((double)f9 * renderer_.renderMaxY * (1.0D - renderer_.renderMinX) + (double)f8 * renderer_.renderMinY * renderer_.renderMinX + (double)f11 * (1.0D - renderer_.renderMaxY) * renderer_.renderMinX + (double)f10 * (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMinX));
        f4 = (float)((double)f9 * renderer_.renderMaxY * (1.0D - renderer_.renderMaxX) + (double)f8 * renderer_.renderMaxY * renderer_.renderMaxX + (double)f11 * (1.0D - renderer_.renderMaxY) * renderer_.renderMaxX + (double)f10 * (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMaxX));
        f5 = (float)((double)f9 * renderer_.renderMinY * (1.0D - renderer_.renderMaxX) + (double)f8 * renderer_.renderMinY * renderer_.renderMaxX + (double)f11 * (1.0D - renderer_.renderMinY) * renderer_.renderMaxX + (double)f10 * (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMaxX));
        f6 = (float)((double)f9 * renderer_.renderMinY * (1.0D - renderer_.renderMinX) + (double)f8 * renderer_.renderMinY * renderer_.renderMinX + (double)f11 * (1.0D - renderer_.renderMinY) * renderer_.renderMinX + (double)f10 * (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMinX));
        k1 = renderer_.getAoBrightness(renderer_.aoBrightnessXZNN, renderer_.aoBrightnessXYZNPN, renderer_.aoBrightnessYZPN, i1);
        j1 = renderer_.getAoBrightness(renderer_.aoBrightnessYZPN, renderer_.aoBrightnessXZPN, renderer_.aoBrightnessXYZPPN, i1);
        i2 = renderer_.getAoBrightness(renderer_.aoBrightnessYZNN, renderer_.aoBrightnessXYZPNN, renderer_.aoBrightnessXZPN, i1);
        l1 = renderer_.getAoBrightness(renderer_.aoBrightnessXYZNNN, renderer_.aoBrightnessXZNN, renderer_.aoBrightnessYZNN, i1);
        renderer_.brightnessTopLeft = renderer_.mixAoBrightness(k1, j1, i2, l1, renderer_.renderMaxY * (1.0D - renderer_.renderMinX), renderer_.renderMaxY * renderer_.renderMinX, (1.0D - renderer_.renderMaxY) * renderer_.renderMinX, (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMinX));
        renderer_.brightnessBottomLeft = renderer_.mixAoBrightness(k1, j1, i2, l1, renderer_.renderMaxY * (1.0D - renderer_.renderMaxX), renderer_.renderMaxY * renderer_.renderMaxX, (1.0D - renderer_.renderMaxY) * renderer_.renderMaxX, (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMaxX));
        renderer_.brightnessBottomRight = renderer_.mixAoBrightness(k1, j1, i2, l1, renderer_.renderMinY * (1.0D - renderer_.renderMaxX), renderer_.renderMinY * renderer_.renderMaxX, (1.0D - renderer_.renderMinY) * renderer_.renderMaxX, (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMaxX));
        renderer_.brightnessTopRight = renderer_.mixAoBrightness(k1, j1, i2, l1, renderer_.renderMinY * (1.0D - renderer_.renderMinX), renderer_.renderMinY * renderer_.renderMinX, (1.0D - renderer_.renderMinY) * renderer_.renderMinX, (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMinX));

        if (flag1)
        {
            renderer_.colorRedTopLeft = renderer_.colorRedBottomLeft = renderer_.colorRedBottomRight = renderer_.colorRedTopRight = par5 * 0.8F;
            renderer_.colorGreenTopLeft = renderer_.colorGreenBottomLeft = renderer_.colorGreenBottomRight = renderer_.colorGreenTopRight = par6 * 0.8F;
            renderer_.colorBlueTopLeft = renderer_.colorBlueBottomLeft = renderer_.colorBlueBottomRight = renderer_.colorBlueTopRight = par7 * 0.8F;
        }
        else
        {
            renderer_.colorRedTopLeft = renderer_.colorRedBottomLeft = renderer_.colorRedBottomRight = renderer_.colorRedTopRight = 0.8F;
            renderer_.colorGreenTopLeft = renderer_.colorGreenBottomLeft = renderer_.colorGreenBottomRight = renderer_.colorGreenTopRight = 0.8F;
            renderer_.colorBlueTopLeft = renderer_.colorBlueBottomLeft = renderer_.colorBlueBottomRight = renderer_.colorBlueTopRight = 0.8F;
        }

        renderer_.colorRedTopLeft *= f3;
        renderer_.colorGreenTopLeft *= f3;
        renderer_.colorBlueTopLeft *= f3;
        renderer_.colorRedBottomLeft *= f4;
        renderer_.colorGreenBottomLeft *= f4;
        renderer_.colorBlueBottomLeft *= f4;
        renderer_.colorRedBottomRight *= f5;
        renderer_.colorGreenBottomRight *= f5;
        renderer_.colorBlueBottomRight *= f5;
        renderer_.colorRedTopRight *= f6;
        renderer_.colorGreenTopRight *= f6;
        renderer_.colorBlueTopRight *= f6;

        if(ico_ != null)
        {
        	icon = ico_;
        }
        else
        {
        	icon = renderer_.getBlockIcon(block_, world_, x_, y_, z_, 2);
        }
        renderer_.renderFaceZNeg(block_, (double)x_, (double)y_, (double)z_, icon);

        if (renderer_.fancyGrass && icon.getIconName().equals("grass_side") && !renderer_.hasOverrideBlockTexture())
        {
            renderer_.colorRedTopLeft *= par5;
            renderer_.colorRedBottomLeft *= par5;
            renderer_.colorRedBottomRight *= par5;
            renderer_.colorRedTopRight *= par5;
            renderer_.colorGreenTopLeft *= par6;
            renderer_.colorGreenBottomLeft *= par6;
            renderer_.colorGreenBottomRight *= par6;
            renderer_.colorGreenTopRight *= par6;
            renderer_.colorBlueTopLeft *= par7;
            renderer_.colorBlueBottomLeft *= par7;
            renderer_.colorBlueBottomRight *= par7;
            renderer_.colorBlueTopRight *= par7;
            renderer_.renderFaceZNeg(block_, (double)x_, (double)y_, (double)z_, BlockGrass.getIconSideOverlay());
        }
        

	}
	
	public void renderFaceAOZPos(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_)
	{
		this.renderFaceAOZPos(world_, renderer_, block_, x_, y_, z_, null);
	}
	
	public void renderFaceAOZPos(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_, Icon ico_)
	{
        int l2 = this.setupColour(world_, renderer_, block_, x_, y_, z_, 3);
        float par5 = (float)(l2 >> 16 & 255) / 255.0F;
        float par6 = (float)(l2 >> 8 & 255) / 255.0F;
        float par7 = (float)(l2 & 255) / 255.0F;

        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_, y_, z_);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer_.getBlockIcon(block_).getIconName().equals("grass_top"))
        {
            flag1 = false;
        }
        else if (renderer_.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;
        float f8;
        float f9;
        float f10;
        float f11;
        int j1;
        int k1;
        int l1;
        int i2;
        Icon icon;
        
        if (renderer_.renderAllFaces || block_.shouldSideBeRendered(world_, x_, y_, z_ + 1, 3))
        {
            if (renderer_.renderMaxZ >= 1.0D)
            {
                ++z_;
            }

            renderer_.aoLightValueScratchXZNP = block_.getAmbientOcclusionLightValue(world_, x_ - 1, y_, z_);
            renderer_.aoLightValueScratchXZPP = block_.getAmbientOcclusionLightValue(world_, x_ + 1, y_, z_);
            renderer_.aoLightValueScratchYZNP = block_.getAmbientOcclusionLightValue(world_, x_, y_ - 1, z_);
            renderer_.aoLightValueScratchYZPP = block_.getAmbientOcclusionLightValue(world_, x_, y_ + 1, z_);
            renderer_.aoBrightnessXZNP = block_.getMixedBrightnessForBlock(world_, x_ - 1, y_, z_);
            renderer_.aoBrightnessXZPP = block_.getMixedBrightnessForBlock(world_, x_ + 1, y_, z_);
            renderer_.aoBrightnessYZNP = block_.getMixedBrightnessForBlock(world_, x_, y_ - 1, z_);
            renderer_.aoBrightnessYZPP = block_.getMixedBrightnessForBlock(world_, x_, y_ + 1, z_);
            flag3 = Block.canBlockGrass[world_.getBlockId(x_ + 1, y_, z_ + 1)];
            flag2 = Block.canBlockGrass[world_.getBlockId(x_ - 1, y_, z_ + 1)];
            flag5 = Block.canBlockGrass[world_.getBlockId(x_, y_ + 1, z_ + 1)];
            flag4 = Block.canBlockGrass[world_.getBlockId(x_, y_ - 1, z_ + 1)];

            if (!flag2 && !flag4)
            {
                renderer_.aoLightValueScratchXYZNNP = renderer_.aoLightValueScratchXZNP;
                renderer_.aoBrightnessXYZNNP = renderer_.aoBrightnessXZNP;
            }
            else
            {
                renderer_.aoLightValueScratchXYZNNP = block_.getAmbientOcclusionLightValue(world_, x_ - 1, y_ - 1, z_);
                renderer_.aoBrightnessXYZNNP = block_.getMixedBrightnessForBlock(world_, x_ - 1, y_ - 1, z_);
            }

            if (!flag2 && !flag5)
            {
                renderer_.aoLightValueScratchXYZNPP = renderer_.aoLightValueScratchXZNP;
                renderer_.aoBrightnessXYZNPP = renderer_.aoBrightnessXZNP;
            }
            else
            {
                renderer_.aoLightValueScratchXYZNPP = block_.getAmbientOcclusionLightValue(world_, x_ - 1, y_ + 1, z_);
                renderer_.aoBrightnessXYZNPP = block_.getMixedBrightnessForBlock(world_, x_ - 1, y_ + 1, z_);
            }

            if (!flag3 && !flag4)
            {
                renderer_.aoLightValueScratchXYZPNP = renderer_.aoLightValueScratchXZPP;
                renderer_.aoBrightnessXYZPNP = renderer_.aoBrightnessXZPP;
            }
            else
            {
                renderer_.aoLightValueScratchXYZPNP = block_.getAmbientOcclusionLightValue(world_, x_ + 1, y_ - 1, z_);
                renderer_.aoBrightnessXYZPNP = block_.getMixedBrightnessForBlock(world_, x_ + 1, y_ - 1, z_);
            }

            if (!flag3 && !flag5)
            {
                renderer_.aoLightValueScratchXYZPPP = renderer_.aoLightValueScratchXZPP;
                renderer_.aoBrightnessXYZPPP = renderer_.aoBrightnessXZPP;
            }
            else
            {
                renderer_.aoLightValueScratchXYZPPP = block_.getAmbientOcclusionLightValue(world_, x_ + 1, y_ + 1, z_);
                renderer_.aoBrightnessXYZPPP = block_.getMixedBrightnessForBlock(world_, x_ + 1, y_ + 1, z_);
            }

            if (renderer_.renderMaxZ >= 1.0D)
            {
                --z_;
            }

            i1 = l;

            if (renderer_.renderMaxZ >= 1.0D || !world_.isBlockOpaqueCube(x_, y_, z_ + 1))
            {
                i1 = block_.getMixedBrightnessForBlock(world_, x_, y_, z_ + 1);
            }

            f7 = block_.getAmbientOcclusionLightValue(world_, x_, y_, z_ + 1);
            f9 = (renderer_.aoLightValueScratchXZNP + renderer_.aoLightValueScratchXYZNPP + f7 + renderer_.aoLightValueScratchYZPP) / 4.0F;
            f8 = (f7 + renderer_.aoLightValueScratchYZPP + renderer_.aoLightValueScratchXZPP + renderer_.aoLightValueScratchXYZPPP) / 4.0F;
            f11 = (renderer_.aoLightValueScratchYZNP + f7 + renderer_.aoLightValueScratchXYZPNP + renderer_.aoLightValueScratchXZPP) / 4.0F;
            f10 = (renderer_.aoLightValueScratchXYZNNP + renderer_.aoLightValueScratchXZNP + renderer_.aoLightValueScratchYZNP + f7) / 4.0F;
            f3 = (float)((double)f9 * renderer_.renderMaxY * (1.0D - renderer_.renderMinX) + (double)f8 * renderer_.renderMaxY * renderer_.renderMinX + (double)f11 * (1.0D - renderer_.renderMaxY) * renderer_.renderMinX + (double)f10 * (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMinX));
            f4 = (float)((double)f9 * renderer_.renderMinY * (1.0D - renderer_.renderMinX) + (double)f8 * renderer_.renderMinY * renderer_.renderMinX + (double)f11 * (1.0D - renderer_.renderMinY) * renderer_.renderMinX + (double)f10 * (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMinX));
            f5 = (float)((double)f9 * renderer_.renderMinY * (1.0D - renderer_.renderMaxX) + (double)f8 * renderer_.renderMinY * renderer_.renderMaxX + (double)f11 * (1.0D - renderer_.renderMinY) * renderer_.renderMaxX + (double)f10 * (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMaxX));
            f6 = (float)((double)f9 * renderer_.renderMaxY * (1.0D - renderer_.renderMaxX) + (double)f8 * renderer_.renderMaxY * renderer_.renderMaxX + (double)f11 * (1.0D - renderer_.renderMaxY) * renderer_.renderMaxX + (double)f10 * (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMaxX));
            k1 = renderer_.getAoBrightness(renderer_.aoBrightnessXZNP, renderer_.aoBrightnessXYZNPP, renderer_.aoBrightnessYZPP, i1);
            j1 = renderer_.getAoBrightness(renderer_.aoBrightnessYZPP, renderer_.aoBrightnessXZPP, renderer_.aoBrightnessXYZPPP, i1);
            i2 = renderer_.getAoBrightness(renderer_.aoBrightnessYZNP, renderer_.aoBrightnessXYZPNP, renderer_.aoBrightnessXZPP, i1);
            l1 = renderer_.getAoBrightness(renderer_.aoBrightnessXYZNNP, renderer_.aoBrightnessXZNP, renderer_.aoBrightnessYZNP, i1);
            renderer_.brightnessTopLeft = renderer_.mixAoBrightness(k1, l1, i2, j1, renderer_.renderMaxY * (1.0D - renderer_.renderMinX), (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMinX), (1.0D - renderer_.renderMaxY) * renderer_.renderMinX, renderer_.renderMaxY * renderer_.renderMinX);
            renderer_.brightnessBottomLeft = renderer_.mixAoBrightness(k1, l1, i2, j1, renderer_.renderMinY * (1.0D - renderer_.renderMinX), (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMinX), (1.0D - renderer_.renderMinY) * renderer_.renderMinX, renderer_.renderMinY * renderer_.renderMinX);
            renderer_.brightnessBottomRight = renderer_.mixAoBrightness(k1, l1, i2, j1, renderer_.renderMinY * (1.0D - renderer_.renderMaxX), (1.0D - renderer_.renderMinY) * (1.0D - renderer_.renderMaxX), (1.0D - renderer_.renderMinY) * renderer_.renderMaxX, renderer_.renderMinY * renderer_.renderMaxX);
            renderer_.brightnessTopRight = renderer_.mixAoBrightness(k1, l1, i2, j1, renderer_.renderMaxY * (1.0D - renderer_.renderMaxX), (1.0D - renderer_.renderMaxY) * (1.0D - renderer_.renderMaxX), (1.0D - renderer_.renderMaxY) * renderer_.renderMaxX, renderer_.renderMaxY * renderer_.renderMaxX);

            if (flag1)
            {
                renderer_.colorRedTopLeft = renderer_.colorRedBottomLeft = renderer_.colorRedBottomRight = renderer_.colorRedTopRight = par5 * 0.8F;
                renderer_.colorGreenTopLeft = renderer_.colorGreenBottomLeft = renderer_.colorGreenBottomRight = renderer_.colorGreenTopRight = par6 * 0.8F;
                renderer_.colorBlueTopLeft = renderer_.colorBlueBottomLeft = renderer_.colorBlueBottomRight = renderer_.colorBlueTopRight = par7 * 0.8F;
            }
            else
            {
                renderer_.colorRedTopLeft = renderer_.colorRedBottomLeft = renderer_.colorRedBottomRight = renderer_.colorRedTopRight = 0.8F;
                renderer_.colorGreenTopLeft = renderer_.colorGreenBottomLeft = renderer_.colorGreenBottomRight = renderer_.colorGreenTopRight = 0.8F;
                renderer_.colorBlueTopLeft = renderer_.colorBlueBottomLeft = renderer_.colorBlueBottomRight = renderer_.colorBlueTopRight = 0.8F;
            }

            renderer_.colorRedTopLeft *= f3;
            renderer_.colorGreenTopLeft *= f3;
            renderer_.colorBlueTopLeft *= f3;
            renderer_.colorRedBottomLeft *= f4;
            renderer_.colorGreenBottomLeft *= f4;
            renderer_.colorBlueBottomLeft *= f4;
            renderer_.colorRedBottomRight *= f5;
            renderer_.colorGreenBottomRight *= f5;
            renderer_.colorBlueBottomRight *= f5;
            renderer_.colorRedTopRight *= f6;
            renderer_.colorGreenTopRight *= f6;
            renderer_.colorBlueTopRight *= f6;

            if(ico_ != null)
            {
            	icon = ico_;
            }
            else
            {
            	icon = renderer_.getBlockIcon(block_, world_, x_, y_, z_, 3);
            }
            
            renderer_.renderFaceZPos(block_, (double)x_, (double)y_, (double)z_, renderer_.getBlockIcon(block_, world_, x_, y_, z_, 3));

            if (renderer_.fancyGrass && icon.getIconName().equals("grass_side") && !renderer_.hasOverrideBlockTexture())
            {
                renderer_.colorRedTopLeft *= par5;
                renderer_.colorRedBottomLeft *= par5;
                renderer_.colorRedBottomRight *= par5;
                renderer_.colorRedTopRight *= par5;
                renderer_.colorGreenTopLeft *= par6;
                renderer_.colorGreenBottomLeft *= par6;
                renderer_.colorGreenBottomRight *= par6;
                renderer_.colorGreenTopRight *= par6;
                renderer_.colorBlueTopLeft *= par7;
                renderer_.colorBlueBottomLeft *= par7;
                renderer_.colorBlueBottomRight *= par7;
                renderer_.colorBlueTopRight *= par7;
                renderer_.renderFaceZPos(block_, (double)x_, (double)y_, (double)z_, BlockGrass.getIconSideOverlay());
            }
        }
        
	}
	
	
	public void renderFaceAOYPos(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_)
	{
		this.renderFaceAOYPos(world_, renderer_, block_, x_, y_, z_, null);
	}
	
	public void renderFaceAOYPos(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_, Icon ico_)
	{
		
        int l2 = this.setupColour(world_, renderer_, block_, x_, y_, z_, 1);
        float par5 = (float)(l2 >> 16 & 255) / 255.0F;
        float par6 = (float)(l2 >> 8 & 255) / 255.0F;
        float par7 = (float)(l2 & 255) / 255.0F;
        
        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_, y_, z_);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer_.getBlockIcon(block_).getIconName().equals("grass_top"))
        {
            flag1 = false;
        }
        else if (renderer_.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;
        
        if (renderer_.renderMaxY >= 1.0D)
        {
            ++y_;
        }

        renderer_.aoBrightnessXYNP = block_.getMixedBrightnessForBlock(world_, x_ - 1, y_, z_);
        renderer_.aoBrightnessXYPP = block_.getMixedBrightnessForBlock(world_, x_ + 1, y_, z_);
        renderer_.aoBrightnessYZPN = block_.getMixedBrightnessForBlock(world_, x_, y_, z_ - 1);
        renderer_.aoBrightnessYZPP = block_.getMixedBrightnessForBlock(world_, x_, y_, z_ + 1);
        renderer_.aoLightValueScratchXYNP = block_.getAmbientOcclusionLightValue(world_, x_ - 1, y_, z_);
        renderer_.aoLightValueScratchXYPP = block_.getAmbientOcclusionLightValue(world_, x_ + 1, y_, z_);
        renderer_.aoLightValueScratchYZPN = block_.getAmbientOcclusionLightValue(world_, x_, y_, z_ - 1);
        renderer_.aoLightValueScratchYZPP = block_.getAmbientOcclusionLightValue(world_, x_, y_, z_ + 1);
        flag3 = Block.canBlockGrass[world_.getBlockId(x_ + 1, y_ + 1, z_)];
        flag2 = Block.canBlockGrass[world_.getBlockId(x_ - 1, y_ + 1, z_)];
        flag5 = Block.canBlockGrass[world_.getBlockId(x_, y_ + 1, z_ + 1)];
        flag4 = Block.canBlockGrass[world_.getBlockId(x_, y_ + 1, z_ - 1)];

        if (!flag4 && !flag2)
        {
            renderer_.aoLightValueScratchXYZNPN = renderer_.aoLightValueScratchXYNP;
            renderer_.aoBrightnessXYZNPN = renderer_.aoBrightnessXYNP;
        }
        else
        {
            renderer_.aoLightValueScratchXYZNPN = block_.getAmbientOcclusionLightValue(world_, x_ - 1, y_, z_ - 1);
            renderer_.aoBrightnessXYZNPN = block_.getMixedBrightnessForBlock(world_, x_ - 1, y_, z_ - 1);
        }

        if (!flag4 && !flag3)
        {
            renderer_.aoLightValueScratchXYZPPN = renderer_.aoLightValueScratchXYPP;
            renderer_.aoBrightnessXYZPPN = renderer_.aoBrightnessXYPP;
        }
        else
        {
            renderer_.aoLightValueScratchXYZPPN = block_.getAmbientOcclusionLightValue(world_, x_ + 1, y_, z_ - 1);
            renderer_.aoBrightnessXYZPPN = block_.getMixedBrightnessForBlock(world_, x_ + 1, y_, z_ - 1);
        }

        if (!flag5 && !flag2)
        {
            renderer_.aoLightValueScratchXYZNPP = renderer_.aoLightValueScratchXYNP;
            renderer_.aoBrightnessXYZNPP = renderer_.aoBrightnessXYNP;
        }
        else
        {
            renderer_.aoLightValueScratchXYZNPP = block_.getAmbientOcclusionLightValue(world_, x_ - 1, y_, z_ + 1);
            renderer_.aoBrightnessXYZNPP = block_.getMixedBrightnessForBlock(world_, x_ - 1, y_, z_ + 1);
        }

        if (!flag5 && !flag3)
        {
            renderer_.aoLightValueScratchXYZPPP = renderer_.aoLightValueScratchXYPP;
            renderer_.aoBrightnessXYZPPP = renderer_.aoBrightnessXYPP;
        }
        else
        {
            renderer_.aoLightValueScratchXYZPPP = block_.getAmbientOcclusionLightValue(world_, x_ + 1, y_, z_ + 1);
            renderer_.aoBrightnessXYZPPP = block_.getMixedBrightnessForBlock(world_, x_ + 1, y_, z_ + 1);
        }

        if (renderer_.renderMaxY >= 1.0D)
        {
            --y_;
        }

        i1 = l;

        if (renderer_.renderMaxY >= 1.0D || !world_.isBlockOpaqueCube(x_, y_ + 1, z_))
        {
            i1 = block_.getMixedBrightnessForBlock(world_, x_, y_ + 1, z_);
        }

        f7 = block_.getAmbientOcclusionLightValue(world_, x_, y_ + 1, z_);
        f6 = (renderer_.aoLightValueScratchXYZNPP + renderer_.aoLightValueScratchXYNP + renderer_.aoLightValueScratchYZPP + f7) / 4.0F;
        f3 = (renderer_.aoLightValueScratchYZPP + f7 + renderer_.aoLightValueScratchXYZPPP + renderer_.aoLightValueScratchXYPP) / 4.0F;
        f4 = (f7 + renderer_.aoLightValueScratchYZPN + renderer_.aoLightValueScratchXYPP + renderer_.aoLightValueScratchXYZPPN) / 4.0F;
        f5 = (renderer_.aoLightValueScratchXYNP + renderer_.aoLightValueScratchXYZNPN + f7 + renderer_.aoLightValueScratchYZPN) / 4.0F;
        renderer_.brightnessTopRight = renderer_.getAoBrightness(renderer_.aoBrightnessXYZNPP, renderer_.aoBrightnessXYNP, renderer_.aoBrightnessYZPP, i1);
        renderer_.brightnessTopLeft = renderer_.getAoBrightness(renderer_.aoBrightnessYZPP, renderer_.aoBrightnessXYZPPP, renderer_.aoBrightnessXYPP, i1);
        renderer_.brightnessBottomLeft = renderer_.getAoBrightness(renderer_.aoBrightnessYZPN, renderer_.aoBrightnessXYPP, renderer_.aoBrightnessXYZPPN, i1);
        renderer_.brightnessBottomRight = renderer_.getAoBrightness(renderer_.aoBrightnessXYNP, renderer_.aoBrightnessXYZNPN, renderer_.aoBrightnessYZPN, i1);
        renderer_.colorRedTopLeft = renderer_.colorRedBottomLeft = renderer_.colorRedBottomRight = renderer_.colorRedTopRight = par5;
        renderer_.colorGreenTopLeft = renderer_.colorGreenBottomLeft = renderer_.colorGreenBottomRight = renderer_.colorGreenTopRight = par6;
        renderer_.colorBlueTopLeft = renderer_.colorBlueBottomLeft = renderer_.colorBlueBottomRight = renderer_.colorBlueTopRight = par7;
        renderer_.colorRedTopLeft *= f3;
        renderer_.colorGreenTopLeft *= f3;
        renderer_.colorBlueTopLeft *= f3;
        renderer_.colorRedBottomLeft *= f4;
        renderer_.colorGreenBottomLeft *= f4;
        renderer_.colorBlueBottomLeft *= f4;
        renderer_.colorRedBottomRight *= f5;
        renderer_.colorGreenBottomRight *= f5;
        renderer_.colorBlueBottomRight *= f5;
        renderer_.colorRedTopRight *= f6;
        renderer_.colorGreenTopRight *= f6;
        renderer_.colorBlueTopRight *= f6;
        
        Icon icon;
        
        if(ico_ != null)
        {
        	icon = ico_;
        }
        else
        {
        	icon = renderer_.getBlockIcon(block_, world_, x_, y_, z_, 1);
        }
        
        renderer_.renderFaceYPos(block_, (double)x_, (double)y_, (double)z_, icon);
        
	}
	
	
	public void renderFaceAOYNeg(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_)
	{
		this.renderFaceAOYNeg(world_, renderer_, block_, x_, y_, z_, null);
	}
	
	public void renderFaceAOYNeg(IBlockAccess world_, RenderBlocks renderer_, Block block_, int x_, int y_, int z_, Icon ico_)
	{
        int l2 = this.setupColour(world_, renderer_, block_, x_, y_, z_, 0);
        float par5 = (float)(l2 >> 16 & 255) / 255.0F;
        float par6 = (float)(l2 >> 8 & 255) / 255.0F;
        float par7 = (float)(l2 & 255) / 255.0F;
        
        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_, y_, z_);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer_.getBlockIcon(block_).getIconName().equals("grass_top"))
        {
            flag1 = false;
        }
        else if (renderer_.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;

        if (renderer_.renderAllFaces || block_.shouldSideBeRendered(renderer_.blockAccess, x_, y_ - 1, z_, 0))
        {
            if (renderer_.renderMinY <= 0.0D)
            {
                --y_;
            }

            renderer_.aoBrightnessXYNN = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_ - 1, y_, z_);
            renderer_.aoBrightnessYZNN = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_, y_, z_ - 1);
            renderer_.aoBrightnessYZNP = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_, y_, z_ + 1);
            renderer_.aoBrightnessXYPN = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_ + 1, y_, z_);
            renderer_.aoLightValueScratchXYNN = block_.getAmbientOcclusionLightValue(renderer_.blockAccess, x_ - 1, y_, z_);
            renderer_.aoLightValueScratchYZNN = block_.getAmbientOcclusionLightValue(renderer_.blockAccess, x_, y_, z_ - 1);
            renderer_.aoLightValueScratchYZNP = block_.getAmbientOcclusionLightValue(renderer_.blockAccess, x_, y_, z_ + 1);
            renderer_.aoLightValueScratchXYPN = block_.getAmbientOcclusionLightValue(renderer_.blockAccess, x_ + 1, y_, z_);
            flag3 = Block.canBlockGrass[renderer_.blockAccess.getBlockId(x_ + 1, y_ - 1, z_)];
            flag2 = Block.canBlockGrass[renderer_.blockAccess.getBlockId(x_ - 1, y_ - 1, z_)];
            flag5 = Block.canBlockGrass[renderer_.blockAccess.getBlockId(x_, y_ - 1, z_ + 1)];
            flag4 = Block.canBlockGrass[renderer_.blockAccess.getBlockId(x_, y_ - 1, z_ - 1)];

            if (!flag4 && !flag2)
            {
                renderer_.aoLightValueScratchXYZNNN = renderer_.aoLightValueScratchXYNN;
                renderer_.aoBrightnessXYZNNN = renderer_.aoBrightnessXYNN;
            }
            else
            {
                renderer_.aoLightValueScratchXYZNNN = block_.getAmbientOcclusionLightValue(renderer_.blockAccess, x_ - 1, y_, z_ - 1);
                renderer_.aoBrightnessXYZNNN = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_ - 1, y_, z_ - 1);
            }

            if (!flag5 && !flag2)
            {
                renderer_.aoLightValueScratchXYZNNP = renderer_.aoLightValueScratchXYNN;
                renderer_.aoBrightnessXYZNNP = renderer_.aoBrightnessXYNN;
            }
            else
            {
                renderer_.aoLightValueScratchXYZNNP = block_.getAmbientOcclusionLightValue(renderer_.blockAccess, x_ - 1, y_, z_ + 1);
                renderer_.aoBrightnessXYZNNP = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_ - 1, y_, z_ + 1);
            }

            if (!flag4 && !flag3)
            {
                renderer_.aoLightValueScratchXYZPNN = renderer_.aoLightValueScratchXYPN;
                renderer_.aoBrightnessXYZPNN = renderer_.aoBrightnessXYPN;
            }
            else
            {
                renderer_.aoLightValueScratchXYZPNN = block_.getAmbientOcclusionLightValue(renderer_.blockAccess, x_ + 1, y_, z_ - 1);
                renderer_.aoBrightnessXYZPNN = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_ + 1, y_, z_ - 1);
            }

            if (!flag5 && !flag3)
            {
                renderer_.aoLightValueScratchXYZPNP = renderer_.aoLightValueScratchXYPN;
                renderer_.aoBrightnessXYZPNP = renderer_.aoBrightnessXYPN;
            }
            else
            {
                renderer_.aoLightValueScratchXYZPNP = block_.getAmbientOcclusionLightValue(renderer_.blockAccess, x_ + 1, y_, z_ + 1);
                renderer_.aoBrightnessXYZPNP = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_ + 1, y_, z_ + 1);
            }

            if (renderer_.renderMinY <= 0.0D)
            {
                ++y_;
            }

            i1 = l;

            if (renderer_.renderMinY <= 0.0D || !renderer_.blockAccess.isBlockOpaqueCube(x_, y_ - 1, z_))
            {
                i1 = block_.getMixedBrightnessForBlock(renderer_.blockAccess, x_, y_ - 1, z_);
            }

            f7 = block_.getAmbientOcclusionLightValue(renderer_.blockAccess, x_, y_ - 1, z_);
            f3 = (renderer_.aoLightValueScratchXYZNNP + renderer_.aoLightValueScratchXYNN + renderer_.aoLightValueScratchYZNP + f7) / 4.0F;
            f6 = (renderer_.aoLightValueScratchYZNP + f7 + renderer_.aoLightValueScratchXYZPNP + renderer_.aoLightValueScratchXYPN) / 4.0F;
            f5 = (f7 + renderer_.aoLightValueScratchYZNN + renderer_.aoLightValueScratchXYPN + renderer_.aoLightValueScratchXYZPNN) / 4.0F;
            f4 = (renderer_.aoLightValueScratchXYNN + renderer_.aoLightValueScratchXYZNNN + f7 + renderer_.aoLightValueScratchYZNN) / 4.0F;
            renderer_.brightnessTopLeft = renderer_.getAoBrightness(renderer_.aoBrightnessXYZNNP, renderer_.aoBrightnessXYNN, renderer_.aoBrightnessYZNP, i1);
            renderer_.brightnessTopRight = renderer_.getAoBrightness(renderer_.aoBrightnessYZNP, renderer_.aoBrightnessXYZPNP, renderer_.aoBrightnessXYPN, i1);
            renderer_.brightnessBottomRight = renderer_.getAoBrightness(renderer_.aoBrightnessYZNN, renderer_.aoBrightnessXYPN, renderer_.aoBrightnessXYZPNN, i1);
            renderer_.brightnessBottomLeft = renderer_.getAoBrightness(renderer_.aoBrightnessXYNN, renderer_.aoBrightnessXYZNNN, renderer_.aoBrightnessYZNN, i1);

            if (flag1)
            {
                renderer_.colorRedTopLeft = renderer_.colorRedBottomLeft = renderer_.colorRedBottomRight = renderer_.colorRedTopRight = par5 * 0.5F;
                renderer_.colorGreenTopLeft = renderer_.colorGreenBottomLeft = renderer_.colorGreenBottomRight = renderer_.colorGreenTopRight = par6 * 0.5F;
                renderer_.colorBlueTopLeft = renderer_.colorBlueBottomLeft = renderer_.colorBlueBottomRight = renderer_.colorBlueTopRight = par7 * 0.5F;
            }
            else
            {
                renderer_.colorRedTopLeft = renderer_.colorRedBottomLeft = renderer_.colorRedBottomRight = renderer_.colorRedTopRight = 0.5F;
                renderer_.colorGreenTopLeft = renderer_.colorGreenBottomLeft = renderer_.colorGreenBottomRight = renderer_.colorGreenTopRight = 0.5F;
                renderer_.colorBlueTopLeft = renderer_.colorBlueBottomLeft = renderer_.colorBlueBottomRight = renderer_.colorBlueTopRight = 0.5F;
            }

            renderer_.colorRedTopLeft *= f3;
            renderer_.colorGreenTopLeft *= f3;
            renderer_.colorBlueTopLeft *= f3;
            renderer_.colorRedBottomLeft *= f4;
            renderer_.colorGreenBottomLeft *= f4;
            renderer_.colorBlueBottomLeft *= f4;
            renderer_.colorRedBottomRight *= f5;
            renderer_.colorGreenBottomRight *= f5;
            renderer_.colorBlueBottomRight *= f5;
            renderer_.colorRedTopRight *= f6;
            renderer_.colorGreenTopRight *= f6;
            renderer_.colorBlueTopRight *= f6;
            
            Icon icon;
            
            if(ico_ != null)
            {
            	icon = ico_;
            }
            else
            {
            	icon = renderer_.getBlockIcon(block_, world_, x_, y_, z_, 0);
            }
            
	        
            renderer_.renderFaceYNeg(block_, (double)x_, (double)y_, (double)z_, icon);
        }
        

	}
	
	public void tessellateInventoryBlock(RenderBlocks renderer_, Block block_, int side_, int meta_)
	{
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer_.renderFaceYNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(side_, meta_));
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer_.renderFaceYPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(side_, meta_));
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer_.renderFaceZNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(side_, meta_));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer_.renderFaceZPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(side_, meta_));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer_.renderFaceXNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(side_, meta_));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer_.renderFaceXPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(side_, meta_));
	}
	
	public void tessellateInventoryBlock(RenderBlocks renderer_, Block block_, int meta_)
	{
		this.tessellateInventoryBlock(renderer_, block_, 0, meta_);
	}
	
	public void setGL11Scale(double scale_)
	{
		GL11.glScaled(scale_, scale_, scale_);
	}
	
	public void resetGL11Scale( )
	{
		GL11.glScaled(1.0, 1.0, 1.0);
	}
	
    public static void renderItemAsBlock(Tessellator tess_, ItemStack istack_, boolean effects_, float par7)
    {

    	Item item = istack_.getItem();
    	Icon icon = item.getIcon(istack_, 0);
    	
    	//pass 0
    	renderItemAsBlockSimple(tess_, icon, par7);
    	
		if(item.requiresMultipleRenderPasses())
		{
			Icon icon2 = item.getIcon(istack_, 0);
            for(int x = 1; x < istack_.getItem().getRenderPasses(istack_.getItemDamage()); x++)
            {
            	icon2 = item.getIcon(istack_, x);
            	renderItemAsBlockSimple(tess_, icon2, par7);
            }		
		}
		
		
		
        for(int x = 0; x < istack_.getItem().getRenderPasses(istack_.getItemDamage()); x++)
        {
			if(istack_.hasEffect(x))
			{
				renderItemEffectRelative(tess_);
			}
        }
    }
    
    public static void renderItemAsBlockSimple(Tessellator tess_, Icon ico_, float par7)
    {
    	if(ico_ != null)
    	{
    		ItemRenderer.renderItemIn2D(tess_, ico_.getMinU(), ico_.getMinV(), ico_.getMaxU(), ico_.getMaxV(), ico_.getIconWidth(), ico_.getIconHeight(), par7);
    	}
    }
    
    public static void renderItemIn2D(Tessellator tess_, float minu_, float minv_, float maxu_, float maxv_, int iconWidth_, int iconHeight_, float par7)
    {
    	//Z
        tess_.startDrawingQuads();
        tess_.setColorOpaque_F(0.8F, 0.8F, 0.8F);
        tess_.setNormal(0.0F, 0.0F, 1.0F);
        tess_.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)minu_, (double)maxv_);
        tess_.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)maxu_, (double)maxv_);
        tess_.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)maxu_, (double)minv_);
        tess_.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)minu_, (double)minv_);
        tess_.draw();
        
        
        tess_.startDrawingQuads();
        tess_.setColorOpaque_F(0.8F, 0.8F, 0.8F);
        tess_.setNormal(0.0F, 0.0F, -1.0F);
        tess_.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - par7), (double)minu_, (double)minv_);
        tess_.addVertexWithUV(1.0D, 1.0D, (double)(0.0F - par7), (double)maxu_, (double)minv_);
        tess_.addVertexWithUV(1.0D, 0.0D, (double)(0.0F - par7), (double)maxu_, (double)maxv_);
        tess_.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - par7), (double)minu_, (double)maxv_);
        tess_.draw();
        float f5 = 0.5F * (minu_ - maxu_) / (float)iconWidth_;
        float f6 = 0.5F * (maxv_ - minv_) / (float)iconHeight_;
        tess_.startDrawingQuads();
        tess_.setNormal(-1.0F, 0.0F, 0.0F);
        int k;
        float f7;
        float f8;
        
        //WEST -X
        tess_.setColorOpaque_F(0.6F, 0.6F, 0.6F);
        for (k = 0; k < iconWidth_; ++k)
        {
            f7 = (float)k / (float)iconWidth_;
            f8 = minu_ + (maxu_ - minu_) * f7 - f5;
            tess_.addVertexWithUV((double)f7, 0.0D, (double)(0.0F - par7), (double)f8, (double)maxv_);
            tess_.addVertexWithUV((double)f7, 0.0D, 0.0D, (double)f8, (double)maxv_);
            tess_.addVertexWithUV((double)f7, 1.0D, 0.0D, (double)f8, (double)minv_);
            tess_.addVertexWithUV((double)f7, 1.0D, (double)(0.0F - par7), (double)f8, (double)minv_);
        }

        tess_.draw();
        tess_.startDrawingQuads();
        
        //EAST +X
        tess_.setNormal(1.0F, 0.0F, 0.0F);
        tess_.setColorOpaque_F(0.6F, 0.6F, 0.6F);
        float f9;

        for (k = 0; k < iconWidth_; ++k)
        {
            f7 = (float)k / (float)iconWidth_;
            f8 = minu_ + (maxu_ - minu_) * f7 - f5;
            f9 = f7 + 1.0F / (float)iconWidth_;
            tess_.addVertexWithUV((double)f9, 1.0D, (double)(0.0F - par7), (double)f8, (double)minv_);
            tess_.addVertexWithUV((double)f9, 1.0D, 0.0D, (double)f8, (double)minv_);
            tess_.addVertexWithUV((double)f9, 0.0D, 0.0D, (double)f8, (double)maxv_);
            tess_.addVertexWithUV((double)f9, 0.0D, (double)(0.0F - par7), (double)f8, (double)maxv_);
        }

        tess_.draw();
        
        //UP? +Y?
        tess_.startDrawingQuads();
        tess_.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        tess_.setNormal(0.0F, 1.0F, 0.0F);

        for (k = 0; k < iconHeight_; ++k)
        {
            f7 = (float)k / (float)iconHeight_;
            f8 = maxv_ + (minv_ - maxv_) * f7 - f6;
            f9 = f7 + 1.0F / (float)iconHeight_;
            tess_.addVertexWithUV(0.0D, (double)f9, 0.0D, (double)minu_, (double)f8);
            tess_.addVertexWithUV(1.0D, (double)f9, 0.0D, (double)maxu_, (double)f8);
            tess_.addVertexWithUV(1.0D, (double)f9, (double)(0.0F - par7), (double)maxu_, (double)f8);
            tess_.addVertexWithUV(0.0D, (double)f9, (double)(0.0F - par7), (double)minu_, (double)f8);
        }

        tess_.draw();
        
        //DOWN? -Y?
        tess_.startDrawingQuads();
        tess_.setColorOpaque_F(0.5F, 0.5F, 0.5F);
        tess_.setNormal(0.0F, -1.0F, 0.0F);

        for (k = 0; k < iconHeight_; ++k)
        {
            f7 = (float)k / (float)iconHeight_;
            f8 = maxv_ + (minv_ - maxv_) * f7 - f6;
            tess_.addVertexWithUV(1.0D, (double)f7, 0.0D, (double)maxu_, (double)f8);
            tess_.addVertexWithUV(0.0D, (double)f7, 0.0D, (double)minu_, (double)f8);
            tess_.addVertexWithUV(0.0D, (double)f7, (double)(0.0F - par7), (double)minu_, (double)f8);
            tess_.addVertexWithUV(1.0D, (double)f7, (double)(0.0F - par7), (double)maxu_, (double)f8);
        }

        tess_.draw();
    }
	
	public static void renderItemEffectRelative(Tessellator tessellator_)
	{
		tessellator_.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		tessellator_.setBrightness(1000);
        GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        textureManager.bindTexture(RES_ITEM_GLINT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        float f7 = 0.76F;
        GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPushMatrix();
        float f8 = 0.125F;
        GL11.glScalef(f8, f8, f8);
        float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
        GL11.glTranslatef(f9, 0.0F, 0.0F);
        GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(tessellator_, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(f8, f8, f8);
        f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
        GL11.glTranslatef(-f9, 0.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(tessellator_, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        
        textureManager.bindTexture(TextureMap.locationItemsTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public void translationNorth(double[] coords_)
	{
		double t = coords_[0];
		coords_[0] = coords_[2];
		coords_[2] = t;
		
		t = coords_[3];
		coords_[3] = coords_[5];
		coords_[5] = t;
		
 	}
	
	public void recalculateTESRLighting(TileEntity t_)
	{
        int l = t_.getWorldObj().getLightBrightnessForSkyBlocks(t_.xCoord, t_.yCoord, t_.zCoord, 0);
        int i1 = l % 65536;
        int j1 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)i1, (float)j1);
	}
	
	  /**
     * Renders the given texture to the east (x-positive) face of the block.  Args: block, x, y, z, texture
     */
	/*
    public void renderFaceXPos(RenderBlocks renderer_, Block block_, double x_, double y_, double z_, Icon icon_, double xoff_, double yoff_)
    {


        if(renderer_.hasOverrideBlockTexture())
        {
            icon_ = renderer_.overrideBlockTexture;
        }
        
        renderer_.enableAO = true;
        
        //interpolated is the internal coords of a texture, 8 px in!
        double minu = icon_.getInterpolatedU(0 + xoff_);
        double maxu = icon_.getMaxU();
        double minv = icon_.getMinV();
        double maxv = icon_.getInterpolatedV(0 + yoff_);
        
        double x = x_ + renderer_.renderMaxX;
        
        if (renderer_.enableAO)
        {
            tessellator.setColorOpaque_F(renderer_.colorRedBottomLeft, renderer_.colorGreenBottomLeft, renderer_.colorBlueBottomLeft);
            tessellator.setBrightness(renderer_.brightnessBottomLeft);
            tessellator.addVertexWithUV(x, y_ + renderer_.renderMinY, z_ + renderer_.renderMaxZ, minu, maxv);
            
            tessellator.setColorOpaque_F(renderer_.colorRedBottomRight, renderer_.colorGreenBottomRight, renderer_.colorBlueBottomRight);
            tessellator.setBrightness(renderer_.brightnessBottomRight);
            tessellator.addVertexWithUV(x, y_ + renderer_.renderMinY, z_ + renderer_.renderMinZ, maxu, maxv);
            
            tessellator.setColorOpaque_F(renderer_.colorRedTopRight, renderer_.colorGreenTopRight, renderer_.colorBlueTopRight);
            tessellator.setBrightness(renderer_.brightnessTopRight);
            tessellator.addVertexWithUV(x, y_ + renderer_.renderMaxY, z_ + renderer_.renderMinZ, maxu, minv);
        	
            tessellator.setColorOpaque_F(renderer_.colorRedTopLeft, renderer_.colorGreenTopLeft, renderer_.colorBlueTopLeft);
            tessellator.setBrightness(renderer_.brightnessTopLeft);
            tessellator.addVertexWithUV(x, y_ + renderer_.renderMaxY, z_ + renderer_.renderMaxZ, minu, minv);


        }
        else
        {
            tessellator.addVertexWithUV(x, y_ + renderer_.renderMinY, z_ + renderer_.renderMaxZ, minu, maxv);      
            tessellator.addVertexWithUV(x, y_ + renderer_.renderMinY, z_ + renderer_.renderMinZ, maxu, maxv);
            tessellator.addVertexWithUV(x, y_ + renderer_.renderMaxY, z_ + renderer_.renderMinZ, maxu, minv);
            tessellator.addVertexWithUV(x, y_ + renderer_.renderMaxY, z_ + renderer_.renderMaxZ, minu, minv);
        }
    }
    */
    
    public void renderFaceXPos(Block block_, RenderBlocks renderer_, double x_, double y_, double z_, Icon icon_, double uoff_, double voff_, int texMode_)
    {
        Tessellator tessellator = Tessellator.instance;

        if (renderer_.hasOverrideBlockTexture())
        {
            icon_ = renderer_.overrideBlockTexture;
        }
        
        double d3 = (double)icon_.getInterpolatedU(renderer_.renderMinZ * 16.0D);
        double d4 = (double)icon_.getInterpolatedU(renderer_.renderMaxZ * 16.0D);
        double d5 = (double)icon_.getInterpolatedV(16.0D - renderer_.renderMaxY * 16.0D);
        double d6 = (double)icon_.getInterpolatedV(16.0D - renderer_.renderMinY * 16.0D);
        double d7;
        
        //static texture not based on block dimensions
        if(texMode_ == 1)
        {
	        d3 = (double)icon_.getInterpolatedU(0.0D + uoff_);
	        d4 = (double)icon_.getMaxU();
	        d5 = (double)icon_.getMinV();
	        d6 = (double)icon_.getInterpolatedV(0.0D + voff_);        	
        }

        if (renderer_.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (renderer_.renderMinZ < 0.0D || renderer_.renderMaxZ > 1.0D)
        {
            d3 = (double)icon_.getMinU();
            d4 = (double)icon_.getMaxU();
        }

        if (renderer_.renderMinY < 0.0D || renderer_.renderMaxY > 1.0D)
        {
            d5 = (double)icon_.getMinV();
            d6 = (double)icon_.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (renderer_.uvRotateSouth == 2)
        {
            d3 = (double)icon_.getInterpolatedU(renderer_.renderMinY * 16.0D);
            d5 = (double)icon_.getInterpolatedV(16.0D - renderer_.renderMinZ * 16.0D);
            d4 = (double)icon_.getInterpolatedU(renderer_.renderMaxY * 16.0D);
            d6 = (double)icon_.getInterpolatedV(16.0D - renderer_.renderMaxZ * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (renderer_.uvRotateSouth == 1)
        {
            d3 = (double)icon_.getInterpolatedU(16.0D - renderer_.renderMaxY * 16.0D);
            d5 = (double)icon_.getInterpolatedV(renderer_.renderMaxZ * 16.0D);
            d4 = (double)icon_.getInterpolatedU(16.0D - renderer_.renderMinY * 16.0D);
            d6 = (double)icon_.getInterpolatedV(renderer_.renderMinZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (renderer_.uvRotateSouth == 3)
        {
            d3 = (double)icon_.getInterpolatedU(16.0D - renderer_.renderMinZ * 16.0D);
            d4 = (double)icon_.getInterpolatedU(16.0D - renderer_.renderMaxZ * 16.0D);
            d5 = (double)icon_.getInterpolatedV(renderer_.renderMaxY * 16.0D);
            d6 = (double)icon_.getInterpolatedV(renderer_.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = x_ + renderer_.renderMaxX;
        double d12 = y_ + renderer_.renderMinY;
        double d13 = y_ + renderer_.renderMaxY;
        double d14 = z_ + renderer_.renderMinZ;
        double d15 = z_ + renderer_.renderMaxZ;

        if (renderer_.enableAO)
        {
            tessellator.setColorOpaque_F(renderer_.colorRedTopLeft, renderer_.colorGreenTopLeft, renderer_.colorBlueTopLeft);
            tessellator.setBrightness(renderer_.brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
            tessellator.setColorOpaque_F(renderer_.colorRedBottomLeft, renderer_.colorGreenBottomLeft, renderer_.colorBlueBottomLeft);
            tessellator.setBrightness(renderer_.brightnessBottomLeft);
            tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
            tessellator.setColorOpaque_F(renderer_.colorRedBottomRight, renderer_.colorGreenBottomRight, renderer_.colorBlueBottomRight);
            tessellator.setBrightness(renderer_.brightnessBottomRight);
            tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
            tessellator.setColorOpaque_F(renderer_.colorRedTopRight, renderer_.colorGreenTopRight, renderer_.colorBlueTopRight);
            tessellator.setBrightness(renderer_.brightnessTopRight);
            tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
        }
        else
        {
            tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
            tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
            tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
            tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
        }
    }
    
    
    public void setRenderBounds(RenderBlocks renderer, BoundSet points)
    {
    	renderer.setRenderBounds(points.x1, points.y1, points.z1, points.x2, points.y2, points.z2);
    }
    
    public PointSet translate(ForgeDirection from, ForgeDirection to, final PointSet points)
    {
    	PointSet ps = new PointSet(points);
    	return translator.translate(from, to, ps);
    }
    
    //This assumes a natural direction of NORTH
    public PointSet centerForInventoryRender(PointSet ps, float shift)
    {
    	ps.z1 = ps.z1 + shift;
    	ps.z2 = ps.z2 + shift;
    	
    	return ps;
    }

}
