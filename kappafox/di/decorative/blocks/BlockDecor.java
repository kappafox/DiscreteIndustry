





package kappafox.di.decorative.blocks;

import java.util.List;
import java.util.Random;

import kappafox.di.base.blocks.BlockDiscreteBlock;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.util.BoundSet;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecor extends BlockDiscreteBlock
{
	
	private static final short SUB_BLOCKS = 7;
	private static SubBlock[] blocks;
	
	private static final double zeroPx = 0.0;
	private static final double onePx = 0.0625;
	private static final double twoPx = onePx * 2;
	private static final double threePx = onePx * 3;
	private static final double fourPx = onePx * 4;
	private static final double fivePx = onePx * 5;
	private static final double sixPx = onePx * 6;
	private static final double sevenPx = onePx * 7;
	private static final double eightPx = onePx * 8;
	private static final double ninePx = onePx * 9;
	private static final double tenPx = onePx * 10;
	private static final double elevenPx = onePx * 11;
	private static final double twelvePx = onePx * 12;
	private static final double thirteenPx = onePx * 13;
	private static final double fourteenPx = onePx * 14;
	private static final double fifteenPx = onePx * 15;
	private static final double sixteenPx = onePx * 16;
	
	
	@SideOnly(Side.CLIENT)
	private int rid;
	
	public BlockDecor(int id_, Material mat_, int renderID_)
	{
		super(id_, mat_, renderID_);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setUnlocalizedName("decorBlock");
		this.setHardness(3.0F);
		
		blocks = new SubBlock[SUB_BLOCKS];
		
		blocks[0] = new SubBlockStrut();
		blocks[1] = new SubBlockLadder();
		blocks[2] = new SubBlockLadder();
		blocks[3] = new SubBlockLoom();
		blocks[4] = new SubBlockSwordRack();
		blocks[5] = new SubBlockStairs();
		blocks[6] = new SubBlockStrut();
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
		{
			rid = renderID_;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ireg_)
	{
		//super.registerIcons(ireg_);
		for(SubBlock b: blocks)
		{
			b.registerIcons(ireg_);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id_, CreativeTabs tabs_, List list_)
	{
		// blockid, count, meta	
		
		for(int i = 0; i < SUB_BLOCKS; i++)
		{
			list_.add(new ItemStack(id_, 1, 3));
			/*
			if(i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 6)
			{
				
			}
			else
			{
				list_.add(new ItemStack(id_, 1, i));
			}
			*/
		}		
	}
	
	
	@SideOnly(Side.CLIENT)
	public Icon getCustomIcon(int side_, int meta_, int offset_)
	{
		return getIcon(side_, meta_ + offset_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side_, int meta_)
	{
		if(meta_ > 15)
		{
			if(meta_ >= 800 && meta_ <= 820)
			{
				return blocks[2].getOverloadedIcon(side_, meta_);
			}
			
			if(meta_ >= 821 && meta_ <= 840)
			{
				return blocks[4].getOverloadedIcon(side_, meta_);
			}
			
			if(meta_ >= 861 && meta_ <= 870)
			{
				return blocks[5].getOverloadedIcon(side_, meta_);
			}
			
			if(meta_ >= 871 && meta_ <= 880)
			{
				return blocks[6].getOverloadedIcon(side_, meta_);
			}
		}
		else
		{
			return blocks[meta_].getIcon(side_, meta_);
		}
		
		return null;
	}

	
	@Override
    public boolean onBlockActivated(World world_, int xcoord_, int ycoord_, int zcoord_, EntityPlayer player_, int side_, float par7, float par8, float par9)
    {	
		int meta = world_.getBlockMetadata(xcoord_, ycoord_, zcoord_);
		return blocks[meta].onBlockActivated(world_, xcoord_, ycoord_, zcoord_, player_, side_, par7, par8, par9);
		
    }

    

	
	@Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess world_, int x_, int y_, int z_, int side_)
    {	
		int meta = world_.getBlockMetadata(x_, y_, z_);
		
		if(blocks[meta].isDiscrete() == true)
		{
			TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
			
			if(tile != null)
			{
				if((tile.getTextureSource(side_) == tile.getOriginalID()) && (tile.getTextureSourceMeta(side_) == tile.getSubType()))
				{
					return blocks[meta].getBlockTexture(world_, x_, y_, z_, side_);
				}
			}
			
			return super.getBlockTexture(world_, x_, y_, z_, side_);
		}
		return blocks[meta].getBlockTexture(world_, x_, y_, z_, side_);	
    }
	
	@Override
	public void onBlockPlacedBy(World world_, int x_, int y_, int z_, EntityLivingBase player_, ItemStack istack_)
	{
		
		int meta = world_.getBlockMetadata(x_, y_, z_);
		if(blocks[meta].isDiscrete() == true)
		{
			super.onBlockPlacedBy(world_, x_, y_, z_, player_, istack_);
		}
		
		blocks[meta].onBlockPlacedBy(world_, x_, y_, z_, player_, istack_);
	}
	
	public void onBlockAdded(World world_, int x_, int y_, int z_) 
	{
		int meta = world_.getBlockMetadata(x_, y_, z_);
		blocks[meta].onBlockAdded(world_, x_, y_, z_);
	}
	
	@Override
	public int onBlockPlaced(World world_, int x_, int y_, int z_, int side_, float hitx_, float hity_, float hitz_, int meta_)
	{
		return blocks[meta_].onBlockPlaced(world_, x_, y_, z_, side_, hitx_, hity_, hitz_, meta_);
	}
	
	@Override
    public void breakBlock(World world_, int x_, int y_, int z_, int id_, int meta_)
    {
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
		
		if(tile != null)
		{
			int type = tile.getSubType();
			
			ItemStack item = new ItemStack(this, 1, type);
			super.dropBlockAsItem_do(world_, x_, y_, z_, item);
		}

		blocks[meta_].breakBlock(world_, x_, y_, z_, id_, meta_);
		super.breakBlock(world_, x_, y_, z_, id_, meta_);
    }
	
	@Override
    public int quantityDropped(int meta_, int fortune_, Random rng_)
    {
		//we will drop our own items because of overloading
		return 0;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType( )
	{
		return rid;	
	}
	
	//do NOT set this to client side only, it is required on the serverside too!
	public boolean isOpaqueCube( )
	{
		return false;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderAsNormalBlock( )
    {
    	return false;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world_, int x_, int y_, int z_, int side_)
    {	
		return true;
		/*
		int meta = world_.getBlockMetadata(x_, y_, z_);
		return blocks[meta].shouldSideBeRendered(world_, x_, y_, z_, side_);
		*/
    }
	
	
	@Override
	public TileEntity createTileEntity(World world_, int meta_)
	{
		if(this.hasTileEntity(meta_) == false)
		{
			return null;
		}
		
		return blocks[meta_].createTileEntity(world_, meta_);
	}
	
	@Override
    public boolean hasTileEntity(int meta_)
    {
		return blocks[meta_].hasTileEntity(meta_);
    }
	
	@Override
    public boolean isLadder(World world_, int x_, int y_, int z_, EntityLivingBase entity_)
    {
		int meta = world_.getBlockMetadata(x_, y_, z_);
		return blocks[meta].isLadder(world_, x_, y_, z_, entity_);
    }
	
	
	//WIREFRAME BOX
	@Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world_, int x_, int y_, int z_)
    {
		int meta = world_.getBlockMetadata(x_, y_, z_);
		
		return blocks[meta].getWireframeBox(world_, x_, y_, z_);
    }
    
    
	
    public void setBlockBoundsBasedOnState(IBlockAccess world_, int x_, int y_, int z_)
    {
    	int meta = world_.getBlockMetadata(x_, y_, z_);
    	
    	BoundSet dim = blocks[meta].getHitBoxesBasedOnState(world_, x_, y_, z_);
    	
    	if(dim != null)
    	{
    		this.setBlockBounds(dim.x1, dim.x2, dim.y1, dim.y2, dim.z1, dim.z2);
    	}
    	else
    	{
    		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    	}
    	
    	/*
      	if(meta == 1 || meta == 2)
    	{
      		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
        	
        	if(tile != null)
        	{

            	int direction = tile.getVariable();
            	
            	switch(direction)
            	{
        			//North
        			case 2:
        			{
        				this.setBlockBounds((float)zeroPx, (float)zeroPx, (float)zeroPx, (float)sixteenPx, (float)sixteenPx, (float)twoPx);
        				break;
        			}
        			
        			//South
        			case 3:
        			{
        				this.setBlockBounds((float)zeroPx, (float)zeroPx, (float)fourteenPx, (float)sixteenPx, (float)sixteenPx, (float)sixteenPx);
        				break;
        			}
        			
        			//East
        			case 5:
        			{
        				this.setBlockBounds((float)fourteenPx, (float)zeroPx, (float)zeroPx, (float)sixteenPx, (float)sixteenPx, (float)sixteenPx);
        				break;
        			}
        			
        			//West
        			case 4:
        			{
        				this.setBlockBounds((float)zeroPx, (float)zeroPx, (float)zeroPx, (float)twoPx, (float)sixteenPx, (float)sixteenPx);
        				break;
        			}
            	}    
            	
        		if(tile.getSubType() == 888)
        		{
        			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        		}
        	}
    	}
      	
      	//Sword Rack
      	if(meta == 4)
      	{
      		
      		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
      		
        	if(tile != null)
        	{
        		
        		if(tile.getSubType() == 822)
        		{	
	            	int direction = tile.getVariable();
	            	
	            	if(direction == 2 || direction == 3)
	            	{
	            		this.setBlockBounds((float)zeroPx, (float)zeroPx, (float)fourPx, (float)sixteenPx, (float)sixteenPx, (float)twelvePx);
	            	}
	            	
	            	if(direction == 4 || direction == 5)
	            	{
	            		this.setBlockBounds((float)fourPx, (float)zeroPx, (float)zeroPx, (float)twelvePx, (float)sixteenPx, (float)sixteenPx);
	            	}
        		}
            	
            	if(tile.getSubType() == 821)
            	{
	            	if(direction == 2 || direction == 3)
	            	{
	            		this.setBlockBounds((float)zeroPx, (float)zeroPx, (float)fourPx, (float)sixteenPx, (float)sixteenPx, (float)twelvePx);
	            	}
	            	
	            	if(direction == 4 || direction == 5)
	            	{
	            		this.setBlockBounds((float)fourPx, (float)zeroPx, (float)zeroPx, (float)twelvePx, (float)sixteenPx, (float)sixteenPx);
	            	}
            	}


        	}
        	

        	
        	

      	}
      	
      	else
      	{
      		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      	}
      	*/
    	
    }

    
    @Override
    public ItemStack getPickBlock(MovingObjectPosition target_, World world_, int x_, int y_, int z_)
    {
        int id = idPicked(world_, x_, y_, z_);        
        TileEntity t = world_.getBlockTileEntity(x_, y_, z_);
        
        if (id == 0 || Item.itemsList[id] == null)
        {
            return null;
        }
        
        
        if(t instanceof TileEntityDiscreteBlock)
        {
        	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)t;
        	int type = tile.getSubType();
        	
        	if(type != 0)
        	{
        		return new ItemStack(id, 1, type);
        	}
        }
        
        if(t instanceof TileEntityLoomBlock)
        {
        	return new ItemStack(id, 3, 0);
        }

        return new ItemStack(id, 1, getDamageValue(world_, x_, y_, z_));
    }
    
    
    @Override
    public boolean isBlockNormalCube(World world_, int x_, int y_, int z_)
    {
    	return blocks[world_.getBlockMetadata(x_, y_, z_)].isBlockNormalCube(world_, x_, y_, z_);
    }
    
    @Override
    public boolean isBlockSolidOnSide(World world_, int x_, int y_, int z_, ForgeDirection side_)
    {
    	return blocks[world_.getBlockMetadata(x_, y_, z_)].isBlockSolidOnSide(world_, x_, y_, z_, side_);
    }
    
    //Generates the collision boxes for a block
    @Override
    public void addCollisionBoxesToList(World world_, int x_, int y_, int z_, AxisAlignedBB mask_, List boxlist_, Entity entity_)
    {
    	blocks[world_.getBlockMetadata(x_, y_, z_)].getCollisionBoxes(world_, x_, y_, z_, mask_, boxlist_, entity_);   
    }


}
