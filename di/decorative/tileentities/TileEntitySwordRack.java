package kappafox.di.decorative.tileentities;

import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;

public class TileEntitySwordRack extends TileEntityDiscreteBlock implements IInventory
{
	private ItemStack[] items = new ItemStack[6];
	
	public TileEntitySwordRack( )
	{
		this(6);

	}
	
	public TileEntitySwordRack(int itemCount_)
	{
		super();
		this.setFullColour(true);
		
		if(itemCount_ > 0)
		{
			items = new ItemStack[itemCount_];
		}
		else
		{
			items = new ItemStack[1];
		}
		
	}
	
	
	public void writeToNBT(NBTTagCompound nbt_)
	{		
		super.writeToNBT(nbt_);
        NBTTagList itemTag = new NBTTagList();

        for(int i = 0; i < items.length; i++)
        {
            if(items[i] != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte)i);
                items[i].writeToNBT(tag);
                itemTag.appendTag(tag);
            }
        }
		
        nbt_.setTag("Items", itemTag);
	}
	
	public void readFromNBT(NBTTagCompound nbt_)
	{
		super.readFromNBT(nbt_);
        NBTTagList nbttaglist = nbt_.getTagList("Items");
        
        items = new ItemStack[this.getSizeInventory()];

        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.getSizeInventory())
            {
                items[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        

	}

	public ItemStack getItem(int slot_)
	{
		if(this.isValidSlot(slot_))
		{
			return items[slot_];
		}
		
		return null;
	}
	
	public void setItem(ItemStack istack_, int slot_)
	{
		if(this.isValidSlot(slot_))
		{
			if(istack_ != null)
			{
				items[slot_] = istack_;
			}	
		}

	}
	

	private boolean isValidSlot(int slot_)
	{
		if(slot_ < items.length && slot_ >= 0)
		{
			return true;
		}
		
		return false;
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
    	readFromNBT(tag);
    	
        this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
    

	@Override
	public int getSizeInventory() 
	{
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot_) 
	{
		return items[slot_];
	}

	@Override
    public ItemStack decrStackSize(int slot_, int amount_)
    {
		ItemStack item = getStackInSlot(slot_);
		
		if(item != null)
		{
			if(item.stackSize <= amount_)
			{
				setInventorySlotContents(slot_, null);
			}
			else
			{
				item = item.splitStack(amount_);
				
				if(item.stackSize == 0)
				{
					setInventorySlotContents(slot_, null);
				}
			}
		}
		
		return item;    	
    }

	@Override
    public ItemStack getStackInSlotOnClosing(int slot_)
    {
        ItemStack stack = getStackInSlot(slot_);
        if (stack != null) 
        {
        	setInventorySlotContents(slot_, null);
        }
        return stack;
    }

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack_)
	{
		items[i] = itemstack_;
		
		if(itemstack_ != null && itemstack_.stackSize > getInventoryStackLimit())
		{
			itemstack_.stackSize = getInventoryStackLimit();
		}	
	}

	@Override
	public String getInvName() 
	{
		return "Sword Rack";
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
	public boolean isUseableByPlayer(EntityPlayer player_) 
	{
		return true;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return true;
	}
	
}
