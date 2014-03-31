package kappafox.di.electrics.tileentities;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.tile.IWrenchable;
import kappafox.di.DiscreteIndustry;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;

public class TileEntityDiscreteCable extends TileEntityDiscreteBlock implements IEnergyConductor, IWrenchable
{
	
	private boolean addedToEnergyNet = false;
	private boolean registerForUpdate = true;
	private boolean firstTick = true;
	private short facing;
	
	private boolean[] connections = new boolean[6];

	private int cable;									//the current cable type in this block
	// 0 = no cable
	// 1 = tin
	// 2 = copper
	// 3 = gold
	// 4 = glass
	// 5 = HV
	
	public TileEntityDiscreteCable( )
	{
		this(0);
	}
	
	public TileEntityDiscreteCable(int meta_)
	{
		boolean def = true;
		
		//facadeBlockID = id_;
		if(meta_ == 0)
		{
			def = false;
		}
		for(int i = 0; i < 6; i++)
		{
			connections[i] = def;
		}
		
		cable = meta_;
		//3 seems to tbe the default face for most blocks;
		//facing = 3;
	}
	
	
	
	@Override
	public void invalidate( )
	{
		
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		}
		
		addedToEnergyNet = false;
		super.invalidate();
	}
	
	@Override
	//@SideOnly(Side.SERVER)
	public void onChunkUnload( )
	{
		
		//IC2 api requires these events only be posted serverside
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		}
		addedToEnergyNet = false;
		registerForUpdate = true;
		firstTick = true;
		
	}
	
	@Override
	public boolean canUpdate( )
	{
		//return registerForUpdate;
		return true;
	}
	

	@Override
	//@SideOnly(Side.SERVER)
	public void updateEntity( )
	{
		if(firstTick == true)
		{
			if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
			{
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			}
			registerForUpdate = false;
			addedToEnergyNet = true;
			firstTick = false;
		}
	}
	
	/*
	@Override
	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}
	*/



	@Override
	public double getConductionLoss()
	{
		return DiscreteIndustry.librarian.ic2.cableLoss[cable];
	}
	
	public boolean getConnection(int side_)
	{
		return connections[side_];
	}
	
	public boolean[] getConnectionArray( )
	{
		return connections;
	}
	
	@Override
	public int getInsulationEnergyAbsorption()
	{
		//System.out.println("InsBreak");
		return DiscreteIndustry.librarian.ic2.cableInsulationAbsorbEnergy[cable];
	}

	@Override
	public int getInsulationBreakdownEnergy()
	{
		//System.out.println("InsBreak" + DiscreteIndustry.librarian.ic2.cableInsulationMeltEnergy[cable]);
		return DiscreteIndustry.librarian.ic2.cableInsulationMeltEnergy[cable];
	}

	@Override
	public int getConductorBreakdownEnergy()
	{
		//System.out.println("CondBreak" + DiscreteIndustry.librarian.ic2.cableMeltEnergy[cable]);
		return DiscreteIndustry.librarian.ic2.cableMeltEnergy[cable];
	}

	@Override
	public void removeInsulation(){}

	@Override
	public void removeConductor()
	{
		//System.out.println("Blow!");
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		}
		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, 0, 0, 7);
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	
	private boolean changeFace = true;
	
	@Override
	public boolean wrenchCanSetFacing(EntityPlayer player_, int side_)
	{
		
		//System.out.println("wrenchCanSetFacing:" + player_.toString() + ":" + side_);
		/*
		if(player_.isSneaking())
		{
			changeFace = true;
			return true;
		}
		*/
		
		if(cable == 0)
		{
			return false;
		}
		
		changeFace = false;
		
		if(connections[side_] == true)
		{
			connections[side_] = false;				
		}
		else
		{
			connections[side_] = true;
		}
		
		//System.out.println(side_ + ":" + connections[side_]);
		
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			//setting true to play the sound
		}
		return true;

	}
	
	@Override
	public short getFace( )
	{
		return this.getFacing();
	}
	
	@Override
	public void setFace(short face_)
	{
		this.setFacing(face_);
	}
	
	@Override
	public short getFacing()
	{
		//System.out.println("getFacing");
		return facing;
	}

	@Override
	public void setFacing(short facing_)
	{		
		
		//System.out.println("setFacing:" + facing_);
		if(changeFace == true )
		{
			facing = facing_;	
		}
		else
		{
			changeFace = true;
		}
			
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		//this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer)
	{
		return false;
	}

		
	public void writeToNBT(NBTTagCompound nbt_)
	{
		
		super.writeToNBT(nbt_);
		
		int[] cons = new int[6];
		for(int i = 0; i < 6; i++)
		{
			if(connections[i] == true)
			{
				cons[i] = 1;
			}
			else
			{
				cons[i] = 0;
			}
		}
		nbt_.setIntArray("cons", cons);
		nbt_.setShort("facing", facing);
		nbt_.setInteger("cable", cable);
		
		//this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	public void readFromNBT(NBTTagCompound nbt_)
	{
		super.readFromNBT(nbt_);
		
		facing = nbt_.getShort("facing");
		cable = nbt_.getInteger("cable");
		
		int[] t = nbt_.getIntArray("cons");
		
		for(int i = 0; i < 6; i++)
		{
			if(t[i] == 1)
			{
				connections[i] = true;
			}
			else
			{
				connections[i] = false;
			}
		}
		
		//sometimes this happens before we have a valid world object
		if(worldObj != null)
		{
			this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
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
    	readFromNBT(tag);
    }
	

	public int getCableType( )
	{
		return cable;
	}
	
	
	public void setMeta(int metadata_)
	{
		cable = metadata_;
	}
	

	@Override
	public float getWrenchDropRate() {return 0;}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {return null;}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter_, ForgeDirection direction_) 
	{
		return connections[this.directionToSide(direction_)];
	}

	@Override
	public boolean emitsEnergyTo(TileEntity receiver_, ForgeDirection direction_) 
	{
		return connections[this.directionToSide(direction_)];
	}
	
}
