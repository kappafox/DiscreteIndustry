





package kappafox.di.decorative.blocks;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Range;

import kappafox.di.base.blocks.BlockDiscreteBlock;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.util.BoundSet;
import kappafox.di.base.util.SubBlockDummy;
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
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecor extends BlockDiscreteBlock
{
	private static final short SUB_BLOCKS = 7;
	private static SubBlock[] blocks;
	
	public static final short ID_LADDER_FOOTHOLD = 800;
	public static final short ID_LADDER_POLE = 801;
	public static final short ID_LADDER_SIMPLE = 802;
	public static final short ID_LADDER_ROPE = 803;
	public static final short ID_LADDER_FIXED = 804;
	public static final short ID_LADDER_CLASSIC = 806;
	public static final short ID_LADDER_INDUSTRIAL = 807;
	
	public static final short ID_RACK_SWORDREST = 821;
	public static final short ID_RACK_SWORDRACK = 822;
	
	public static final short ID_STAIRS_NORMAL = 861;
	public static final short ID_STAIRS_SMALL = 862;
	
	public static final short ID_STRUT_2X2 = 871;
	public static final short ID_STRUT_4X4 = 872;
	public static final short ID_STRUT_6X6 = 873;
	
	
	//        Range<Integer> r = Range.closed(851, 860);
	
	public static final Range<Integer> RANGE_LADDER = Range.closed(800, 820);
	public static final Range<Integer> RANGE_RACK = Range.closed(821, 840);
	public static final Range<Integer> RANGE_STAIRS = Range.closed(861, 870);
	public static final Range<Integer> RANGE_STRUT = Range.closed(871, 880);
	
	@SideOnly(Side.CLIENT)
	private int rid;
	
	public BlockDecor(int id_, Material mat_, int renderID_)
	{
		super(id_, mat_, renderID_);
		
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setUnlocalizedName("decorBlock");
		this.setHardness(3.0F);
		
		blocks = new SubBlock[SUB_BLOCKS];
		
		blocks[0] = new SubBlockDummy();
		blocks[1] = new SubBlockDummy();
		
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
			if(RANGE_LADDER.contains(meta_))
			{
				return blocks[2].getOverloadedIcon(side_, meta_);
			}
			
			if(RANGE_RACK.contains(meta_))
			{
				return blocks[4].getOverloadedIcon(side_, meta_);
			}
			
			if(RANGE_STAIRS.contains(meta_))
			{
				return blocks[5].getOverloadedIcon(side_, meta_);
			}
			
			if(RANGE_STRUT.contains(meta_))
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
				if((tile.getTextureSource(side_) == tile.getOriginalID()) && (tile.getTextureSourceMeta(side_) == tile.getSubtype()))
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
			int type = tile.getSubtype();
			
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
        	int type = tile.getSubtype();
        	
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
