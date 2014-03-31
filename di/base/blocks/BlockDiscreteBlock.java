





package kappafox.di.base.blocks;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDiscreteBlock extends Block
{
	protected boolean isTileProvider = true;
	protected int renderType;
	protected boolean lockRefresh;
	
	protected Icon[] textures = new Icon[6];
	protected Icon[] icons = new Icon[6];
	
	protected Icon defaultIcon;
	
	
	public BlockDiscreteBlock(int id_, Material mat_, int renderID_)
	{
		super(id_, mat_);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setUnlocalizedName("discreteCable");
		this.setHardness(3.0F);
		this.setResistance(150.0F);
		this.renderType = renderID_;
		this.isTileProvider = true;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ireg_)
	{	
		
		defaultIcon = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
	
		for(int i = 0; i < 6; i++)
		{
			textures[i] = defaultIcon;
			icons[i] = defaultIcon;
		}
		
		this.blockIcon = icons[0];
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id_, CreativeTabs tabs_, List list_)
	{
		list_.add(new ItemStack(id_, 1, 0));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side_, int meta_)
	{
		return icons[meta_]; 
	}
	
	
	/*
	//@SideOnly(Side.CLIENT)
	private boolean refreshTextures(IBlockAccess ibaccess_, int xcoord_, int ycoord_, int zcoord_, int side_)
	{
		
		if(lockRefresh == true)
		{
			return true;
		}
		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)ibaccess_.getBlockTileEntity(xcoord_, ycoord_, zcoord_);
		
		
		if(tile != null)
		{
			int face = tile.getFace();
					
			//higher blockids are reserved for items
			Block inHand;
			int item = tile.getTextureSource(side_);
			try
			{
				inHand = Block.blocksList[item];
			}
			catch(Exception e_)
			{
				return true;
			}
			

			//grab all the textures fresh;
			
			//if(inHand != null)
			//{
			
			if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
			{
				for(int i = 0; i < 6; i++)
				{
					textures[i] = inHand.getIcon(i, tile.getTextureSourceMeta(side_));
				}
				
				Icon oldFace = textures[3];
				textures[3] = textures[face];
				textures[face] = oldFace;
				
			}
			
		}
		
		return true;
	}
	*/
	
	@Override
    public Icon getBlockTexture(IBlockAccess world_, int x_, int y_, int z_, int side_)
    {
		/*
    	refreshTextures(ibaccess_, xcoord_, ycoord_, zcoord_, side_);
    	
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)ibaccess_.getBlockTileEntity(xcoord_, ycoord_, zcoord_);
    	
    	if(tile != null && tile.getTextureSource() == this.blockID)
    	{
    		return icons[0];
    	}
    	else
    	{
    		return textures[side_];
    	}
    	*/
		
    	//refreshTextures(ibaccess_, xcoord_, ycoord_, zcoord_, side_);
    	int id = world_.getBlockId(x_, y_, z_);
    	int meta = world_.getBlockMetadata(x_, y_, z_);
		
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
    	
    	if(tile != null)
    	{
    		
    		/*
	    	if(tile.getTextureSource(side_) == id)
	    	{
	    		
	    		System.out.println("match!" + id + "\t" + Block.blocksList[this.blockID].getIcon(side_, meta).getIconName());
	    		//return textures[0];
	    		return Block.blocksList[this.blockID].getIcon(side_, meta);
	    		
	    	}
	    	else
	    	{
	    	*/
	    		Block target = Block.blocksList[tile.getTextureSource(side_)];
	    		
	    		if(target != null && tile.getTextureSource(side_) != 0)
	    		{
	    			return target.getIcon(tile.getTextureSourceSide(side_), tile.getTextureSourceMeta(side_));
	    		}


	    	//}
    	}
    	//return this.getIcon(side_, ibaccess_.getBlockMetadata(x_, y_, z_));
    	return textures[0];
    }
	
	
	@Override
	public void onBlockPlacedBy(World world_, int x_, int y_, int z_, EntityLivingBase player_, ItemStack istack_)
    {
		//thankyou mojang <3
		int magic = MathHelper.floor_double((double)(player_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(x_, y_, z_);
		
		short dir = 3;
	
		if(tile != null)
		{
			switch(magic)
			{
				case 0:
				{
					dir = 2;
					break;
				}
				
				case 2:
				{
					dir = 3;
					break;
				}
				
				case 3:
				{
					dir = 4;
					break;
				}
				
				case 1:
				{
					dir = 5;
					break;
				}
			}
		
		tile.setFace(dir);
		tile.setTextureOrientation(dir);
		}
    }
    
	@Override
    public boolean hasTileEntity(int metadata)
    {
        return isTileProvider;
    }
    
    @Override
    public TileEntity createTileEntity(World world_, int metadata_)
    {
    	
        if(isTileProvider)
        {
        	TileEntityDiscreteBlock tile = new TileEntityDiscreteBlock(this.blockID, metadata_);
            return tile;
        }
        return null;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderAsNormalBlock( )
    {
    	return true;
    }
    
    @Override
    public boolean isOpaqueCube( )
    {
    	return true;
    }

    
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType( )
    {
    	return renderType;
    }
    
    
    @Override
    public void initializeBlock( )
    {
    	//we call this last so that we don't try and grab a texture from a block that doesn't exist yet
    	lockRefresh = false;
    	
    }
    
    /*
	@Override
    public boolean onBlockActivated(World world_, int xcoord_, int ycoord_, int zcoord_, EntityPlayer player_, int side_, float par7, float par8, float par9)
    {	
		
		
		//change in 1.0.2 to make blocks only change when sneaking
		if(player_.isSneaking() == false)
		{
			return false;
		}
		
		
		//System.out.println("ENTRY");
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world_.getBlockTileEntity(xcoord_, ycoord_, zcoord_);

		
		if(tile == null)
		{
			//System.out.println("NULL TILE");
			return false;
		}
		
		ItemStack item = player_.inventory.getCurrentItem();
		Block inHand = null;
		
		//higher blockids are reserved for items
		try
		{
			inHand = Block.blocksList[item.itemID];
		}
		catch(Exception e_)
		{
			//System.out.println("EXCEPTION");
			return false;
		}
		
		if(item != null && canUseTexture(inHand) == true)
		{			
			
			//client only for .getIcon()
			if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
			{
				for(int i = 0; i < 6; i++)
				{
					//System.out.println("TEXTURE SET");
					textures[i] = inHand.getIcon(i, item.getItemDamage());
				}
			}
			
			tile.setTextureSource(item.itemID);
			tile.setTextureSourceMeta(item.getItemDamage());
			tile.setFace((short)side_);
			world_.markBlockForUpdate(xcoord_, ycoord_, zcoord_);
			return true;
		}
		
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			System.out.println("Clicked!");
		}
        return false;
        
    }
    */
    
	
	@Override
	public int damageDropped(int meta_)
	{
		return meta_;
	}
	
	
	private boolean canUseTexture(Block target_)
	{
		if(target_ != null)
		{
			if(target_.renderAsNormalBlock() && target_.blockID != this.blockID)
			{
				return true;
			}
		}
			
			//item != null && inHand.isNormalCube(item.itemID) == true && item.itemID != this.blockID
		
		return false;
	}

}
