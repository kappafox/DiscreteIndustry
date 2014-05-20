package kappafox.di.transport.blocks;

import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.network.PacketCrafter;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.SideHelper;
import kappafox.di.transport.DiscreteTransport;
import kappafox.di.transport.tileentities.TileEntityDiscreteHopper;
import kappafox.di.transport.tileentities.TileEntityStorageRack;

public class SubBlockStorageRack extends SubBlock
{
	
    private Random rand;
    
    private static Icon ICON_STORAGERACK_EDGE;
    private static Icon ICON_STORAGERACK_SIDE;
    private static Icon ICON_STORAGERACK_TOP;
    
    public SubBlockStorageRack( )
    {
    	rand = new Random();
    }
    
	@Override
	public Icon getIcon(int side, int meta)
	{
		if(side < 2)
		{
			return ICON_STORAGERACK_TOP;
		}
		return ICON_STORAGERACK_SIDE;
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if(tile instanceof TileEntityStorageRack)
		{
			int face = ((TileEntityStorageRack)tile).getDirection();
			
			if(face == ForgeDirection.OPPOSITES[side])
			{
				return ICON_STORAGERACK_TOP;
			}
		}
		return this.getIcon(side, 0);
	}
	
	@Override
	public void registerIcons(IconRegister ireg)
	{
		ICON_STORAGERACK_EDGE = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockStorageRack_Edge");
		ICON_STORAGERACK_SIDE = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockStorageRack_side");
		ICON_STORAGERACK_TOP = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockStorageRack_top");
	}
	
	@Override
	public Icon getSpecialIcon(int index, int side)
	{
		switch(index)
		{
			case 0:
			{
				return ICON_STORAGERACK_EDGE;
			}
		}
		
		return this.getIcon(side, 0);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz)
	{
		
		if(SideHelper.onServer())
		{
			if(side <= 1) return false;	//top and bottom not required
			
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			
			if(tile instanceof TileEntityStorageRack)
			{
				TileEntityStorageRack t = (TileEntityStorageRack)tile;
				ItemStack inhand = player.getCurrentEquippedItem();
				
				int box = this.getSlotIndex(t.getSize(), t.getDirection(), hitx, hity, hitz);
				
				if(t.isWaitingForDoubleClick())
				{
					boolean all = t.shouldTakeAllFromInventory();
					
					if(all == true)
					{
						for(int i = 0; i < player.inventory.getSizeInventory(); i++)
						{
							if(t.willContainerAccept(box, player.inventory.getStackInSlot(i)))
							{
								ItemStack result = t.addItemToContainer(box, player.inventory.getStackInSlot(i), true);
								if(result.stackSize < 1)
								{
									result = null;
								}
								player.inventory.setInventorySlotContents(i, result);
								
							}
						}
						
						
						if(player instanceof EntityPlayerMP)
						{
		                    ((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
						}
						//world.markBlockForUpdate(x, y, z);
						return true;
					}
				}
	
	
				
				if(player.isSneaking() == false)
				{		
	
					//An item from discrete Transport so attempt container modification behaviour
					if((inhand != null) && (inhand.itemID - 256) == DiscreteTransport.discreteTransportItemID)
					{
						//Damage value for a storage box
						if(inhand.getItemDamage() >= 0 && inhand.getItemDamage() <= 16)
						{

							boolean result = this.tryInsertContainer(t, player, x, y, z, box, inhand);
							world.markBlockForUpdate(x, y, z);

							return result;
						}
					}
					
					//try insert
					if(inhand != null)
					{
						//t.dumpDebugStats(0);
						boolean result = this.tryAddStackToContainer(t, player, x, y, z, box, inhand);
						//world.markBlockForUpdate(x, y, z);
						//t.dumpDebugStats(0);
						return result;
					}
					
				}
				else
				{
					//sneaking
					//System.out.println("Sneaking");
					if(inhand == null)
					{
						boolean result = this.tryRemoveContainer(world, t, player, x, y, z, box, inhand);
						world.markBlockForUpdate(x, y, z);
						return result;
					}
					else	//something in hand
					{
						if(t.hasContainer(box) == true && t.willContainerAccept(box, inhand))
						{
							inhand.stackSize = t.addItemToContainer(box, inhand, false).stackSize;
							world.markBlockForUpdate(x, y, z);
							return true;
						}
					}
				}
			}
		}
		return true;
	}
	
	@Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta)
    {
		TileEntityStorageRack tile = (TileEntityStorageRack)world.getBlockTileEntity(x, y, z);     
  	
	    if(tile instanceof TileEntityStorageRack)
	    {
	       	Random random = new Random();
	        for(int i = 0; i < tile.getSize(); i++)
	        {
	            ItemStack itemstack = tile.removeContainer(i);
	
	            if(itemstack != null)
	            {
	                float f = random.nextFloat() * 0.8F + 0.1F;
	                float f1 = random.nextFloat() * 0.8F + 0.1F;
	                float f2 = random.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.itemID, 1, itemstack.getItemDamage()));

                    if(itemstack.hasTagCompound())
                    {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }

                    float f3 = 0.05F;
                    entityitem.motionX = (double)((float)random.nextGaussian() * f3);
                    entityitem.motionY = (double)((float)random.nextGaussian() * f3 + 0.2F);
                    entityitem.motionZ = (double)((float)random.nextGaussian() * f3);
                    world.spawnEntityInWorld(entityitem);
	            }
	        }
	
	        world.func_96440_m(x, y, z, id);
	    }
	
	   //super.breakBlock(world, x, y, z, id, meta);
    }
	
	private void spawnItemIntoWorld(World world, int x, int y, int z, EntityPlayer player, ItemStack istack)
	{
        int side = Minecraft.getMinecraft().objectMouseOver.sideHit;
        
        float fx = x;
        float fy = y;
        float fz = z;
        
        float velx = 0;
        float vely = 0;
        float velz = 0;
        
		fy += 0.5F;	//middle of block in the y axis
		
        float offsetMinor = 0.9F;
        float offsetMajor = 0.9F;
        
        switch(side)
        {
        	case 0:
        	{
        		fy -= 1;
        		break;
        	}
        	
        	case 1:
        	{
        		fy += 1;
        		break;
        	}
        	
        	//North
        	case 2:
        	{
        		fx += 0.5F;
        		break;
        	}
        	
        	//South
        	case 3:
        	{
        		fz += 1.2F;
        		fx += 0.5F;
        		velz = 0.1F;
        		break;
        	}
        	
        	case 4:
        	{
        		//fx = -0.2F;
        		fz += 0.5F;
        		break;
        	}
        	
        	case 5:
        	{
        		fz += 0.5F;
        		fx += 1.2F;
        		velx = 0.1F;
        		break;
        	}
        }
        
        
        EntityItem ent = new EntityItem(world, (double)((float)fx), (double)((float)fy), (double)((float)fz), istack);

        if (istack.hasTagCompound())
        {
            ent.getEntityItem().setTagCompound((NBTTagCompound)istack.getTagCompound().copy());
        }

        
        ent.delayBeforeCanPickup = 0;
        ent.setVelocity(velx, vely, velz);
        world.spawnEntityInWorld(ent);
		
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player, float hitx, float hity, float hitz)
	{
		
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile instanceof TileEntityStorageRack)
			{
				TileEntityStorageRack t = (TileEntityStorageRack)tile;
				
				int subbox = this.getSlotIndex(t.getSize(), t.getDirection(), hitx, hity, hitz);
				
				//got a container?
				if(t.hasContainer(subbox))
				{
					//does it have items?
					if(t.getContainerContentCount(subbox) > 0)
					{
						boolean fullStack = true;
						if(player.isSneaking())
						{
							fullStack = false;
						}
						ItemStack get = t.removeItemFromContainer(subbox, fullStack);
						
						if(get != null)
						{
							boolean success = player.inventory.addItemStackToInventory(get);
							
							if(success == false)
							{
								this.spawnItemIntoWorld(world, x, y, z, player, get);
							}
							else
							{
								world.playSoundAtEntity(player, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
							}
						}
					}
				}
			}
			world.markBlockForUpdate(x, y, z);
		}
		

	}
	
	@Override
	public boolean isDiscrete( )
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntityStorageRack(1);
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{
		return true;
	}
	
	private boolean tryInsertContainer(TileEntityStorageRack tile, EntityPlayer player, int x, int y, int z, int slot, ItemStack inhand)
	{	
		if(tile.hasContainer(slot) == false)
		{
			tile.addContainer(slot, inhand.copy());
			player.getCurrentEquippedItem().stackSize = player.getCurrentEquippedItem().stackSize - 1;
			return true;
		}
		
		return true;
	}
	
	private boolean tryAddStackToContainer(TileEntityStorageRack tile, EntityPlayer player, int x, int y, int z, int slot, ItemStack inhand)
	{
		//if(SideHelper.onServer())
		//{
			if(tile.hasContainer(slot) == true && tile.willContainerAccept(slot, inhand))
			{
				inhand.stackSize = tile.addItemToContainer(slot, inhand, true).stackSize;
				tile.shouldTakeAllFromInventory();
				return true;
			}
		//}
		
		return true;
	}
	
	private boolean tryRemoveContainer(World world, TileEntityStorageRack tile, EntityPlayer player, int x, int y, int z, int slot, ItemStack inhand)
	{
		if(tile.hasContainer(slot) == true)
		{
			ItemStack con = tile.removeContainer(slot);

			boolean success = player.inventory.addItemStackToInventory(con);
			
			if(player instanceof EntityPlayerMP)
			{
                ((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
			}
			
			//inventory full or not possible to add
			if(success == false && con != null)
			{
				this.spawnItemIntoWorld(world, x, y, z, player, con);
			}
			else
			{
				world.playSoundAtEntity(player, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			}
			
			return true;
		}
		
		return false;
	}
	
	private int getSlotIndex(int size, int facing, float hitx, float hity, float hitz)
	{
		if(size == 1)
		{
			return 0;
		}
		
		if(size == 2)
		{
			return this.getHalf(facing, hitx, hity, hitz);
		}
		
		if(size == 4)
		{
			return this.getQuarter(facing, hitx, hity, hitz);
		}
		
		return 0;
	}
	
	private int getHalf(int facing, float hitx, float hity, float hitz)
	{	
		if(hity < 0.5F)
		{
			return 1;
		}
		
		return 0;
	}
	
	private int getQuarter(int facing, float hitx, float hity, float hitz)
	{
		float[] normalised = this.normaliseHitCoordinates(facing, hitx, hity, hitz);
		
		hitx = normalised[0];
		hity = normalised[1];
		hitz = normalised[2];
		
		if(hitx < 0.5)
		{
			if(hity < 0.5)
			{
				return 2;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			if(hity < 0.5)
			{
				return 3;
			}
			else
			{
				return 1;
			}
		}
	}
	
	private float[] normaliseHitCoordinates(int facing, float hitx, float hity, float hitz)
	{
		float[] normalised = {hitx, hity, hitz};
		
		switch(facing)
		{
			//South
			case 3:
			{
				normalised[0] = 1 - hitx;
				normalised[2] = 1;
				break;
			}
			
			//West
			case 4:
			{
				normalised[0] = 1 - hitz;
				normalised[2] = 1;
				break;
			}
			
			//East
			case 5:
			{
				normalised[0] = hitz;
				normalised[2] = 1;
				break;
			}
			
			
		}
		
		return normalised;
	}

}
