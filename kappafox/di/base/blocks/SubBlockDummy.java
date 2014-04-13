package kappafox.di.base.blocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import kappafox.di.DiscreteIndustry;

public class SubBlockDummy extends SubBlock
{

	private static Icon DEFAULT_ICON;
	
	@Override
	public void registerIcons(IconRegister ireg)
	{
		DEFAULT_ICON = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		return DEFAULT_ICON;
	}
	
	public Icon getOverloadedIcon(int side, int meta)
	{
		return this.getIcon(0, 0);
	}
	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		return this.getIcon(0, 0);
	}

}
