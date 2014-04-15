package kappafox.di.base.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityDiscreteBlock extends TileEntitySubtype
{

	

	private int facadeBlockID = 810;					//the current block we look like
	private int facadeMeta = 0;						//the damage value of the meta based facade
	private short facing;
	private int colour = 16777215;
	private int var1 = 0;
	//private int subType = 0;
	private boolean fullColour = false;
	private int textureOrienation = 0;
	private short direction = 0;
	
	private int[] blockIDs = new int[6];
	private int[] blockMetas = new int[6];
	private int[] blockSides = new int[6];
	
	private int originalID = 0;
	private int originalMeta = 0;
	
	public TileEntityDiscreteBlock( )
	{
		this(0, 0);
	}
	
	
	public TileEntityDiscreteBlock(int id, int meta)
	{
		for(int i = 0; i < 6; i++)
		{
			blockIDs[i] = id;
			blockMetas[i] = meta;
			blockSides[i] = i;
		}
		
		originalID = id;
		originalMeta = meta;
		setSubtype(0);
	}
	
	public TileEntityDiscreteBlock(int id, int meta, int sub)
	{
		this(id, meta);
		super.setSubtype(sub);
	}
	

	
	public void setAllTextureSources(int id, int meta, int side)
	{
		for(int i = 0; i < 6; i++)
		{
			this.setTextureSource(id, meta, side, i);
		}
	}
	
	public void setAllTexturesFromSource(int id, int meta)
	{
		for(int i = 0; i < 6; i++)
		{
			this.setTextureSource(id, meta, i, i);
		}
	}
		
	//target ID, target Meta, target Side, Side actually clicked of this block
	public void setTextureSource(int id, int meta, int side, int hitside)
	{
		if(side >= 0 || side < blockIDs.length)
		{
			blockIDs[hitside] = id;
			blockMetas[hitside] = meta;
			blockSides[hitside] = side;
		}
	}
	
	public int getTextureSource(int side)
	{
		if(side < 0 || side >= blockIDs.length)
		{
			return 0;
		}

		return blockIDs[side];


	}

	
	public void setTextureSourceMeta(int meta, int side)
	{
		blockMetas[side] = meta;
	}
	
	public int getTextureSourceMeta(int side)
	{
		if(side < 0 || side >= blockMetas.length)
		{
			return 0;
		}
		

		return blockMetas[side];	


		//return facadeMeta;		
	}
	
	//side of this block to get from
	public int getTextureSourceSide(int side)
	{
		if(side < 0 || side >= blockSides.length)
		{
			return 0;
		}
		
		return blockSides[side];
	}

	public int getBlockColor( )
	{
		return colour;
	}
	
	public void setBlockColor(int col)
	{
		colour = col;
	}

	
	
	private boolean changeFace = true;


	public short getFace( )
	{
		return facing;
	}

	public void setFace(short facing)
	{		
		
		//System.out.println("setFacing:" + facing);
		if(changeFace == true )
		{
			facing = facing;	
		}
		else
		{
			changeFace = true;
		}
			
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		//this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	public int getVariable( )
	{
		return var1;
	}
	
	public void setVariable(int var)
	{
		var1 = var;
	}
	
	public int getTextureOrientation( )
	{
		return textureOrienation;
	}
	
	public void setTextureOrientation(int orient)
	{
		textureOrienation = orient;
	}
	
	public short getDirection( )
	{
		return direction;
	}
	
	public void setDirection(short dir)
	{
		direction = dir;
	}
	
	public void setOriginalID(int id)
	{
		originalID = id;
	}
	
	public int getOriginalID( )
	{
		return originalID;
	}
	
	public int getOriginalMeta( )
	{
		return originalMeta;
	}
	

		
	public void writeToNBT(NBTTagCompound nbt)
	{
		
		super.writeToNBT(nbt);
		
		nbt.setInteger("facadeBlockID", facadeBlockID);
		nbt.setInteger("facadeMeta", facadeMeta);
		nbt.setShort("facing", facing);
		nbt.setInteger("colour", colour);
		nbt.setInteger("var1", var1);
		//nbt.setInteger("subType", subType);
		nbt.setInteger("torient", textureOrienation);
		nbt.setShort("direction", direction);
		
		nbt.setIntArray("blockIDs", blockIDs);
		nbt.setIntArray("blockMetas", blockMetas);
		nbt.setIntArray("blockSides", blockSides);
		
		nbt.setBoolean("fullColour", fullColour);
		
		nbt.setInteger("originalID", originalID);
		nbt.setInteger("originalMeta", originalMeta);
		
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		facadeBlockID = nbt.getInteger("facadeBlockID");
		facadeMeta = nbt.getInteger("facadeMeta");
		facing = nbt.getShort("facing");
		colour = nbt.getInteger("colour");
		var1 = nbt.getInteger("var1");
		//subType = nbt.getInteger("subType");
		textureOrienation = nbt.getInteger("torient");
		
		blockIDs = nbt.getIntArray("blockIDs");
		blockMetas = nbt.getIntArray("blockMetas");
		blockSides = nbt.getIntArray("blockSides");
		
		fullColour = nbt.getBoolean("fullColour");
		direction = nbt.getShort("direction");
		
		originalID = nbt.getInteger("originalID");
		originalMeta = nbt.getInteger("originalMeta");
		
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
	
	
	
	public boolean isFullColour( )
	{
		return fullColour;
	}
	
	public void setFullColour(boolean b)
	{
		fullColour = b;
	}
	




	
	protected int directionToSide(ForgeDirection dir)
	{
		
		if(dir == ForgeDirection.DOWN)
		{
			return 0;
		}
		
		if(dir == ForgeDirection.UP)
		{
			return 1;
		}
		
		if(dir == ForgeDirection.NORTH)
		{
			return 2;
		}
		
		if(dir == ForgeDirection.SOUTH)
		{
			return 3;
		}
		
		if(dir == ForgeDirection.WEST)
		{
			return 4;
		}
		
		if(dir == ForgeDirection.EAST)
		{
			return 5;
		}
		

		
		return 0;
	}

}
