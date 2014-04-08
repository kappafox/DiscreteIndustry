package kappafox.di.base;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.IBlockAccess;
import kappafox.di.base.util.PixelSet;

public abstract class SubBlockRenderingHandler 
{
	protected static final PixelSet px = PixelSet.getInstance();	
	protected static final DiscreteRenderHelper drh = new DiscreteRenderHelper();
	
	protected static final Tessellator tessellator = Tessellator.instance;
	protected static final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
	
	public abstract boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer); 
	public abstract void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer);

}
