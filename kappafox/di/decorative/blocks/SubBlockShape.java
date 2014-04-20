package kappafox.di.decorative.blocks;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.util.BoundSet;
import kappafox.di.base.util.PointSet;

public class SubBlockShape extends SubBlock
{
	
	private static final PointSet SLAB = new PointSet(px.zero, px.zero, px.zero, px.sixteen, px.eight, px.sixteen);
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		return DEFAULT_ICON;
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		return DEFAULT_ICON;
	}
	
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
    	return AxisAlignedBB.getAABBPool().getAABB(x + SLAB.x1,  y + SLAB.y1, z + SLAB.z1, x + SLAB.x2,  y + SLAB.y2, z + SLAB.z2);
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
    	return SLAB;
    }
    
	public AxisAlignedBB getWireframeBox(World world, int x, int y, int z) 
	{
   	 	return AxisAlignedBB.getAABBPool().getAABB(x + SLAB.x1,  y + SLAB.y1, z + SLAB.z1, x + SLAB.x2,  y + SLAB.y2, z + SLAB.z2);
	}
	
	@Override
	public boolean isDiscrete( )
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntityDiscreteBlock();
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{
		return true;
	}

}
