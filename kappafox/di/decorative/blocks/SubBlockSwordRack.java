package kappafox.di.decorative.blocks;

import ic2.api.item.Items;

import java.util.List;
import java.util.Random;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SubBlockSwordRack extends SubBlock 
{
	
	private static Icon side;
	private static Icon top;
	
	@Override
	public void registerIcons(IconRegister ireg_) 
	{
		side = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockSwordRack_side");
		top = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockSwordRack_top");

	}

	@Override
	public Icon getIcon(int side_, int meta_) 
	{
		if(side_ == 0 || side_ == 1)
		{
			return top;	
		}
		
		return side;
	}
	
	@Override
	public Icon getOverloadedIcon(int side_, int meta_)
	{
		return this.getIcon(side_, meta_);
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world_, int x_, int y_, int z_, int side_) 
	{
		return this.getIcon(side_, 0);
	}
	
	
	@Override	
	public TileEntity createTileEntity(World world_, int meta_)
	{
		return new TileEntitySwordRack(6);
	}
	
	@Override	
	public boolean hasTileEntity(int meta_)
	{
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world_, int xcoord_, int ycoord_, int zcoord_, EntityPlayer player_, int side_, float par7, float par8, float par9)
	{
		ItemStack held = player_.inventory.getCurrentItem();
		
		if(held != null)
		{
			if(held.itemID == Items.getItem("obscurator").itemID)
			{
				return true;
			}
		}
		player_.openGui(DiscreteIndustry.instance, 2, world_, xcoord_, ycoord_, zcoord_);	
		return true;
	}
	
	@Override
	public boolean isDiscrete( )
	{
		return true;
	}
	
	@Override
    public void breakBlock(World world_, int x_, int y_, int z_, int id_, int meta_)
    {
        Random rand = new Random();

        TileEntity tileEntity = world_.getBlockTileEntity(x_, y_, z_);
        if (!(tileEntity instanceof IInventory)) 
        {
        	
        }
        else
        {
	        IInventory inventory = (IInventory)tileEntity;
	
	        for (int i = 0; i < inventory.getSizeInventory(); i++) 
	        {
                ItemStack item = inventory.getStackInSlot(i);

                if (item != null && item.stackSize > 0) 
                {
                    float rx = rand.nextFloat() * 0.8F + 0.1F;
                    float ry = rand.nextFloat() * 0.8F + 0.1F;
                    float rz = rand.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityItem = new EntityItem(world_, x_ + rx, y_ + ry, z_ + rz, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

                    if (item.hasTagCompound()) 
                    {
                    	entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                    }

                    float factor = 0.05F;
                    entityItem.motionX = rand.nextGaussian() * factor;
                    entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                    entityItem.motionZ = rand.nextGaussian() * factor;
                    world_.spawnEntityInWorld(entityItem);
                    item.stackSize = 0;
                }
	        }  
        }
    }
	
	
	@Override
	public AxisAlignedBB getWireframeBox(World world_, int x_, int y_, int z_) 
	{
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
    	
    	if(tile != null)
    	{
    		int direction = tile.getDirection();
    		
    		if(direction == 2 || direction == 3)
    		{
    			return AxisAlignedBB.getAABBPool().getAABB(x_ + px.zero, y_ + px.zero, z_ + px.four, x_ + px.sixteen, y_ + px.sixteen, z_ + px.twelve);		
    		}
    		else
    		{
    			return AxisAlignedBB.getAABBPool().getAABB(x_ + px.four, y_ + px.zero, z_ + px.zero, x_ + px.twelve, y_ + px.sixteen, z_ + px.sixteen);    			
    		}
    		
    	}
        
    	return null;
	}
	
	@Override
    public void getCollisionBoxes(World world_, int x_, int y_, int z_, AxisAlignedBB mask_, List boxlist_, Entity entity_)
    {
		
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
    	AxisAlignedBB box = null;
    	
    	if(tile != null)
    	{
    		
    		int direction = tile.getDirection();
    		
    		if(direction == 2 || direction == 3)
    		{
    			box = AxisAlignedBB.getAABBPool().getAABB(x_ + px.zero, y_ + px.zero, z_ + px.four, x_ + px.sixteen, y_ + px.sixteen, z_ + px.twelve);			
    		}
    		else
    		{
    			box = AxisAlignedBB.getAABBPool().getAABB(x_ + px.four, y_ + px.zero, z_ + px.zero, x_ + px.twelve, y_ + px.sixteen, z_ + px.sixteen);  			
    		}
    		
    	}
    	
        if (box != null && mask_.intersectsWith(box))
        {
            boxlist_.add(box);
        }
    }


}
