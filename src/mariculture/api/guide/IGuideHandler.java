package mariculture.api.guide;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public interface IGuideHandler {
	public void registerFluidIcon(String key, String fluid);
	public void registerIcon(String key, ItemStack stack);
	public void registerOreDicIcon(String key, ItemStack stack);
	public void registerCyclingMetaIcon(String key, ItemStack stack, int max);
	public void registerPageHandler(String key, PageParser parser);
}
