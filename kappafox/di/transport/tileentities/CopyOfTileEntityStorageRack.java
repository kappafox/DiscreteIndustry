package kappafox.di.transport.tileentities;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.oredict.OreDictionary;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.util.SideHelper;

public class CopyOfTileEntityStorageRack extends TileEntityDiscreteBlock implements IInventory
{
	private ItemStack[] items;
	private ItemStack[] accessSlots;
	private int[] amounts;
	
	private long clicked = 0;
	private boolean canUpdate = false;
	
	private static final int DOUBLE_CLICK_DELAY = 1000;
	
	private static short MAX_STACK_SIZE = 64;
	private ItemStack lastItemAdded = null;
	
	//size in STACKS
	private static final short MAX_CAPACITY_WOOD = 64;
	private static final short MAX_CAPACITY_STONE = 128;
	private static final short MAX_CAPACITY_IRON = 256;
	private static final short MAX_CAPACITY_REDSTONE = 512;
	private static final short MAX_CAPACITY_OBSIDIAN = 1024;
	private static final short MAX_CAPACITY_GOLD = 2048;
	private static final short MAX_CAPACITY_DIAMOND = 4096;
	private static final short MAX_CAPACITY_EMERALD= 8192;
	private static final short MAX_CAPACITY_IRIDIUM = 16364;
	
	//Lapis, copper, tin, tungsten, coal?
	
	public CopyOfTileEntityStorageRack( )
	{
		this(1);
	}
	
	public static int getMaxCapacity(int damage)
	{
		switch(damage)
		{
			case 0:
				return MAX_CAPACITY_WOOD * MAX_STACK_SIZE;
			case 2:
				return MAX_CAPACITY_STONE * MAX_STACK_SIZE;
			case 4:
				return MAX_CAPACITY_IRON * MAX_STACK_SIZE;
			case 6:
				return MAX_CAPACITY_REDSTONE * MAX_STACK_SIZE;
			case 8:
				return MAX_CAPACITY_OBSIDIAN * MAX_STACK_SIZE;
			case 10:
				return MAX_CAPACITY_GOLD * MAX_STACK_SIZE;
			case 12:
				return MAX_CAPACITY_DIAMOND * MAX_STACK_SIZE;
			case 14:
				return MAX_CAPACITY_EMERALD * MAX_STACK_SIZE;
			case 16:
				return MAX_CAPACITY_IRIDIUM * MAX_STACK_SIZE;
		}
		
		return 0;
	}
	
	public CopyOfTileEntityStorageRack(int slots)
	{
		super();
		items = new ItemStack[slots];
		amounts = new int[slots];
		accessSlots = new ItemStack[slots];
	}
	
	@Override
	public boolean canUpdate( )
	{
		for(int i = 0; i < accessSlots.length; i++)
		{
			if(accessSlots[i] != null && this.getFreeSpace(i) > 0)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateEntity( )
	{
		for(int i = 0; i < accessSlots.length; i++)
		{
			if(accessSlots[i] != null && this.hasContainer(i) && this.getFreeSpace(i) > 0)
			{
				accessSlots[i] = this.addItemToContainer(i, accessSlots[i], true);
			}
		}
	}
	
	
	private int getFreeSpace(int slot)
	{
		int count = this.getContainerContentCount(slot);
		if(accessSlots[slot] != null)
		{
			count = this.getContainerContentCount(slot) + accessSlots[slot].stackSize;
		}
		
		int max = this.getMaxCapacity(items[slot].getItemDamage());
		
		return max - count;
	}
	
	public boolean isWaitingForDoubleClick( )
	{
		if(clicked != 0)
		{
			return true;
		}
		
		return false;
	}

	public boolean shouldTakeAllFromInventory( )
	{
		if(clicked == 0)
		{
			clicked = System.currentTimeMillis();
		}
		else
		{
			long currentTime = System.currentTimeMillis();
			
			if(currentTime - clicked < DOUBLE_CLICK_DELAY)
			{
				clicked = 0;
				return true;
			}
			
			clicked = 0;
		}
		
		return false;
	}
	public int getSize( )
	{
		return items.length;
	}
	
	private int getAccessSlotCount(int slot)
	{
		if(accessSlots[slot] != null)
		{
			return accessSlots[slot].stackSize;
		}
		
		return 0;
	}
	
	public int getContainerContentCount(int slot)
	{
		if(slot < items.length)
		{
			if(items[slot] != null && items[slot].hasTagCompound())
			{
				return amounts[slot] + this.getAccessSlotCount(slot);
			}
		}
		
		return 0;
	}
	
	public ItemStack getContainerContent(int slot)
	{
		if(slot < items.length)
		{
			if(items[slot] != null && items[slot].hasTagCompound())
			{
				ItemStack t = ItemStack.loadItemStackFromNBT(items[slot].getTagCompound());
				
				ItemStack t2 = ItemStack.copyItemStack(t);
				t2.stackSize = 1;
				
				return t2;
			}
		}
		
		return null;
	}
	
	public boolean addContainer(int slot, ItemStack container)
	{
		if(slot < items.length)
		{
			if(items[slot] == null)
			{
				items[slot] = container;
				
				if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
				{
					if(container.hasTagCompound())
					{
						if(container.getTagCompound().hasKey("ContainerAmount"))
						{
							amounts[slot] = container.getTagCompound().getInteger("ContainerAmount");
							container.getTagCompound().removeTag("ContainerAmount");
						}
					}
					this.updateTileEntity();
				}

				return true;
			}
		}
		
		return false;
	}
	
	public ItemStack removeContainer(int slot)
	{
		if(SideHelper.onServer() == true)
		{
			if(slot < items.length)
			{
				if(items[slot] != null)
				{
					ItemStack con = items[slot];
					con.stackSize = 1;
					
					int total = amounts[slot] + this.getAccessSlotCount(slot);
					if(con.hasTagCompound() == true)
					{
						con.getTagCompound().setInteger("ContainerAmount", total);
					}
					items[slot] = null;
					accessSlots[slot] = null;
					amounts[slot] = 0;
					
					if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
					{
						this.updateTileEntity();
					}
					return con;
				}
			}
		}
		return null;
	}
	
	public boolean hasContainer(int slot)
	{
		if(slot < items.length)
		{
			if(items[slot] != null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isContainerEmpty(int slot)
	{
		if(slot < items.length)
		{
			if(items[slot] != null)
			{
				if(items[slot].hasTagCompound())
				{
					return false;
				}
			}
		}
		
		return true;
	}

	public ItemStack removeItemFromContainer(int slot, boolean fullStack)
	{
		if(fullStack)
		{
			int stackSize = 64;
			if(this.hasContainer(slot) && this.isContainerEmpty(slot) == false)
			{
				ItemStack content = ItemStack.loadItemStackFromNBT(items[slot].getTagCompound());
				stackSize = content.getMaxStackSize();
			}
			else
			{
				//slot is invalid or empty
				return null;
			}

			return this.removeItemFromContainer(slot, stackSize);
		}
		else
		{
			return this.removeItemFromContainer(slot, 1);
		}
	}
	
	public ItemStack removeItemFromContainer(int slot, int count)
	{	
		if(slot < items.length)
		{
			if(this.hasContainer(slot) && amounts[slot] > 0 && this.isContainerEmpty(slot) == false)
			{
				ItemStack content = ItemStack.loadItemStackFromNBT(items[slot].getTagCompound());
				
				if(content != null)
				{
					int stackSize = count;
					
					if(amounts[slot] > stackSize)
					{
						ItemStack result = content.copy();
						result.stackSize = stackSize;
						
						amounts[slot] = amounts[slot] - stackSize;
						//this.updateAccessSlot(slot, result);
						this.updateTileEntity();						
						return result;
					}
					
					if(amounts[slot] == stackSize)
					{
						ItemStack result = content.copy();
						result.stackSize = stackSize;
						
						amounts[slot] = 0;
						items[slot].stackTagCompound = null;
						//this.updateAccessSlot(slot, null);
						this.updateTileEntity();						
						return result;
					}
					
					if(amounts[slot] < stackSize)
					{
						ItemStack result = content.copy();
						result.stackSize = amounts[slot];
						
						amounts[slot] = 0;
						items[slot].stackTagCompound = null;
						//this.updateAccessSlot(slot, null);
						
						this.updateTileEntity();						
						return result;
					}
				}
			}
		}
		return null;
	}
	
	public ItemStack addItemToContainer(int slot, ItemStack istack, boolean fullStack)
	{
		
		if(SideHelper.onServer() == true)
		{
			if(slot < items.length)
			{
				if(this.hasContainer(slot) && istack != null)
				{
					if(this.isContainerEmpty(slot) == false)
					{
						ItemStack contents = ItemStack.loadItemStackFromNBT(items[slot].getTagCompound());
						
						//equal items, just add on
						if(this.areItemStacksEqual(contents, istack))
						{
							int currentSize = amounts[slot];
							int additional = istack.stackSize;
							
							if(fullStack == false)
							{
								additional = 1;
							}

							int newSize = currentSize + additional;
							int max = this.maxCapacity(slot);
							
							//enough space for all
							if(newSize <= max)
							{
								amounts[slot] = newSize;
								//this.updateAccessSlot(slot, istack);
								lastItemAdded = istack;
								istack.stackSize -= additional;
								this.updateTileEntity();

								return istack;
							}
							
							//already full
							if(currentSize == max)
							{
								//this.updateAccessSlot(slot, istack);
								return istack;
							}
							
							//Partial fit
							if(currentSize < max && newSize > max)
							{
								int overflow = max - newSize;
								amounts[slot] = max;
								
								istack.stackSize = overflow;
								this.updateTileEntity();
								
								return istack;						
							}	
						}
						else	//itemstacks aren't equal, so just return what you put in
						{
							return istack;
						}
					}
					else	//no items in here yet
					{
						items[slot].stackTagCompound = null;
						NBTTagCompound contents = new NBTTagCompound();
						istack.writeToNBT(contents);
						items[slot].stackTagCompound = contents;
						
						amounts[slot] = istack.stackSize;
						//this.updateAccessSlot(slot, istack);
						istack.stackSize = 0;

						this.updateTileEntity();
						return istack;
						
					}
				}
			
			}
		}
		return istack;
	}
	
	private void updateAccessSlot(int slot, ItemStack istack)
	{
		if(istack == null)
		{
			accessSlots[slot] = null;
		}
		else
		{
			accessSlots[slot] = istack.copy();
			
			//more space than we need
			if(this.getFreeSpace(slot) >= istack.getMaxStackSize())
			{
				accessSlots[slot] = null;
				//accessSlots[slot].stackSize = 0;
			}
			
			if(this.getFreeSpace(slot) < istack.getMaxStackSize())
			{
				accessSlots[slot].stackSize = istack.getMaxStackSize() - this.getFreeSpace(slot);
			}
		}
	}
	
	public boolean willContainerAccept(int slot, ItemStack istack)
	{
		if(slot < items.length)
		{
			if(this.hasContainer(slot) && istack != null)
			{
				if(this.isContainerEmpty(slot) == false)
				{
					ItemStack contents = ItemStack.loadItemStackFromNBT(items[slot].getTagCompound());
					
					if(this.areItemStacksEqual(contents, istack) == true)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean areItemStacksEqual(ItemStack a, ItemStack b)
	{
		if(a.isItemEqual(b) && ItemStack.areItemStackTagsEqual(a, b))
		{
			return true;
		}
		
		//TODO
		//OreDictionary.getOreID(itemStack)
		//OreDictionary.itemMatches(target, input, strict)
		
		return false;
	}
	
	private int maxCapacity(int slot)
	{
		
		if(slot > items.length)
		{
			return 0;
		}
		
		int damage = items[slot].getItemDamage();
		
		switch(damage)
		{
			case 0:
				return MAX_CAPACITY_WOOD * MAX_STACK_SIZE;
			case 2:
				return MAX_CAPACITY_STONE * MAX_STACK_SIZE;
			case 4:
				return MAX_CAPACITY_IRON * MAX_STACK_SIZE;
			case 6:
				return MAX_CAPACITY_REDSTONE * MAX_STACK_SIZE;
			case 8:
				return MAX_CAPACITY_OBSIDIAN * MAX_STACK_SIZE;
			case 10:
				return MAX_CAPACITY_GOLD * MAX_STACK_SIZE;
			case 12:
				return MAX_CAPACITY_DIAMOND * MAX_STACK_SIZE;
			case 14:
				return MAX_CAPACITY_EMERALD * MAX_STACK_SIZE;
			case 16:
				return MAX_CAPACITY_IRIDIUM * MAX_STACK_SIZE;
		}
		
		return 0;
	}
	
	private void updateTileEntity( )
	{
		if(this.worldObj != null)
		{
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	@Override
    public Packet getDescriptionPacket()
    {
		NBTTagCompound send = new NBTTagCompound();
		this.writeToNBT(send);
		
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, send);
    }
	
	@Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
    {
    	NBTTagCompound tag = packet.data;
    	this.readFromNBT(tag);
    	
		this.updateTileEntity();
    }
	
	
    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        NBTTagList containers = tag.getTagList("Containers");
        
        int size = tag.getInteger("slots");
        items = new ItemStack[size];
        
        int[] t = tag.getIntArray("amounts");
        amounts = null;
        amounts = t;
        

        for (int i = 0; i < containers.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)containers.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < size)
            {
                items[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        
        NBTTagList aslots = tag.getTagList("accessSlots");
        
        accessSlots = new ItemStack[size];
        
        for (int i = 0; i < aslots.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)aslots.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < size)
            {
            	accessSlots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        
        //System.out.println("Got a packet and stack at..." + amounts[0]);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        NBTTagList nbttaglist = new NBTTagList();
        
        for (int i = 0; i < items.length; ++i)
        {
            if (items[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                items[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        
        NBTTagList nbttaglist2 = new NBTTagList();
        
        for (int i = 0; i < accessSlots.length; ++i)
        {
            if (accessSlots[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                accessSlots[i].writeToNBT(nbttagcompound1);
                nbttaglist2.appendTag(nbttagcompound1);
            }
        }
        
        tag.setTag("accessSlots", nbttaglist2);
        tag.setTag("Containers", nbttaglist);
        tag.setInteger("slots", items.length);
        tag.setIntArray("amounts", amounts);
    }

	@Override
	public int getSizeInventory()
	{
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		//System.out.println("getStackInSlot");
		//if(this.hasContainer(slot) && this.isContainerEmpty(slot) == false)
		//{
			return accessSlots[slot];
			//return ItemStack.loadItemStackFromNBT(items[slot].getTagCompound());
		//}
		
		//return null;
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int count)
	{
		return this.removeItemFromContainer(slot, count);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return null;
	}
	
	
	//this function works on modifying the original itemstack, be warned
	@Override
	public void setInventorySlotContents(int slot, ItemStack istack)
	{

		//System.out.println("setInventorySlotContents");
		if(istack != null && this.hasContainer(slot))
		{
			accessSlots[slot] = istack;
			/*
			//should we do special handling?
			if(accessSlots[slot] != null)
			{
				this.addItemWithOverflow(slot, istack);
			}
			else
			{
				this.addItemToContainer(slot, istack, true);
			}
			*/
		}
	}
	
	//true if leftover, false if all fit
	private boolean addItemWithOverflow(int slot, ItemStack istack)
	{
		ItemStack leftover = this.addItemToContainer(slot, istack, true);
		
		//it all fit
		if(leftover.stackSize < 1)
		{
			accessSlots[slot] = null;
			return false;
		}
		else
		{
			//some leftover
			accessSlots[slot].stackSize = leftover.getMaxStackSize();
			return true;
		}
	}

	@Override
	public String getInvName()
	{
		return "Discrete Storage Rack";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openChest(){}

	@Override
	public void closeChest(){}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack istack)
	{
		//System.out.println("isItemValidForSlot");
		if(this.hasContainer(slot))
		{
			//already item in container
			if(this.isContainerEmpty(slot) == false)
			{
				ItemStack contents = ItemStack.loadItemStackFromNBT(items[slot].getTagCompound());
				if(this.areItemStacksEqual(contents, istack))
				{
					return true;
					/*
					if(this.willFit(slot, istack.stackSize))
					{
						this.updateAccessSlot(slot, null);
						return true;
					}
					else
					{
						//the stack won't fit as a whole but part of it might
						if(this.getFreeSpace(slot) > 0)
						{

							this.updateAccessSlot(slot, istack);
							return false;
						}
						else
						{
							this.updateAccessSlot(slot, istack);
						}
					}
					*/
				}
			}
			else
			{
				//nothing in the container
				//System.out.println("[3]isItemValidForSlot:" + true);
				return true;
			}
		}
		//System.out.println("isItemValidForSlot:" + false);
		return false;
	}
	
	private boolean willFit(int slot, int number)
	{
		if((amounts[slot] + number) < this.getMaxCapacity(items[slot].getItemDamage()))
		{
			return true;
		}
		
		return false;
	}
	/*
	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		//System.out.println("getAccessibleSlotsFromSide");
		int[] slots = new int[items.length];
				
		for(int i = 0; i < items.length; i++)
		{
			slots[i] = i;
		}
		
		return slots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack istack, int side)
	{
		//System.out.println("canInsertItem");
		if(this.willContainerAccept(slot, istack))
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j)
	{
		// TODO Auto-generated method stub
		return false;
	}
	*/


}
