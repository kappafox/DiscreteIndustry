package kappafox.di.decorative.blocks;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SubBlockStairs extends SubBlock
{
	
	private static Icon STAIR_TEXTURE;
	private static Icon STAIR_SMALL_TEXTURE;
	private static Icon STAIR_TEXTURE_TOP;
	private static Icon STAIR_TEXTURE_SIDE;
	private static Icon STAIR_TEXTURE_BACK;
	@Override
	public void registerIcons(IconRegister ireg_) 
	{
		STAIR_TEXTURE = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteStairs");
		STAIR_SMALL_TEXTURE = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteSmallStairs");
		
		STAIR_TEXTURE_TOP = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteStairs_top");
		STAIR_TEXTURE_SIDE = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteStairs_side");
		STAIR_TEXTURE_BACK = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteStairs_back");
	}

	
	@Override
	public Icon getIcon(int side_, int meta_) 
	{
		/*
		if(side_ == 0 || side_ == 1)
		{
			return STAIR_TEXTURE_TOP;
		}
		
		if(side_ == 4 || side_ == 5)
		{
			return STAIR_TEXTURE_SIDE;
		}
		
		if(side_ == 7)
		{
			return STAIR_TEXTURE_BACK;
		}
		*/
		
		if(meta_ == 862)
		{
			return STAIR_SMALL_TEXTURE;
		}
		return STAIR_TEXTURE;
	}
	
	@Override
	public Icon getOverloadedIcon(int side_, int meta_)
	{
		return this.getIcon(side_, meta_);
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world_, int x_, int y_, int z_, int side_) 
	{
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
		
		if(tile != null)
		{
			return this.getIcon(0, tile.getSubtype());
			/*
			int dir = tile.getDirection();
			
			if(dir == 2)
			{
				switch(side_)
				{
					case 0:
						return this.getIcon(0, 0);
					case 1:
						return this.getIcon(1, 0);
					case 2:
						return this.getIcon(7, 0);
					case 3:
						return this.getIcon(3, 0);
					case 4:
						return new IconFlipped(this.getIcon(4, 0), true, false);
					case 5:
						return this.getIcon(5, 0);
						
						
				}
			}
			
			if(dir == 3)
			{
				if(side_ == 1 || side_ == 0)
				{
					return this.getIcon(1, 0);
				}
				
				if(side_ == 4 || side_ == 5)
				{
					return new IconFlipped(this.getIcon(4, 0), true, false);
				}
			}
			*/
		}
		return STAIR_TEXTURE;
	}
	
	@Override
    public boolean isDiscrete( )
    {
    	return true;
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
	public void getCollisionBoxes(World world_, int x_, int y_, int z_, AxisAlignedBB mask_, List boxlist_, Entity entity_)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
    	int subtype = tile.getSubtype();
    	
    	AxisAlignedBB box = null;
    	AxisAlignedBB box2 = null;
    	
    	
    	if(tile != null)
    	{
    		int direction = tile.getDirection();
    		
    		double y1 = px.dzero;
    		double y2 = px.deight;
    		double y3 = y2;
    		double y4 = px.dsixteen;
    		
    		//stairs are upside down
    		if(tile.getVariable() == 1)
    		{
    			y1 = px.deight;
    			y2 = px.dsixteen;
    			y3 = px.dzero;
    			y4 = px.deight;
    		}

    		//base box
            box = AxisAlignedBB.getAABBPool().getAABB((double)x_ + px.zero, (double)y_ + y1, (double)z_ + px.zero, (double)x_ + px.sixteen, (double)y_ + y2, (double)z_ + px.sixteen);	

        	
        	if(box != null)
        	{
	        	if(mask_.intersectsWith(box) == true)
	        	{
	        		boxlist_.add(box);
	        	}
        	}
        	
        	double d1 = px.dzero;
        	double d2 = px.dsixteen;
        	boolean con = false;
        	switch(direction)
        	{
        		//
	        	case 2:
	        	{

					int dir2 = SubBlockStairs.shouldStairsConnect(world_, x_, y_, z_ - 1, tile);
					
					if(dir2 == 4)
					{
						d2 = px.eight;
					}
					
					if(dir2 == 5)
					{
						d1 = px.eight;
					}
					
					box = AxisAlignedBB.getAABBPool().getAABB((double)x_ + d1, (double)y_ + y3, (double)z_ + px.zero, (double)x_ + d2, (double)y_ + y4, (double)z_ + px.eight);
					
					
					int dir3 = SubBlockStairs.shouldStairsConnect(world_, x_, y_, z_ + 1, tile);
					
					if(dir3 == 4)
					{
						box2 = AxisAlignedBB.getAABBPool().getAABB(x_ + px.dzero, y_ + y3, z_ + px.eight, x_ + px.eight, y_ + y4, z_+ px.sixteen);
					}
					
					if(dir3 == 5)
					{
						box2 = AxisAlignedBB.getAABBPool().getAABB(x_ + px.eight, y_ + y3, z_ + px.eight, x_ + px.sixteen, y_ + y4, z_+ px.sixteen);
					}

	        		break;
	        	}
	        	
	        	case 3:
	        	{

					int dir2 = SubBlockStairs.shouldStairsConnect(world_, x_, y_, z_ + 1, tile);

					
					if(dir2 == 4)
					{
						d2 = px.eight;
					}
					
					if(dir2 == 5)
					{
						d1 = px.eight;
					}
					
	        		box = AxisAlignedBB.getAABBPool().getAABB((double)x_ + d1, (double)y_ + y3, (double)z_ + px.eight, (double)x_ + d2, (double)y_ + y4, (double)z_ + px.sixteen);
	        		
					int dir3 = SubBlockStairs.shouldStairsConnect(world_, x_, y_, z_ + 1, tile);
					
					if(dir3 == 4)
					{
						box2 = AxisAlignedBB.getAABBPool().getAABB(x_ + px.zero, y_ + y3, z_ + px.zero, x_ + px.eight, y_ + y4, z_+ px.eight);
					}
					
					if(dir3 == 5)
					{
						box2 = AxisAlignedBB.getAABBPool().getAABB(x_ + px.eight, y_ + y3, z_ + px.zero, x_ + px.sixteen, y_ + y4, z_+ px.eight);
					}
					
					break;
	        	}
	        	
	        	case 4:
	        	{
					int dir2 = SubBlockStairs.shouldStairsConnect(world_, x_ - 1, y_, z_, tile);
					
					if(dir2 == 2)
					{
						d2 = px.eight;
					}
					
					if(dir2 == 3)
					{
						d1 = px.eight;
					}
					
	        		box = AxisAlignedBB.getAABBPool().getAABB((double)x_ + px.zero, (double)y_ + y3, (double)z_ + d1, (double)x_ + px.eight, (double)y_ + y4, (double)z_ + d2);
	        		
					int dir3 = SubBlockStairs.shouldStairsConnect(world_, x_ + 1, y_, z_, tile);
					
					if(dir3 == 2)
					{
						box2 = AxisAlignedBB.getAABBPool().getAABB(x_ + px.eight, y_ + y3, z_ + px.zero, x_ + px.sixteen, y_ + y4, z_+ px.eight);
					}
					
					if(dir3 == 3)
					{
						box2 = AxisAlignedBB.getAABBPool().getAABB(x_ + px.eight, y_ + y3, z_ + px.eight, x_ + px.sixteen, y_ + y4, z_+ px.sixteen);
					}
					
	        		break;
	        	}
	        	
	        	case 5:
	        	{
	        		int dir2 = SubBlockStairs.shouldStairsConnect(world_, x_ + 1, y_, z_, tile);
	        		
					if(dir2 == 2)
					{
						d2 = px.eight;
					}
					
					if(dir2 == 3)
					{
						d1 = px.eight;
					}
					
	        		
	        		box = AxisAlignedBB.getAABBPool().getAABB((double)x_ + px.eight, (double)y_ + y3, (double)z_ + d1, (double)x_ + px.sixteen, (double)y_ + y4, (double)z_ + d2);
	        		
					int dir3 = SubBlockStairs.shouldStairsConnect(world_, x_ - 1, y_, z_, tile);
					
					if(dir3 == 2)
					{
						box2 = AxisAlignedBB.getAABBPool().getAABB(x_ + px.zero, y_ + y3, z_ + px.zero, x_ + px.eight, y_ + y4, z_+ px.eight);
					}
					
					if(dir3 == 3)
					{
						box2 = AxisAlignedBB.getAABBPool().getAABB(x_ + px.zero, y_ + y3, z_ + px.eight, x_ + px.eight, y_ + y4, z_+ px.sixteen);
					}
					
	        		break;
	        	}
	        	
	        	default:
	        		box = AxisAlignedBB.getAABBPool().getAABB((double)x_ + px.zero, (double)y_ + px.eight, (double)z_ + px.zero, (double)x_ + px.sixteen, (double)y_ + px.sixteen, (double)z_ + px.sixteen);
        	}
            //box = AxisAlignedBB.getAABBPool().getAABB((double)x_ + zeroPx, (double)y_ + eightPx, (double)z_ + zeroPx, (double)x_ + sixteenPx, (double)y_ + sixteenPx, (double)z_ + eightPx);	

        	
        	if(box != null)
        	{
	        	if(mask_.intersectsWith(box) == true)
	        	{
	        		boxlist_.add(box);
	        	}
        	}
        	
        	if(box2 != null)
        	{
	        	if(mask_.intersectsWith(box2) == true)
	        	{
	        		boxlist_.add(box2);
	        	}
        	}
        	
    	}
    }
	
	
	public static int shouldStairsConnect(World world_, int x_, int y_, int z_, TileEntityDiscreteBlock stair_)
	{
		TileEntity tile = world_.getBlockTileEntity(x_, y_, z_);
		
		if(tile != null && tile instanceof TileEntityDiscreteBlock && stair_ != null)
		{
			TileEntityDiscreteBlock tile2 = (TileEntityDiscreteBlock)tile;
			
			if(tile2.getSubtype() == stair_.getSubtype())
			{
				//int dir = stair_.getDirection();
				return tile2.getDirection();
				
				
			}
		}
		return -1;
	}

}
