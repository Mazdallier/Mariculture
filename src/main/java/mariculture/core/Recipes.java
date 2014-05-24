package mariculture.core;

import static mariculture.core.helpers.RecipeHelper.*;
import static mariculture.core.lib.ItemLib.*;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.Extra;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.ItemLib;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.RockMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.util.Fluids;
import mariculture.fishery.Fish;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class Recipes {
	public static void add() {
		RecipesSmelting.add();
		addCraftingItems();
		addMetalRecipes();
		addUpgradeRecipes();
		addAnvilRecipes();
	//Items
		addShaped(hammer, new Object[] {"PP ", " SP", "S  ", 'P', burntBrick, 'S', netherBrick});
		addShaped(ladle, new Object[] {" C ", " C ", "C  ", 'C', "ingotCopper"});
		addShaped(titaniumBucket, new Object[] {"T T", " T ", 'T', "ingotTitanium"});
		addShaped(copperBattery, new Object[] {" I ", "TRT", "TRT", 'I', "ingotIron", 'R', "dustRedstone", 'T', "ingotCopper"});
		addShaped(titaniumBattery, new Object[] {" I ", "TRT", "TRT", 'I', "ingotIron", 'R', "dustRedstone", 'T', "ingotTitanium"});
		addShaped(_(emptyBottle, 3), new Object[] {"G G", " G ", 'G', heatglass});
		addShapeless(_(voidBottle, 8), new Object[] {emptyBottle, "dustRedstone", ink});
		addShaped(oysterPie, new Object[] { "SSS", "BOP", "WEW", 'S', "foodSalt", 'B', beef, 'O', oyster, 'P', pork, 'W', wheat, 'E', egg });
		
	//Blocks
		addShaped(baseBrick, new Object[] {"IGI", "G G", "IGI", 'I', burntBrick, 'G', ironBars});
		addShaped(baseIron, new Object[] {"IGI", "G G", "IGI", 'I', "ingotIron", 'G', glassPane});
		addShaped(baseWood, new Object[] {"IGI", "G G", "IGI", 'I', "logWood", 'G', fence});

	//Machines
		addShaped(airPump, new Object[] {"WGW", "PRP", "PMP", 'G', "glass", 'R', "dustRedstone", 'P', "plankWood", 'M', piston, 'W', ironWheel});
		addShaped(_(tank, 2), new Object[] {"CWC", "WGW", "CWC", 'C', "ingotCopper", 'W', "plankWood", 'G', "glass"});
		addShaped(storageBookshelf, new Object[] {"SPS", "PCP", "SSS", 'P', "plankWood", 'S', bookshelf, 'C', chest});
		addShaped(crucible, new Object[] {" L ", "BGB", "HCH", 'B', burntBrick, 'L', lava, 'G', tank, 'H', heating, 'C', baseBrick});
		addShaped(anvil, new Object[] {"CCC", " N ", "BBB", 'C', baseBrick, 'B', burntBrick, 'N', netherBrick});
		addShaped(vat, new Object[] {"C C", "C C", "CCC", 'C', "ingotCopper"});
		addShaped(blockCaster, new Object[] {"BBB", "B B", "BBB", 'B', burntBrick});
		addShaped(ingotCaster, new Object[] {" B ", "BBB", " B ", 'B', burntBrick});
		addShaped(nuggetCaster, new Object[] {" B ", "B B", " B ", 'B', burntBrick});
		
		for (int i = 0; i < 12; i++) {
			RecipeHelper.add4x4Recipe(_(pearlBlock, i, 1), _(pearls, i, 1));
			RecipeHelper.addUncraftingRecipe(_(pearls, i, 4), _(pearlBlock, i, 1));
		}

		addShaped(_(piston), new Object[] {"TTT", "#X#", "#R#", '#', "cobblestone", 'X', "ingotAluminum", 'R', "dustRedstone", 'T', "plankWood"});
	}

	private static void addCraftingItems() {
		addShaped(life, new Object[] {"DSR", "FHB", "PAC", 'D', dandelion, 'S', "treeSapling", 'R', rose, 'F', fish, 'H', regen, 'B', bait, 'P', potato, 'A', lily, 'C', carrot});
		addVatItemRecipe(_(string), Fluids.gold, MetalRates.INGOT * 4, goldSilk, 5);
		addShaped(goldThread, new Object[] {"ABA", "ABA", 'B', polishedStick, 'A', goldSilk});
		addShaped(neoprene,  new Object[] {"IPI", "PEP", "IPI", 'I', rubber, 'P', pearls, 'E', bottleGas });
		addShaped(_(neoprene, 2),  new Object[] {"IPI", "PEP", "IPI", 'I', rubber, 'P', pearls, 'E', bottleGas2 });
		addVatItemRecipe(_(limestone, 4), Fluids.natural_gas, 5000, plastic, 45);
		if(FluidRegistry.getFluid("bioethanol") != null) addVatItemRecipe(_(limestone, 4), "bioethanol", 10000, plastic, 60);
		addShaped(plasticLens, new Object[] {" N ", "NGN", " N ", 'N', neoprene, 'G', transparent});
		addShaped(glassLens, new Object[] {" P ", "PGP", " P ", 'P', "plankWood", 'G', "glass"});
		
		addShaped(heating, new Object[] {"CCC", "CCC", 'C', carbide});
		addShaped(cooling, new Object[] {"  P", "PI ", "  P", 'P', "plankWood", 'I', "ingotIron"});
		addShaped(cooling, new Object[] {" P ", " I ", "P P", 'P', "plankWood", 'I', "ingotIron"});
		addShaped(cooling, new Object[] {"P  ", " IP", "P  ", 'P', "plankWood", 'I', "ingotIron"});
		addShaped(cooling, new Object[] {"P P", " I ", " P ", 'P', "plankWood", 'I', "ingotIron"});
		addShaped(carbide, new Object[] {" S ", "FBF", " S ", 'F', coal, 'S', sand, 'B', blockClay});
		addWheelRecipe(_(ironWheel, 3), "ingotIron", "slabWood");
		addCrossHatchRecipe(wicker, "stickWood", reeds);
		addVatItemRecipe(_(plastic, 4), Fluids.gold, MetalRates.BLOCK, goldPlastic, 60 * 5);
		addVatItemRecipe(_(stick),  Fluids.titanium, MetalRates.INGOT * 3, titaniumRod, 60);
		addVatItemRecipe(_(netherBrick), "lava", 250, burntBrick, 8);
		
		if(Extra.OVERWORLD) {
			addVatItemRecipe(_(brick), "lava", 500, burntBrick, 16);
		}
		
		if(!Modules.isActive(Modules.worldplus)) {
			add9x9Recipe(new ItemStack(Core.food, 1, FoodMeta.KELP_WRAP), ItemLib.cactusGreen);
		}
 	}
	
	private static void addMetalRecipes() {		
		add9x9Recipe(ingotRutile, "nuggetRutile");
		addUncraftingRecipe(_(nuggetRutile, 9), "ingotRutile");
		add9x9Recipe(ingotMagnesium, "nuggetMagnesium");
		addUncraftingRecipe(_(nuggetMagnesium, 9), "ingotMagnesium");
		add9x9Recipe(ingotTitanium, "nuggetTitanium");
		addUncraftingRecipe(_(nuggetTitanium, 9), "ingotTitanium");
		add9x9Recipe(ingotAluminum, "nuggetAluminum");
		addUncraftingRecipe(_(nuggetAluminum, 9), "ingotAluminum");
		add9x9Recipe(ingotCopper, "nuggetCopper");
		addUncraftingRecipe(_(nuggetCopper, 9), "ingotCopper");
		add9x9Recipe(_(ingotIron), "nuggetIron");
		addUncraftingRecipe(_(nuggetIron, 9), "ingotIron");
		
		add9x9Recipe(blockRutile, "ingotRutile");
		addUncraftingRecipe(_(ingotRutile, 9), "blockRutile");
		add9x9Recipe(blockMagnesium, "ingotMagnesium");
		addUncraftingRecipe(_(ingotMagnesium, 9), "blockMagnesium");
		add9x9Recipe(blockTitanium, "ingotTitanium");
		addUncraftingRecipe(_(ingotTitanium, 9), "blockTitanium");
		add9x9Recipe(blockAluminum, "ingotAluminum");
		addUncraftingRecipe(_(ingotAluminum, 9), "blockAluminum");
		add9x9Recipe(blockCopper, "ingotCopper");
		addUncraftingRecipe(_(ingotCopper, 9), "blockCopper");
		addSmelting(ingotCopper, oreCopper, 0.5F);
	}

	private static void addUpgradeRecipes() {
		//Storage
		ItemStack previous = addUpgrade(UpgradeMeta.BASIC_STORAGE, new Object[] {"WPW", "DCD", "WPW", 'D', "ingotCopper", 'P', "plankWood", 'C', chest, 'W', wicker});
		previous = addUpgrade(UpgradeMeta.STANDARD_STORAGE, new Object[] {"WCW", "CUC", "STS", 'C', "ingotCopper", 'S', "slabWood", 'T', chest, 'W', wicker, 'U', previous});
		previous = addUpgrade(UpgradeMeta.ADVANCED_STORAGE, new Object[] {"CSC", "AUA", "WTW", 'C', "ingotCopper", 'S', "slabWood", 'T', chest, 'A', aluminumSheet, 'W', wicker, 'U', previous});
		addUpgrade(UpgradeMeta.ULTIMATE_STORAGE, new Object[] {"GWG", "WUW", "ATA", 'G', "ingotGold", 'T', chest, 'A', aluminumSheet, 'W', wicker, 'U', previous});

		//Cooling
		previous = addUpgrade(UpgradeMeta.BASIC_COOLING, new Object[] {" S ", "CBC", " S ", 'S', snowball, 'B', snow, 'C', cooling});
		previous = addUpgrade(UpgradeMeta.STANDARD_COOLING, new Object[] {"ACA", "SUS", "CAC", 'S', snowball, 'A', "ingotAluminum", 'C', cooling, 'U', previous});
		previous = addUpgrade(UpgradeMeta.ADVANCED_COOLING, new Object[] {"CTC", "IUI", "TRT", 'I', ice, 'R', "ingotIron", 'T', "ingotAluminum", 'C', cooling, 'U', previous});
		addUpgrade(UpgradeMeta.ULTIMATE_COOLING, new Object[] {"TCT", "IUI", "CDC", 'I', ice, 'D', "ingotAluminum", 'T', "ingotTitanium", 'C', cooling, 'U', previous});
		
		//Heating
		previous = addUpgrade(UpgradeMeta.BASIC_HEATING, new Object[] {"HIH", 'I', "ingotIron", 'H', heating});
		previous = addUpgrade(UpgradeMeta.STANDARD_HEATING, new Object[] {"A A", "HUH", " A ", 'A', "ingotAluminum", 'H', heating, 'U', previous});
		previous = addUpgrade(UpgradeMeta.ADVANCED_HEATING, new Object[] {"IHI", "TUT", "IHI", 'T', "ingotTitanium", 'I', "ingotIron", 'H', heating, 'U', previous});
		addUpgrade(UpgradeMeta.ULTIMATE_HEATING, new Object[] {"TDT", "HUH", "GTG", 'G', "ingotGold", 'T', "ingotTitanium", 'D', blazeRod ,'H', heating, 'U', previous});

		//Purity and Filtrator
		previous = RecipeHelper.addUpgrade(UpgradeMeta.BASIC_PURITY, new Object[] {"MPM", "PIP", "MPM", 'I', "dyeRed", 'M', "ingotAluminum",'P', whitePearl});
		ItemStack previous2 = RecipeHelper.addUpgrade(UpgradeMeta.FILTER, new Object[] {"MPM", "CUC", "AMA", 'A', "ingotAluminum", 'M', "ingotMagnesium", 'P', bluePearl, 'C', cooling, 'U', previous});
		addUpgrade(UpgradeMeta.FILTER_2, new Object[] {"UAU", 'A', "ingotAluminum", 'U', previous2});
		previous = addUpgrade(UpgradeMeta.STANDARD_PURITY, new Object[] {"PHP", "NUN", "MHM", 'H', heart, 'N', "ingotTitanium", 'M', "ingotAluminum", 'P', whitePearl, 'U', previous});
		previous = addUpgrade(UpgradeMeta.ADVANCED_PURITY, new Object[] {"PSP", "AUA", "TPT", 'A', heart, 'T', "ingotTitanium", 'P', whitePearl, 'U', previous, 'S', goldSilk});
		addUpgrade(UpgradeMeta.ULTIMATE_PURITY, new Object[] {"PSP", "TUT", "PAP", 'A', heart, 'T', "ingotTitanium", 'P', whitePearl, 'U', previous, 'S', goldThread});

		//Impurity and salinator
		previous = RecipeHelper.addUpgrade(UpgradeMeta.BASIC_IMPURITY, new Object[] {"NWN", "ESE", "NGN", 'N', netherrack, 'W', netherWart, 'E', fermentedEye, 'S', bone, 'G', "ingotGold"});
		previous2 = RecipeHelper.addUpgrade(UpgradeMeta.SALINATOR, new Object[] {"ASA", "SUS", "MAM", 'A', "ingotAluminum", 'S', "foodSalt", 'M', "ingotMagnesium", 'U', previous});
		addUpgrade(UpgradeMeta.SALINATOR_2, new Object[] {"UAU", 'A', "ingotAluminum", 'U', previous2});
		previous = RecipeHelper.addUpgrade(UpgradeMeta.STANDARD_IMPURITY, new Object[] {"TGT", "SUS", "PFP", 'T', ghastTear, 'F', fermentedEye, 'P', poison, 'S', attack, 'G', goldSilk, 'U', previous});
		previous = RecipeHelper.addUpgrade(UpgradeMeta.ADVANCED_IMPURITY, new Object[] {"HGH", "FUF", "SPS", 'F', night, 'P', poison, 'H', attack, 'S', fermentedEye, 'G', "blockGold", 'U', previous});
		RecipeHelper.addUpgrade(UpgradeMeta.ULTIMATE_IMPURITY, new Object[] {"SGS", "PUP", "FSF", 'F', ender, 'P', poison, 'S', attack, 'G', goldThread, 'U', previous});
		
		//Ethereal
		addUpgrade(UpgradeMeta.ETHEREAL, new Object[] {"PUP", "GEG", "PDP", 'P', enderPearl, 'G', "ingotGold", 'E', eyeOfEnder, 'D', diamond, 'U', redstoneTorch});
		
		//Speed
		previous = addUpgrade(UpgradeMeta.BASIC_SPEED, new Object[] { " W ", "WSW", " W ", 'W', sugar, 'S', "ingotIron" });
		previous = addUpgrade(UpgradeMeta.STANDARD_SPEED, new Object[] {" S ", "AUA", " S ", 'A', "ingotAluminum", 'S', sugar, 'U', previous});
		previous = addUpgrade(UpgradeMeta.ADVANCED_SPEED, new Object[] {"ASA", "TUT", "SNS", 'A', "ingotAluminum", 'S', sugar, 'T', "ingotTitanium", 'N', ice, 'U', previous});
		addUpgrade(UpgradeMeta.ULTIMATE_SPEED, new Object[] {"TRT", "SUS", "ARA", 'A', aluminumSheet, 'S', sugar, 'T', "ingotTitanium", 'R', goldRail, 'U', previous});
		
		//Basic Capacitor
		RecipeHelper.addShaped(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_RF), new Object[] {
			" T ", "CRC", 'T', Blocks.redstone_torch, 'C', "ingotCopper", 'R', "dustRedstone"
		});
		
		//Standard Capacitor
		RecipeHelper.addShaped(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_RF), new Object[] {
			"CTC", "QUQ", "RCR", 'C', "ingotCopper", 'T', Blocks.redstone_torch, 'Q', Items.quartz, 'R', Items.repeater,
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_RF)
		});
		
		//Advanced Capacitor
		RecipeHelper.addShaped(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_RF), new Object[] {
			" D ", "RUR", "QCQ", 'D', "dustRedstone", 'Q', Items.quartz, 'R', Items.repeater,
			'C', new ItemStack(Core.batteryCopper, 1, OreDictionary.WILDCARD_VALUE),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_RF)
		});
		
		//Ultimate Capacitor
		RecipeHelper.addShaped(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_RF), new Object[] {
			" C ", "RUR", "QBQ", 'Q', Items.comparator, 'R', "dustRedstone", 'B', "blockRedstone",
			'C', new ItemStack(Core.batteryTitanium, 1, OreDictionary.WILDCARD_VALUE),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_RF)
		});
	}
	
	public static void addAnvilRecipes() {
		addAnvilRecipe(blockAluminum, _(aluminumSheet, 8), 50);
		addAnvilRecipe(blockTitanium, _(titaniumSheet, 8), 150);
		addAnvilRecipe(_(bone), _(bonemeal, 5), 10);
		addAnvilRecipe(new ItemStack(Blocks.red_flower, 1, 0), new ItemStack(Items.dye, 2, Dye.RED), 10);
		addAnvilRecipe(new ItemStack(Blocks.red_flower, 1, 1), new ItemStack(Items.dye, 2, Dye.LIGHT_BLUE), 10);
		addAnvilRecipe(new ItemStack(Blocks.red_flower, 1, 2), new ItemStack(Items.dye, 2, Dye.MAGENTA), 10);
		addAnvilRecipe(new ItemStack(Blocks.red_flower, 1, 3), new ItemStack(Items.dye, 2, Dye.LIGHT_GREY), 10);
		addAnvilRecipe(new ItemStack(Blocks.red_flower, 1, 4), new ItemStack(Items.dye, 2, Dye.RED), 10);
		addAnvilRecipe(new ItemStack(Blocks.red_flower, 1, 5), new ItemStack(Items.dye, 2, Dye.ORANGE), 10);
		addAnvilRecipe(new ItemStack(Blocks.red_flower, 1, 6), new ItemStack(Items.dye, 2, Dye.LIGHT_GREY), 10);
		addAnvilRecipe(new ItemStack(Blocks.red_flower, 1, 7), new ItemStack(Items.dye, 2, Dye.PINK), 10);
		addAnvilRecipe(new ItemStack(Blocks.red_flower, 1, 8), new ItemStack(Items.dye, 2, Dye.LIGHT_GREY), 10);
		addAnvilRecipe(new ItemStack(Blocks.yellow_flower), new ItemStack(Items.dye, 3, Dye.YELLOW), 10);
		addAnvilRecipe(new ItemStack(Blocks.double_plant, 1, 0), new ItemStack(Items.dye, 4, Dye.YELLOW), 10);
		addAnvilRecipe(new ItemStack(Blocks.double_plant, 1, 1), new ItemStack(Items.dye, 4, Dye.MAGENTA), 10);
		addAnvilRecipe(new ItemStack(Blocks.double_plant, 1, 4), new ItemStack(Items.dye, 4, Dye.RED), 10);
		addAnvilRecipe(new ItemStack(Blocks.double_plant, 1, 5), new ItemStack(Items.dye, 4, Dye.PINK), 10);
	}
}
