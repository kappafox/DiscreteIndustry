package kappafox.di.electrics.items;

import kappafox.di.DiscreteIndustry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHorseInspector extends Item
{
	public ItemHorseInspector(int id_, String name_, int maxStack_)
	{
		super(id_);
		this.setUnlocalizedName(name_);
		this.setMaxStackSize(maxStack_);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register_)
    {	
        this.itemIcon = register_.registerIcon(DiscreteIndustry.MODID + ":" + "itemHorseInspector");
    }

	
	

}
