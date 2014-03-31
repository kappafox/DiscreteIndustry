package kappafox.di.decorative.blocks;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SubBlockLoom extends SubBlock
{

	@Override
	public void registerIcons(IconRegister ireg_) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Icon getIcon(int side_, int meta_) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world_, int x_, int y_, int z_, int side_) 
	{
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world_, int xcoord_, int ycoord_, int zcoord_, EntityPlayer player_, int side_, float par7, float par8, float par9)
	{
		player_.openGui(DiscreteIndustry.instance, 1, world_, xcoord_, ycoord_, zcoord_);
		return true;
	}
	
    @Override
	public TileEntity createTileEntity(World world_, int meta_)
	{
		return new TileEntityLoomBlock();
	}
	
	
	@Override
	public boolean hasTileEntity(int meta_)
	{
		return true;
	}
	
}
