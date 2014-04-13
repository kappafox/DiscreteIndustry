package kappafox.di.decorative.blocks;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.BoundSet;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SubBlockLadder extends SubBlock
{
	
	private static Icon indLadderSide;
	private static Icon indLadderTop;
	
	private static Icon claLadderSide;
	private static Icon claLadderTop;
	
	private static Icon footLadderSide;
	
	private static Icon poleLadderSide;
	
	private static Icon ropeLadderSide;
	
	private static Icon testIcon;

	@Override
	public void registerIcons(IconRegister ireg) 
	{
		super.registerIcons(ireg);
		indLadderSide = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockIndustrialLadder");
		indLadderTop = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockIndustrialLadder_top");
		
		claLadderSide = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockClassicLadder");
		claLadderTop = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
		
		footLadderSide = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockFootholdLadder");
		
		poleLadderSide = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockPoleLadder");
		
		ropeLadderSide = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockRope");
	}

	@Override
	public Icon getIcon(int side, int meta) 
	{
		return DEFAULT_ICON;
	}
	
	@Override
	public Icon getOverloadedIcon(int side, int meta)
	{
		if(meta == 800)
		{
			return footLadderSide;
		}
		
		if(meta == 801)
		{
			if(side == 1 || side == 0)
			{
				return footLadderSide;
			}
			return poleLadderSide;
		}
		
		if(meta == 802)
		{
			return poleLadderSide;
		}
		
		if(meta == 803)
		{
			return ropeLadderSide;
		}
		
		if(meta == 804)
		{
			return footLadderSide;
		}
		
		if(meta == 805)
		{
			return testIcon;
		}
		
		if(meta == 806)
		{
			if(side == 1 || side == 0)
			{
				return claLadderTop;
			}
			return claLadderSide;		
		}
		
		if(meta == 807)
		{
			if(side == 1 || side == 0)
			{
				return indLadderTop;
			}
			return indLadderSide;
		}
		
		return this.getIcon(1, 0);
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) 
	{
		TileEntitySubtype tile = (TileEntitySubtype)world.getBlockTileEntity(x, y, z);
		int type = tile.getSubtype();
		
		return this.getOverloadedIcon(side, type);
	}
	
	@Override
	public boolean isLadder(World world, int x, int y, int z, EntityLivingBase entity)
	{
		return true;
	}
	
	@Override
	public void getCollisionBoxes(World world, int x, int y, int z, AxisAlignedBB mask, List boxlist, Entity entity)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getBlockTileEntity(x, y, z);
    	
    	AxisAlignedBB box = null;
    	
    	if(tile != null)
    	{
    		int direction = tile.getDirection();
        	switch(direction)
        	{
            	case 2:
            		box = AxisAlignedBB.getAABBPool().getAABB(x + px.zero, y + px.zero, z + px.zero, x + px.sixteen, y + px.sixteen, z + px.two);	
            		break;
            	case 3:
            		box = AxisAlignedBB.getAABBPool().getAABB(x + px.zero, y + px.zero, z + px.fourteen, x + px.sixteen, y + px.sixteen, z + px.sixteen);
            		break;
            	case 4:
            		box = AxisAlignedBB.getAABBPool().getAABB(x + px.zero, y + px.zero, z + px.zero, x + px.two, y + px.sixteen, z + px.sixteen);
            		break;
            	case 5:
            		box = AxisAlignedBB.getAABBPool().getAABB(x + px.fourteen, y + px.zero, z + px.zero, x + px.sixteen, y + px.sixteen, z + px.sixteen);
            		break;
        	}
        	
        	
        	if(box != null)
        	{
	        	if(mask.intersectsWith(box) == true)
	        	{
	        		boxlist.add(box);
	        	}
        	}
    	}
    }
	
	@Override
	public AxisAlignedBB getWireframeBox(World world, int x, int y, int z) 
	{
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getBlockTileEntity(x, y, z);
    	
    	AxisAlignedBB box = null;
    	
    	if(tile != null)
    	{
    		int direction = tile.getDirection();
    		
    		
        	switch(direction)
        	{
            	case 2:
            		return AxisAlignedBB.getAABBPool().getAABB(x + px.zero, y + px.zero, z + px.zero, x + px.sixteen, y + px.sixteen, z + px.two);	
            	case 3:
            		return AxisAlignedBB.getAABBPool().getAABB(x + px.zero, y + px.zero, z + px.fourteen, x + px.sixteen, y + px.sixteen, z + px.sixteen);
            	case 4:
            		return AxisAlignedBB.getAABBPool().getAABB(x + px.zero, y + px.zero, z + px.zero, x + px.two, y + px.sixteen, z + px.sixteen);
            	case 5:
            		return AxisAlignedBB.getAABBPool().getAABB(x + px.fourteen, y + px.zero, z + px.zero, x + px.sixteen, y + px.sixteen, z + px.sixteen);
        	}
        	
    	}
    	
    	 return AxisAlignedBB.getAABBPool().getAABB((double)x, (double)y, (double)z, (double)x + px.sixteen, (double)y + px.sixteen, z + px.sixteen);
	}
	
   
	@Override
    public BoundSet getHitBoxesBasedOnState(IBlockAccess world, int x, int y, int z)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getBlockTileEntity(x, y, z);
    	
    	AxisAlignedBB box = null;
    	
    	if(tile != null)
    	{
    		int direction = tile.getDirection();
    		
    		
        	switch(direction)
        	{
            	case 2:
            		return new BoundSet(px.zero, px.zero, px.zero, px.sixteen, px.sixteen, px.two);		
            	case 3:
            		return new BoundSet(px.zero, px.zero, px.fourteen, px.sixteen, px.sixteen, + px.sixteen);
            	case 4:
            		return new BoundSet(px.zero, px.zero, px.zero, px.two, px.sixteen, px.sixteen);
            	case 5:
            		return new BoundSet(px.fourteen, px.zero, px.zero, px.sixteen, px.sixteen, px.sixteen);
        	}
        	
    	}
    	
    	return new BoundSet(0,0,0,1,1,1);
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
	
	@Override
	public boolean isDiscrete( )
	{
		return true;
	}
}
