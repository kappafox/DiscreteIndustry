package kappafox.di.decorative.blocks;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
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
	public void registerIcons(IconRegister ireg_) 
	{
		indLadderSide = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockIndustrialLadder");
		indLadderTop = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockIndustrialLadder_top");
		
		claLadderSide = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockClassicLadder");
		claLadderTop = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
		
		footLadderSide = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockFootholdLadder");
		
		poleLadderSide = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockPoleLadder");
		
		ropeLadderSide = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockRope");
		
		testIcon = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "flagPart");
	}

	@Override
	public Icon getIcon(int side_, int meta_) 
	{
		return testIcon;
	}
	
	@Override
	public Icon getOverloadedIcon(int side_, int meta_)
	{
		if(meta_ == 800)
		{
			return footLadderSide;
		}
		
		if(meta_ == 801)
		{
			if(side_ == 1 || side_ == 0)
			{
				return footLadderSide;
			}
			return poleLadderSide;
		}
		
		if(meta_ == 802)
		{
			return poleLadderSide;
		}
		
		if(meta_ == 803)
		{
			return ropeLadderSide;
		}
		
		if(meta_ == 804)
		{
			return footLadderSide;
		}
		
		if(meta_ == 805)
		{
			return testIcon;
		}
		
		if(meta_ == 806)
		{
			if(side_ == 1 || side_ == 0)
			{
				return claLadderTop;
			}
			return claLadderSide;		
		}
		
		if(meta_ == 807)
		{
			if(side_ == 1 || side_ == 0)
			{
				return indLadderTop;
			}
			return indLadderSide;
		}
		
		return this.getIcon(1, 0);
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world_, int x_, int y_, int z_, int side_) 
	{
		return testIcon;
	}
	
	@Override
	public boolean isLadder(World world_, int x_, int y_, int z_, EntityLivingBase entity_)
	{
		return true;
	}
	
	@Override
	public void getCollisionBoxes(World world_, int x_, int y_, int z_, AxisAlignedBB mask_, List boxlist_, Entity entity_)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
    	
    	AxisAlignedBB box = null;
    	
    	if(tile != null)
    	{
    		int direction = tile.getVariable();
    		
        	switch(direction)
        	{
            	case 2:
            		box = AxisAlignedBB.getAABBPool().getAABB(x_ + px.zero, y_ + px.zero, z_ + px.zero, x_ + px.sixteen, y_ + px.sixteen, z_ + px.two);	
            		break;
            	case 3:
            		box = AxisAlignedBB.getAABBPool().getAABB(x_ + px.zero, y_ + px.zero, z_ + px.fourteen, x_ + px.sixteen, y_ + px.sixteen, z_ + px.sixteen);
            		break;
            	case 4:
            		box = AxisAlignedBB.getAABBPool().getAABB(x_ + px.zero, y_ + px.zero, z_ + px.zero, x_ + px.two, y_ + px.sixteen, z_ + px.sixteen);
            		break;
            	case 5:
            		box = AxisAlignedBB.getAABBPool().getAABB(x_ + px.fourteen, y_ + px.zero, z_ + px.zero, x_ + px.sixteen, y_ + px.sixteen, z_ + px.sixteen);
            		break;
        	}
        	
        	
        	if(box != null)
        	{
	        	if(mask_.intersectsWith(box) == true)
	        	{
	        		boxlist_.add(box);
	        	}
        	}
    	}
    }
	
	@Override
	public AxisAlignedBB getWireframeBox(World world_, int x_, int y_, int z_) 
	{
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
    	
    	AxisAlignedBB box = null;
    	
    	if(tile != null)
    	{
    		int direction = tile.getVariable();
    		
    		
        	switch(direction)
        	{
            	case 2:
            		return AxisAlignedBB.getAABBPool().getAABB(x_ + px.zero, y_ + px.zero, z_ + px.zero, x_ + px.sixteen, y_ + px.sixteen, z_ + px.two);	
            	case 3:
            		return AxisAlignedBB.getAABBPool().getAABB(x_ + px.zero, y_ + px.zero, z_ + px.fourteen, x_ + px.sixteen, y_ + px.sixteen, z_ + px.sixteen);
            	case 4:
            		return AxisAlignedBB.getAABBPool().getAABB(x_ + px.zero, y_ + px.zero, z_ + px.zero, x_ + px.two, y_ + px.sixteen, z_ + px.sixteen);
            	case 5:
            		return AxisAlignedBB.getAABBPool().getAABB(x_ + px.fourteen, y_ + px.zero, z_ + px.zero, x_ + px.sixteen, y_ + px.sixteen, z_ + px.sixteen);
        	}
        	
    	}
    	
    	 return AxisAlignedBB.getAABBPool().getAABB((double)x_, (double)y_, (double)z_, (double)x_ + px.sixteen, (double)y_ + px.sixteen, z_ + px.sixteen);
	}
	
   
	@Override
    public BoundSet getHitBoxesBasedOnState(IBlockAccess world_, int x_, int y_, int z_)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
    	
    	AxisAlignedBB box = null;
    	
    	if(tile != null)
    	{
    		int direction = tile.getVariable();
    		
    		
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
	public TileEntity createTileEntity(World world_, int meta_)
	{
		return new TileEntityDiscreteBlock();
	}
	
	
	@Override
	public boolean hasTileEntity(int meta_)
	{
		return true;
	}
	
	@Override
	public boolean isDiscrete( )
	{
		return true;
	}
}
