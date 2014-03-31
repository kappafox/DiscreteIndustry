package kappafox.di.decorative.blocks;

import ic2.api.item.Items;
import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.decorative.tileentities.TileEntityFixtureBlock;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockStrut extends SubBlock
{
	private static Icon FIXTURE_TEXTURE;
	private static float tolerance = 0.3F;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ireg_)
	{	
		FIXTURE_TEXTURE = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
	}

	@Override
	public Icon getIcon(int side_, int meta_) 
	{
		return FIXTURE_TEXTURE;
	}

	@Override
	public boolean onBlockActivated(World world_, int xcoord_, int ycoord_, int zcoord_, EntityPlayer player_, int side_, float hitx_, float hity_, float hitz_) 
	{
		
		//System.out.println(side_ + "," + hitx_ + "," + hity_ + "," + hitz_);
		
		TileEntityFixtureBlock tile = (TileEntityFixtureBlock)world_.getBlockTileEntity(xcoord_, ycoord_, zcoord_);

		
		if(tile == null)
		{
			return false;
		}
		
		ItemStack inhand = player_.inventory.getCurrentItem();
		ItemStack wrench = Items.getItem("wrench");
		ItemStack ewrench = Items.getItem("electricWrench");
		

		
		boolean ignore = false;
		if(inhand != null)
		{
			
			if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
			{
				
				boolean isGTWrench = false;
				if(DiscreteIndustry.GTWrench != null)
				{
					if(inhand.getItem().getClass().getName().equalsIgnoreCase(DiscreteIndustry.GTWrench.getName()));
					{
						isGTWrench = true;
					}
				}
			
				if(isGTWrench || inhand.getDisplayName().equalsIgnoreCase(wrench.getDisplayName()) || inhand.getDisplayName().equalsIgnoreCase(ewrench.getDisplayName()))
				{			
					
					if(side_ != 0 && side_ != 1)
					{
						if(hity_ >= 1.0F - tolerance)
						{
							tile.toggleConnection((short)1);
							ignore = true;
						}
						
						if(hity_ <= tolerance)
						{
							tile.toggleConnection((short)0);
							ignore = true;
						}
					}
					else
					{
						if(hitz_ <= tolerance)
						{
							tile.toggleConnection((short)2);
						}
						
						if(hitz_ >= 1.0F - tolerance)
						{
							tile.toggleConnection((short)3);
						}
						
						if(hitx_ <= tolerance)
						{
							tile.toggleConnection((short)4);
						}
						
						if(hitx_ >= 1.0F - tolerance)
						{
							tile.toggleConnection((short)5);
						}
					}
					
					if((side_ == 4 || side_ == 5) && ignore == false)
					{
						if(hitz_ <= tolerance)
						{
							tile.toggleConnection((short)2);	
						}
						
						if(hitz_ >= 1.0F - tolerance)
						{
							tile.toggleConnection((short)3);	
						}
					}
					
					if((side_ == 2 || side_ == 3) && ignore == false)
					{
						if(hitx_ <= tolerance)
						{
							tile.toggleConnection((short)4);	
						}
						
						if(hitx_ >= 1.0F - tolerance)
						{
							tile.toggleConnection((short)5);	
						}
					}
					
					
					world_.markBlockForUpdate(xcoord_, ycoord_, zcoord_);
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world_, int x_, int y_, int z_, int side_) 
	{
		return this.getIcon(side_, 0);
	}
	
	@Override
	public Icon getOverloadedIcon(int side_, int meta_)
	{
		return this.getIcon(side_, meta_);
	}
	


	@Override
	public TileEntity createTileEntity(World world_, int meta_) 
	{
		return new TileEntityFixtureBlock();
	}

	@Override
	public boolean hasTileEntity(int meta_) 
	{
		return true;
	}
	
	@Override
	public boolean isDiscrete() 
	{
		return true;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess block_, int x_, int y_, int z_, int side_) 
	{
		return true;
	}
}
