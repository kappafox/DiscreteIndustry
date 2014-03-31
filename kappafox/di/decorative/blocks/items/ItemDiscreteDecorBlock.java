package kappafox.di.decorative.blocks.items;

import java.util.List;

import kappafox.di.base.items.SubItem;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;






public class ItemDiscreteDecorBlock extends ItemBlock
{
	
	private static SubItem[] subs = new SubItem[3];
	
	public ItemDiscreteDecorBlock(int id_)
	{
		super(id_);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		
		subs[0] = new SubItemSwordRack();
		subs[1] = new SubItemDiscreteStairs();
		subs[2] = new SubItemFixture();
	}
	
	public String getUnlocalizedName(ItemStack istack_)
	{
		String name = "Unset";
		
		switch(istack_.getItemDamage())
		{
			case 0:
			{
				name = "Overhead Fixture";
				break;
			}
			
			case 1:
			{
				name = "DBLOCK_NAME_1";
				break;
			}
			case 2:
			{
				name = "DBLOCK_NAME_2";
				break;
			}
			case 3:
			{
				name = "DBLOCK_NAME_3";
				break;
			}
			case 4:
			{
				name = "DBLOCK_NAME_4";
				break;
			}
			case 5:
			{
				name = "DBLOCK_NAME_5";
				break;
			}
			case 6:
			{
				name = "DBLOCK_NAME_6";
				break;
			}
			case 7:
			{
				name = "DBLOCK_NAME_7";
				break;
			}
			case 8:
			{
				name = "DBLOCK_NAME_8";
				break;
			}
			case 9:
			{
				name = "DBLOCK_NAME_9";
				break;
			}
			case 10:
			{
				name = "DBLOCK_NAME_10";
				break;
			}
			case 11:
			{
				name = "DBLOCK_NAME_11";
				break;
			}
			case 12:
			{
				name = "DBLOCK_NAME_12";
				break;
			}
			case 13:
			{
				name = "DBLOCK_NAME_13";
				break;
			}
			case 14:
			{
				name = "DBLOCK_NAME_14";
				break;
			}
			case 15:
			{
				name = "DBLOCK_NAME_15";
				break;
			}
			
			case 800:
			{
				return "Foothold Ladder";
			}
			
			case 801:
			{
				return "Pole Ladder";
			}
			
			case 802:
			{
				return "Simple Ladder";
			}
			
			case 803:
			{
				return "Rope Ladder";
			}
			
			case 804:
			{
				return "XM1 Ladder";
			}
			
			case 805:
			{
				return "XM2 Ladder";
			}
			
			case 806:
			{
				return "Classic Ladder";
			}
			
			case 807:
			{
				return "Industrial Ladder";
			}
			
			case 821:
			{
				return "Sword Rest";
			}
			
			case 822:
			{
				return "Sword Rack";
			}
			
			case 861:
			{
				return "Discrete Stairs";
			}
			
			case 862:
			{
				return "Discrete Small Stairs";
			}
			
			case 871:
			{
				return "2x2 Strut";
			}
			
			case 872:
			{
				return "4x4 Strut";
			}
			
			case 873:
			{
				return "6x6 Strut";
			}
			
			case 888:
			{
				return "Test Item";
			}
			
			default:
			{
				name = "how did you get this?";
				break;
			}
		}
		
		return getUnlocalizedName() + "." + name;
	}
	
	@Override
    public void getSubItems(int id_, CreativeTabs tab_, List itemList_)
    {
        Block.blocksList[this.getBlockID()].getSubBlocks(id_, tab_, itemList_);
        
        //Ladders
        itemList_.add(new ItemStack(id_, 1, 800));
        itemList_.add(new ItemStack(id_, 1, 801));
        itemList_.add(new ItemStack(id_, 1, 802));
        itemList_.add(new ItemStack(id_, 1, 803));
        itemList_.add(new ItemStack(id_, 1, 804));
        itemList_.add(new ItemStack(id_, 1, 805));
        itemList_.add(new ItemStack(id_, 1, 806));
        itemList_.add(new ItemStack(id_, 1, 807));
        
        //Racks/Rests
        itemList_.add(new ItemStack(id_, 1, 821));
        itemList_.add(new ItemStack(id_, 1, 822));
        
        //Discrete Stairs
        itemList_.add(new ItemStack(id_, 1, 861));
        itemList_.add(new ItemStack(id_, 1, 862));
        
        //StrutS
        itemList_.add(new ItemStack(id_, 1, 871));	//2x2
        itemList_.add(new ItemStack(id_, 1, 872));	//4x4
        itemList_.add(new ItemStack(id_, 1, 873));	//6x6
        
        //itemList_.add(new ItemStack(id_, 1, 888));
    }
	
	@Override
	public int getMetadata(int par1_)
	{
		return par1_;
	}
	
	
	@Override
    public boolean onItemUse(ItemStack item_, EntityPlayer player_, World world_, int x_, int y_, int z_, int side_, float hitx_, float hity_, float hitz_)
    {
		int meta = item_.getItemDamage();
        int id_ = world_.getBlockId(x_, y_, z_);
       
        int direction;
        
        if(meta >= 800)
        {
        	return this.OnItemUseOverloaded(item_, player_, world_, x_, y_, z_, side_, hitx_, hity_, hitz_, meta);
        }
        
		if(meta == 0 || meta == 3)
		{
			return super.onItemUse(item_, player_, world_, x_, y_, z_, side_, hitx_, hity_, hitz_);
		}
		
		int tMeta = 0;
		int ox = x_;
		int oy = y_;
		int oz = z_;
		
        if (id_ == Block.snow.blockID && (world_.getBlockMetadata(x_, y_, z_) & 7) < 1)
        {
            side_ = 1;
        }
        else if (id_ != Block.vine.blockID && id_ != Block.tallGrass.blockID && id_ != Block.deadBush.blockID
                && (Block.blocksList[id_] == null || !Block.blocksList[id_].isBlockReplaceable(world_, x_, y_, z_)))
        {
            if (side_ == 0)
            {
                --y_;
                
                direction = this.directionFromYaw(player_);
                switch(direction)
                {
                	case 0:
                		tMeta = 3;
                		break;
                		
                	case 1:
                		tMeta = 4;
                		break;
                		
                	case 2:
                		tMeta = 2;
                		break;
                		
                	case 3:
                		tMeta = 5;
                		break;
                }
            }

            if (side_ == 1)
            {
                ++y_;
                
                direction = this.directionFromYaw(player_);
                switch(direction)
                {
                	case 0:
                		tMeta = 3;
                		break;
                		
                	case 1:
                		tMeta = 4;
                		break;
                		
                	case 2:
                		tMeta = 2;
                		break;
                		
                	case 3:
                		tMeta = 5;
                		break;
                }
            }

            if (side_ == 2)
            {
                --z_;
                tMeta = 3;
            }

            if (side_ == 3)
            {
                ++z_;
                tMeta = 2;
            }

            if (side_ == 4)
            {
                --x_;
                tMeta = 5;
            }

            if (side_ == 5)
            {
                ++x_;
                tMeta = 4;
            }
        }

        if (item_.stackSize == 0)
        {
            return false;
        }
        else if (!player_.canPlayerEdit(x_, y_, z_, side_, item_))
        {
            return false;
        }
        else if (y_ == 255 && Block.blocksList[this.getBlockID()].blockMaterial.isSolid())
        {
            return false;
        }

        Block block = Block.blocksList[this.getBlockID()];
        int j1 = this.getMetadata(item_.getItemDamage());        
        int k1 = Block.blocksList[this.getBlockID()].onBlockPlaced(world_, x_, y_, z_, side_, hitx_, hity_, hitz_, j1);

        if (placeBlockAt(item_, player_, world_, x_, y_, z_, side_, hitx_, hity_, hitz_, k1))
        {
            world_.playSoundEffect((double)((float)x_ + 0.5F), (double)((float)y_ + 0.5F), (double)((float)z_ + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
            --item_.stackSize;
        }
        
        /*
        TileEntitySwordRack tile = null;
        
        if(meta == 4)
        {
        	TileEntitySwordRack t = new TileEntitySwordRack(6);
        	t.setVariable(tMeta);
        	t.setSubType(4);
        	t.setTextureOrientation(tMeta);

	        for(int i = 0; i < 6; i++)
	        {
	        	t.setTextureSource(this.getBlockID(), meta, i, i);
	        }
	        
        	tile = t;
        }
        
        
        
        if(world_.isRemote == false)
        {
            world_.setBlockTileEntity(x_, y_, z_, tile);
            world_.markBlockForUpdate(x_, y_, z_);
        }
        */
        
        

        return true;
    }
	
	
	//Method just for overloaded damages 800+
	public boolean OnItemUseOverloaded(ItemStack item_, EntityPlayer player_, World world_, int x_, int y_, int z_, int side_, float hitx_, float hity_, float hitz_, int damage_)
	{
		int meta = damage_;
        int id_ = world_.getBlockId(x_, y_, z_);
       
        int direction;		
		int orient = 0;
		int ox = x_;
		int oy = y_;
		int oz = z_;
		
        if(id_ == Block.snow.blockID && (world_.getBlockMetadata(x_, y_, z_) & 7) < 1)
        {
            side_ = 1;
        }
        else if(id_ != Block.vine.blockID && id_ != Block.tallGrass.blockID && id_ != Block.deadBush.blockID && (Block.blocksList[id_] == null || !Block.blocksList[id_].isBlockReplaceable(world_, x_, y_, z_)))
        {
            if(side_ == 0)
            {
                y_--;             
                direction = this.directionFromYaw(player_);
                orient = this.getDirectionFromBottom(direction);
            }

            if (side_ == 1)
            {
                y_++;               
                direction = this.directionFromYaw(player_);
                orient = this.getDirectionFromTop(direction);
            }
            
            switch(side_)
            {
	            case 2:
	            {
	            	z_--;
	            	orient = 3;
	            	break;
	            }
	            
	            case 3:
	            {
	            	z_++;
	            	orient = 2;
	            	break;
	            }
	            
	            case 4:
	            {
	            	x_--;
	            	orient = 5;
	            	break;
	            }
	            
	            case 5:
	            {
	            	x_++;
	            	orient = 4;
	            	break;
	            }
            }
            
            /*
            if (side_ == 2)
            {
                --z_;
                orient = 3;
            }

            if (side_ == 3)
            {
                ++z_;
                orient = 2;
            }

            if (side_ == 4)
            {
                --x_;
                orient = 5;
            }

            if (side_ == 5)
            {
                ++x_;
                orient = 4;
            }
            */
        }

        if(item_.stackSize == 0)
        {
            return false;
        }
        else if(!player_.canPlayerEdit(x_, y_, z_, side_, item_))
        {
            return false;
        }
        else if(y_ == 255 && Block.blocksList[this.getBlockID()].blockMaterial.isSolid())
        {
            return false;
        }

        Block block = Block.blocksList[this.getBlockID()];
        
        int j1 = this.getOverloadedMeta(damage_);    
        int k1 = Block.blocksList[this.getBlockID()].onBlockPlaced(world_, x_, y_, z_, side_, hitx_, hity_, hitz_, j1);

        if(placeBlockAt(item_, player_, world_, x_, y_, z_, side_, hitx_, hity_, hitz_, k1))
        {
            world_.playSoundEffect((double)((float)x_ + 0.5F), (double)((float)y_ + 0.5F), (double)((float)z_ + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
            --item_.stackSize;
        }

        
        
        TileEntity tile = null;
        int subindex = this.getSubIndex(meta);
        
        
        if(subindex != -1)
        {
        	tile = subs[this.getSubIndex(meta)].getTileEntity(meta, orient, -1, this.getBlockID(), meta, side_, hitx_, hity_);
        }
        else
        {
        	//Default Block init stuff for non specialised subblocks
	        TileEntityDiscreteBlock t = new TileEntityDiscreteBlock(this.getBlockID(), j1, damage_);
	        t.setAllTexturesFromSource(this.getBlockID(), meta);
	        
	        t.setVariable(orient);
	        t.setDirection((short)orient);
	        t.setTextureOrientation(orient);
        	t.setFullColour(true);
        	t.setOriginalID(this.getBlockID());
        	t.setSubType(meta);
	        
	        tile = t;        	
        }
        
        /*
        //Sword Racks
        if(meta >= 821 && meta <= 840)
        {
        	
        	/*
        	TileEntitySwordRack t = null;
        	
        	switch(meta)
        	{
        		case 821:
        			t = new TileEntitySwordRack(1);
        			break;
        		
        		case 822:
        			t = new TileEntitySwordRack(6);
        			break;
        			
        		default:
        			t = new TileEntitySwordRack();
        			
        	}
        	
        	t.setVariable(tMeta);
        	t.setSubType(meta);
        	t.setFullColour(true);
        	t.setAllTexturesFromSource(this.getBlockID(), 4);

        	tile = subs[this.getSubIndex(meta)].getTileEntity(meta, orient, -1, this.getBlockID(), meta, hitx_, hity_);
        }
        else
        {
	        TileEntityDiscreteBlock t = new TileEntityDiscreteBlock(this.getBlockID(), j1, damage_);
	        t.setAllTexturesFromSource(this.getBlockID(), meta);
	        
	        t.setVariable(orient);
	        t.setDirection((short)orient);
	        t.setTextureOrientation(orient);
        	t.setFullColour(true);
        	t.setOriginalID(this.getBlockID());
        	t.setSubType(meta);
	        
	        tile = t;
        }
        
        */
        if(world_.isRemote == false && tile != null)
        {
            world_.setBlockTileEntity(x_, y_, z_, tile);
            world_.markBlockForUpdate(x_, y_, z_);
        }
        
        return true;
	}

    /**
     * Returns true if the given ItemBlock can be placed on the given side of the given block position.
     */
	@Override
    public boolean canPlaceItemBlockOnSide(World world_, int x_, int y_, int z_, int side_, EntityPlayer player_, ItemStack item_)
    {  	
        int id = world_.getBlockId(x_, y_, z_);
        int meta = item_.getItemDamage();
        
        if(meta == 0)
        {
        	return super.canPlaceItemBlockOnSide(world_, x_, y_, z_, side_, player_, item_);
        }
        
        int ox = x_;
        int oy = y_;
        int oz = z_;
        
        if (id == Block.snow.blockID)
        {
            side_ = 1;
        }
        else if (id != Block.vine.blockID && id != Block.tallGrass.blockID && id != Block.deadBush.blockID
                && (Block.blocksList[id] == null || !Block.blocksList[id].isBlockReplaceable(world_, x_, y_, z_)))
        {
            if (side_ == 0)
            {
                --y_;
            }

            if (side_ == 1)
            {
                ++y_;
            }

            if (side_ == 2)
            {
                --z_;
            }

            if (side_ == 3)
            {
                ++z_;
            }

            if (side_ == 4)
            {
                --x_;
            }

            if (side_ == 5)
            {
                ++x_;
            }
        }
        

        return world_.canPlaceEntityOnSide(this.getBlockID(), x_, y_, z_, false, side_, player_, item_);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item_, EntityPlayer player_, List tooltip_, boolean par4_)
    {
    	int meta = item_.getItemDamage();
    	
    	if(meta == 0)
    	{
        	tooltip_.add("Craft me to update to the new version!");
    	}
    	else
    	{
        	tooltip_.add("Obscurator Compatible");
    		//struts
    		if(meta >= 871 && meta <= 880)
    		{
            	tooltip_.add("Wrench to toggle connections");
    		}

    	}

    }
    
    private int directionFromYaw(EntityPlayer player_)
    {
    	return MathHelper.floor_double((double)((player_.rotationYaw * 4F) / 360F) + 0.5D) & 3;
    }
    
    private int getSubIndex(int type_)
    {
        if(type_ >= 821 && type_ <= 840)
        {
        	return 0;
        }
        
        if(type_ >= 861 && type_ <= 870)
        {
        	return 1;
        }
        
    	//Stairs
    	if(type_ >= 871 && type_ <= 880)
    	{
    		return 2;
    	}
        
        return -1;
    }
    
    private int getOverloadedMeta(int damage_)
    {
    	//ladder
    	if(damage_ >= 800 && damage_ <= 820)
    	{
    		return 2;
    	}
    	
    	//Sword Racks
    	if(damage_ >= 821 && damage_ <= 840)
    	{
    		return 4;
    	}
    	
    	//Stairs
    	if(damage_ >= 861 && damage_ <= 870)
    	{
    		return 5;
    	}
    	
    	//Stairs
    	if(damage_ >= 871 && damage_ <= 880)
    	{
    		return 6;
    	}
    	
    	return 0;
    }
    
    private int getDirectionFromTop(int dir_)
    {
    	return this.getDirectionFromBottom(dir_);
    }
    
    private int getDirectionFromBottom(int dir_)
    {
        switch(dir_)
        {
        	case 0:
        		return 3;
        		
        	case 1:
        		return 4;
        		
        	case 2:
        		return 2;
        		
        	case 3:
        		return 5;
        		
        	default:
        		return 0;
        }
    }
}
