package kappafox.di.base.blocks;

import java.util.List;

import kappafox.di.base.util.BoundSet;
import kappafox.di.base.util.PixelSet;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public abstract class SubBlock
{
	
	public double minX = 0.0;
	public double maxX = 1.0;
	public double minY = 0.0;
	public double maxY = 1.0;
	public double minZ = 0.0;
	public double maxZ = 1.0;
	
	protected static final PixelSet px = PixelSet.getInstance();
	
	
	//public abstract boolean isDiscrete();
	public abstract void registerIcons(IconRegister ireg_);
	public abstract Icon getIcon(int side_, int meta_);
	//public abstract boolean onBlockActivated(World world_, int xcoord_, int ycoord_, int zcoord_, EntityPlayer player_, int side_, float par7, float par8, float par9);
	public abstract Icon getBlockTexture(IBlockAccess world_, int x_, int y_, int z_, int side_);
	//public abstract void onBlockPlacedBy(World world_, int x_, int y_, int z_, EntityLivingBase player_, ItemStack istack_);
	//public abstract TileEntity createTileEntity(World world_, int meta_);
	//public abstract boolean hasTileEntity(int meta_);
	//public abstract boolean shouldSideBeRendered(IBlockAccess block_, int x_, int y_, int z_, int side_);
	
	
    public void breakBlock(World world_, int x_, int y_, int z_, int id_, int meta_)
    {
    	
    }
	public void onBlockPlacedBy(World world_, int x_, int y_, int z_, EntityLivingBase player_, ItemStack istack_)
	{
		
	}
	
	public int onBlockPlaced(World world_, int x_, int y_, int z_, int side_, float hitx_, float hity_, float hitz_, int meta_)
	{
		return meta_;
	}
	
	public boolean onBlockActivated(World world_, int xcoord_, int ycoord_, int zcoord_, EntityPlayer player_, int side_, float par7, float par8, float par9)
	{
		return false;
	}
	
	public boolean shouldSideBeRendered(IBlockAccess world_, int x_, int y_, int z_, int side_)
	{
		return side_ == 0 && this.minY > 0.0D ? true : (side_ == 1 && this.maxY < 1.0D ? true : (side_ == 2 && this.minZ > 0.0D ? true : (side_ == 3 && this.maxZ < 1.0D ? true : (side_ == 4 && this.minX > 0.0D ? true : (side_ == 5 && this.maxX < 1.0D ? true : !world_.isBlockOpaqueCube(x_, y_, z_))))));
	}
	
	public TileEntity createTileEntity(World world_, int meta_)
	{
		return null;
	}
	
	public boolean hasTileEntity(int meta_)
	{
		return false;
	}

    public boolean isLadder(World world_, int x_, int y_, int z_, EntityLivingBase entity_)
    {
        return false;
    }
    
    public boolean isDiscrete( )
    {
    	return false;
    }
    
    
    /*
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world_, int x_, int y_, int z_)
    {
        return AxisAlignedBB.getAABBPool().getAABB((double)x_ + 0, (double)y_ + 0, (double)z_ + 0, (double)x_ + 1, (double)y_ + 1, (double)z_ + 1);
    }
    */
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world_, int x_, int y_, int z_)
    {
    	return AxisAlignedBB.getAABBPool().getAABB((double)x_ + 0, (double)y_ + 0, (double)z_ + 0, (double)x_ + 1, (double)y_ + 1, (double)z_ + 1);
    }
    
    
    public void getCollisionBoxes(World world_, int x_, int y_, int z_, AxisAlignedBB mask_, List boxlist_, Entity entity_)
    {
        AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(world_, x_, y_, z_);

        if (axisalignedbb1 != null && mask_.intersectsWith(axisalignedbb1))
        {
            boxlist_.add(axisalignedbb1);
        }
    }
    
    
    
    public BoundSet getHitBoxesBasedOnState(IBlockAccess world_, int x_, int y_, int z_)
    {
    	return new BoundSet(0,0,0,1,1,1);
    }
    
	public AxisAlignedBB getWireframeBox(World world_, int x_, int y_, int z_) 
	{
		return AxisAlignedBB.getBoundingBox(x_ + 0, y_ + 0, z_ + 0, x_ + 1, y_ + 1, z_ + 1);
	}
    
    
	public void onBlockAdded(World world_, int x_, int y_, int z_) 
	{
		
	}
	
	public double getMinX( )
	{
		return minX;
	}
	
	public double getMaxX( )
	{
		return maxX;
	}
	
	public double getMinY( )
	{
		return minY;
	}
	
	public double getMaxY( )
	{
		return maxY;
	}	
	
	public double getMinZ( )
	{
		return minZ;
	}
	
	public double getMaxZ( )
	{
		return maxX;
	}
	
	public Icon getOverloadedIcon(int side_, int meta_)
	{
		return null;
	}
	

		
	public boolean isBlockSolidOnSide(World world, int x_, int y_, int z_, ForgeDirection side_)
	{
		return true;
	}
	
    public boolean isBlockNormalCube(World world_, int x_, int y_, int z_)
    {
    	return false;
    }
}
