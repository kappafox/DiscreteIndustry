package kappafox.di.base.blocks;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.util.BoundSet;
import kappafox.di.base.util.PixelSet;
import kappafox.di.base.util.PointSet;
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
	protected static final PixelSet px = PixelSet.getInstance();
	
	protected static Icon DEFAULT_ICON;
	
	public void registerIcons(IconRegister ireg)
	{
		DEFAULT_ICON = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
	}
	
	public abstract Icon getIcon(int side, int meta);
	public abstract Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side);
	
	
    public void breakBlock(World world, int x, int y, int z, int id, int meta)
    {
    	
    }
    
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack istack)
	{
		
	}
	
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta)
	{
		return meta;
	}
	
	public boolean onBlockActivated(World world, int xcoord, int ycoord, int zcoord, EntityPlayer player, int side, float par7, float par8, float par9)
	{
		//player.openGui(DiscreteIndustry.instance, 1, world, xcoord, ycoord, zcoord);
		return false;
	}
	
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player, float hitx, float hity, float hitz)
	{
		
	}
	
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return side == 0 && px.zero > 0.0D ? true : (side == 1 && px.sixteen < 1.0D ? true : (side == 2 && px.zero > 0.0D ? true : (side == 3 && px.sixteen < 1.0D ? true : (side == 4 && px.one > 0.0D ? true : (side == 5 && px.sixteen < 1.0D ? true : !world.isBlockOpaqueCube(x, y, z))))));
	}
	
	public TileEntity createTileEntity(World world, int meta)
	{
		return null;
	}
	
	public boolean hasTileEntity(int meta)
	{
		return false;
	}

    public boolean isLadder(World world, int x, int y, int z, EntityLivingBase entity)
    {
        return false;
    }
    
    public boolean isDiscrete( )
    {
    	return false;
    }
    
    
    /*
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return AxisAlignedBB.getAABBPool().getAABB((double)x + 0, (double)y + 0, (double)z + 0, (double)x + 1, (double)y + 1, (double)z + 1);
    }
    */
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
    	return AxisAlignedBB.getAABBPool().getAABB((double)x + 0, (double)y + 0, (double)z + 0, (double)x + 1, (double)y + 1, (double)z + 1);
    }
    
    
    public void getCollisionBoxes(World world, int x, int y, int z, AxisAlignedBB mask, List boxlist, Entity entity)
    {
        AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(world, x, y, z);

        if (axisalignedbb1 != null && mask.intersectsWith(axisalignedbb1))
        {
            boxlist.add(axisalignedbb1);
        }
    }
    
    
    
    public BoundSet getHitBoxesBasedOnState(IBlockAccess world, int x, int y, int z)
    {
    	return new PointSet(0,0,0,1,1,1);
    }
    
	public AxisAlignedBB getWireframeBox(World world, int x, int y, int z) 
	{
		return AxisAlignedBB.getBoundingBox(x + 0, y + 0, z + 0, x + 1, y + 1, z + 1);
	}
    
    
	public void onBlockAdded(World world, int x, int y, int z) 
	{
		
	}
	
	public double getMinX( )
	{
		return px.one;
	}
	
	public double getMaxX( )
	{
		return px.sixteen;
	}
	
	public double getMinY( )
	{
		return px.one;
	}
	
	public double getMaxY( )
	{
		return px.sixteen;
	}	
	
	public double getMinZ( )
	{
		return px.one;
	}
	
	public double getMaxZ( )
	{
		return px.sixteen;
	}
	
	public Icon getOverloadedIcon(int side, int meta)
	{
		return getIcon(side, meta);
	}
	
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		return true;
	}
	
    public boolean isBlockNormalCube(World world, int x, int y, int z)
    {
    	return false;
    }

	public Icon getSpecialIcon(int index, int meta)
	{
		return this.getIcon(0, meta);
	}
}
