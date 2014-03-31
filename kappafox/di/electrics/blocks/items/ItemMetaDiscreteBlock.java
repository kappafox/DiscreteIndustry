package kappafox.di.electrics.blocks.items;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public abstract class ItemMetaDiscreteBlock extends ItemBlock
{

	public ItemMetaDiscreteBlock(int par1)
	{
		super(par1);
		setHasSubtypes(true);
	}
	
	@Override
	public abstract String getUnlocalizedName(ItemStack istack_);
	
	@Override
	public int getMetadata(int par1_)
	{
		return par1_;
	}

}
