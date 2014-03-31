package kappafox.di.decorative.tileentities;

import ic2.api.tile.IWrenchable;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;

public class TileEntityStripHazardBlock extends TileEntityDiscreteBlock implements IWrenchable
{
	private int stripIterations;
	private short facing = 0;
	private short strips[];
	
	
	
	public TileEntityStripHazardBlock( )
	{
		this(6);
	}
	
	public TileEntityStripHazardBlock(int stripCount_)
	{
		stripIterations = stripCount_;
		strips = new short[6];
		
		for(int i = 0; i < strips.length; i++)
		{
			strips[i] = 0;
		}
	}
	
	public boolean isStripHidden(int side_)
	{
		if(strips[side_] == stripIterations)
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public short getFacing( )
	{
		return facing;
	}
	
	@Override
	public void setFacing(short facing_)
	{
		facing = facing_;
		this.incrementStripPosition((short)facing_);
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}	

	public short getStripPosition(int side_)
	{
		return strips[side_];
	}
	
	public void setStripPosition(short side_, short pos_)
	{
		strips[side_] = pos_;
	}
	
	@Override
	public boolean wrenchCanSetFacing(EntityPlayer player_, int side_) 
	{
		
		if(player_.isSneaking() == true)
		{
			return true;
		}
		return false;
	}
	
	private void incrementStripPosition(short side_)
	{
		short pos = getStripPosition(side_);
		
		if(pos == stripIterations)
		{
			this.setStripPosition(side_, (short)0);
		}
		else
		{
			pos++;
			this.setStripPosition(side_, pos);
		}
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) 
	{
		return false;
	}

	@Override
	public float getWrenchDropRate() 
	{
		return 0.0F;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) 
	{
		return null;
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
    	//super.readFromNBT(tag);
    	readFromNBT(tag);
    }
	
	
	public void writeToNBT(NBTTagCompound nbt_)
	{
		super.writeToNBT(nbt_);
		
		int[] send = new int[6];
		for(int i = 0; i < strips.length; i++)
		{
			send[i] = strips[i];
		}
		
		nbt_.setInteger("iterations", stripIterations);
		nbt_.setIntArray("strips", send);
		nbt_.setShort("facing", facing);
	}
	
	public void readFromNBT(NBTTagCompound nbt_)
	{
		super.readFromNBT(nbt_);
		stripIterations = nbt_.getInteger("iterations");
		
		int[] send = nbt_.getIntArray("strips");
		
		for(int i = 0; i < send.length; i++)
		{
			strips[i] = (short)send[i];
		}
		
		facing = nbt_.getShort("facing");
		
		//sometimes this happens before we have a valid world object
		if(worldObj != null)
		{
			this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		}
	}	
}
