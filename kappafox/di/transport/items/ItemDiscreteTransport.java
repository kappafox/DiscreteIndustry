package kappafox.di.transport.items;

import java.util.HashMap;
import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.transport.blocks.BlockDiscreteTransport;
import kappafox.di.transport.tileentities.TileEntityStorageRack;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemDiscreteTransport extends Item
{
	
	private static HashMap<Integer, String> names = new HashMap<Integer, String>();
	private static HashMap<Integer, Icon> icons = new HashMap<Integer, Icon>();
	
	private static Icon ICON_STORAGERACK_WOOD;
	private static Icon ICON_STORAGERACK_STONE;
	private static Icon ICON_STORAGERACK_IRON;
	private static Icon ICON_STORAGERACK_REDSTONE;
	private static Icon ICON_STORAGERACK_OBSIDIAN;
	private static Icon ICON_STORAGERACK_GOLD;
	private static Icon ICON_STORAGERACK_DIAMOND;
	private static Icon ICON_STORAGERACK_EMERALD;
	private static Icon ICON_STORAGERACK_IRIDIUM;
	
	public ItemDiscreteTransport(int id)
	{
		super(id);
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setUnlocalizedName("BaseDiscreteTransportItem");
		
		names.put(0, "Wooden Storage Unit");
		names.put(2, "Stone Storage Unit");
		names.put(4, "Iron Storage Unit");
		names.put(6, "Redstone Storage Unit");
		names.put(8, "Obsidian Storage Unit");
		names.put(10, "Gold Storage Unit");
		names.put(12, "Diamond Storage Unit");
		names.put(14, "Emerald Storage Unit");
		names.put(16, "Iridium Storage Unit");
	}
	
	@Override
	public void registerIcons(IconRegister ireg)
	{
		this.itemIcon = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitWood");
		
		ICON_STORAGERACK_WOOD =  this.itemIcon;
		ICON_STORAGERACK_STONE = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitStone");
		ICON_STORAGERACK_IRON = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitIron");
		ICON_STORAGERACK_REDSTONE = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitRedstone");
		ICON_STORAGERACK_OBSIDIAN = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitObsidian");
		ICON_STORAGERACK_GOLD = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitGold");
		ICON_STORAGERACK_DIAMOND = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitDiamond");
		ICON_STORAGERACK_EMERALD = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitEmerald");
		ICON_STORAGERACK_IRIDIUM = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitIridium");
		
		icons.put(0, ICON_STORAGERACK_WOOD);
		icons.put(2, ICON_STORAGERACK_STONE);
		icons.put(4, ICON_STORAGERACK_IRON);
		icons.put(6, ICON_STORAGERACK_REDSTONE);
		icons.put(8, ICON_STORAGERACK_OBSIDIAN);
		icons.put(10, ICON_STORAGERACK_GOLD);
		icons.put(12, ICON_STORAGERACK_DIAMOND);
		icons.put(14, ICON_STORAGERACK_EMERALD);
		icons.put(16, ICON_STORAGERACK_IRIDIUM);	
	}
	
	@Override
    public Icon getIconFromDamage(int damage)
    {
        return icons.get(damage);
    }
	
	@Override
    public String getUnlocalizedName(ItemStack istack)
    {
        String name = names.get(istack.getItemDamage());
        
        if(name != null)
        {
        	return name;
        }

        return "ERROR";
        
    }
	
	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) 
	{
		
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 2));
		list.add(new ItemStack(id, 1, 4));
		list.add(new ItemStack(id, 1, 6));
		list.add(new ItemStack(id, 1, 8));
		list.add(new ItemStack(id, 1, 10));
		list.add(new ItemStack(id, 1, 12));
		list.add(new ItemStack(id, 1, 14));
		list.add(new ItemStack(id, 1, 16));
		
	}
	
	public String getName(int damage)
	{
        String name = names.get(damage);
        
        if(name != null)
        {
        	return name;
        }
        
        return "ERROR";
	}
	
	public void addInformation(ItemStack istack, EntityPlayer player, List tooltip, boolean par4) 
	{
		//Storage Units
		if(istack.getItemDamage() >= 0 && istack.getItemDamage() <= 16)
		{
			if(istack.hasTagCompound())
			{
				if(istack.getTagCompound().hasKey("ContainerAmount"))
				{
					ItemStack contents = ItemStack.loadItemStackFromNBT(istack.getTagCompound());
					if(contents != null)
					{
						int amount = istack.getTagCompound().getInteger("ContainerAmount");
						
						tooltip.add(amount + " Units of " + contents.getDisplayName());
					}
				}
			}
			else
			{
				tooltip.add(TileEntityStorageRack.getMaxCapacity(istack.getItemDamage()) + " Unit Capacity");
				tooltip.add("Empty");
			}
		}
		
	}

}
