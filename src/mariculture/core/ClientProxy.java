package mariculture.core;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import tconstruct.TConstruct;
import mariculture.Mariculture;
import mariculture.core.blocks.TileAirPump;
import mariculture.core.blocks.TileAnvil;
import mariculture.core.blocks.TileOyster;
import mariculture.core.blocks.TileVat;
import mariculture.core.guide.GuideRegistry;
import mariculture.core.handlers.ClientEventHandler;
import mariculture.core.handlers.KeyBindingHandler;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.render.AnvilSpecialRenderer;
import mariculture.core.render.ModelOyster;
import mariculture.core.render.RenderDouble;
import mariculture.core.render.RenderFakeItem;
import mariculture.core.render.RenderSingle;
import mariculture.core.render.RenderSingleItem;
import mariculture.core.render.RenderTanks;
import mariculture.core.render.VatSpecialRenderer;
import mariculture.core.util.EntityFakeItem;
import mariculture.diving.render.ModelAirPump;
import mariculture.factory.EntityFLUDDSquirt;
import mariculture.factory.FLUDDKeyHandler;
import mariculture.factory.Factory;
import mariculture.factory.blocks.TileFLUDDStand;
import mariculture.factory.blocks.TileTurbineGas;
import mariculture.factory.blocks.TileTurbineHand;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.factory.render.ModelFLUDD;
import mariculture.factory.render.ModelTurbineGas;
import mariculture.factory.render.ModelTurbineHand;
import mariculture.factory.render.ModelTurbineWater;
import mariculture.factory.render.RenderCustomItem;
import mariculture.factory.render.RenderFLUDDSquirt;
import mariculture.fishery.EntityBass;
import mariculture.fishery.EntityFishing;
import mariculture.fishery.Fishery;
import mariculture.fishery.blocks.TileFeeder;
import mariculture.fishery.blocks.TileFishTank;
import mariculture.fishery.blocks.TileSift;
import mariculture.fishery.render.FishTankSpecialRenderer;
import mariculture.fishery.render.ModelFeeder;
import mariculture.fishery.render.ModelSift;
import mariculture.fishery.render.RenderFishingHook;
import mariculture.fishery.render.RenderProjectileFish;
import mariculture.plugins.compatibility.CompatBooks;
import mariculture.sealife.EntityHammerhead;
import mariculture.sealife.render.RenderHammerhead;
import mariculture.transport.EntitySpeedBoat;
import mariculture.transport.Transport;
import mariculture.transport.render.RenderSpeedBoat;
import mariculture.transport.render.RenderSpeedBoatItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	public static final float scale = (float) (1.0 / 20.0);
	private static final ResourceLocation AIR_PUMP = new ResourceLocation(Mariculture.modid, "textures/blocks/air_pump_texture.png");
	private static final ResourceLocation OYSTER = new ResourceLocation(Mariculture.modid, "textures/blocks/oyster_texture.png");
	private static final ResourceLocation SIFT = new ResourceLocation(Mariculture.modid, "textures/blocks/sift_texture.png");
	private static final ResourceLocation FEEDER = new ResourceLocation(Mariculture.modid, "textures/blocks/feeder_texture.png");
	private static final ResourceLocation TURBINE = new ResourceLocation(Mariculture.modid, "textures/blocks/turbine_texture.png");
	private static final ResourceLocation TURBINE_GAS = new ResourceLocation(Mariculture.modid, "textures/blocks/turbine_gas_texture.png");
	private static final ResourceLocation TURBINE_HAND = new ResourceLocation(Mariculture.modid, "textures/blocks/turbine_hand_texture.png");
	private static final ResourceLocation FLUDD = new ResourceLocation(Mariculture.modid, "textures/blocks/fludd_texture.png");
	private static final ResourceLocation PRESSURE_VESSEL = new ResourceLocation(Mariculture.modid, "textures/blocks/pressure_vessel_texture.png");

	@Override
	public void initClient() {	
		KeyBindingRegistry.registerKeyBinding(new KeyBindingHandler());
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		
		RenderIds.BLOCK_SINGLE = RenderingRegistry.getNextAvailableRenderId();
		RenderIds.BLOCK_DOUBLE = RenderingRegistry.getNextAvailableRenderId();
		RenderIds.BLOCK_TANKS = RenderingRegistry.getNextAvailableRenderId();

		MinecraftForgeClient.registerItemRenderer(Core.singleBlocks.blockID, new RenderSingleItem());
		MinecraftForgeClient.registerItemRenderer(Core.oysterBlock.blockID, new RenderSingleItem());
		ClientRegistry.bindTileEntitySpecialRenderer(TileOyster.class, new RenderSingle(new ModelOyster(scale), OYSTER));
		ClientRegistry.bindTileEntitySpecialRenderer(TileVat.class, new VatSpecialRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileAnvil.class, new AnvilSpecialRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileAirPump.class, new RenderSingle(new ModelAirPump(scale), AIR_PUMP));
		RenderingRegistry.registerBlockHandler(new RenderTanks());
		RenderingRegistry.registerBlockHandler(new RenderDouble());
		RenderingRegistry.registerBlockHandler(new RenderSingle());
		RenderingRegistry.registerEntityRenderingHandler(EntityFakeItem.class, new RenderFakeItem());
		
		if(Modules.diving.isActive()) {
			RenderIds.DIVING = RenderingRegistry.addNewArmourRendererPrefix("diving");
			RenderIds.SCUBA = RenderingRegistry.addNewArmourRendererPrefix("scuba");
			RenderIds.SNORKEL = RenderingRegistry.addNewArmourRendererPrefix("snorkel");
		}
		
		if(Modules.factory.isActive()) {
			KeyBindingRegistry.registerKeyBinding(new FLUDDKeyHandler());
			RenderingRegistry.registerEntityRenderingHandler(EntityFLUDDSquirt.class, new RenderFLUDDSquirt());
			RenderIds.FLUDD = RenderingRegistry.addNewArmourRendererPrefix("fludd");
			MinecraftForgeClient.registerItemRenderer(Factory.customBlock.blockID, new RenderCustomItem());
			MinecraftForgeClient.registerItemRenderer(Factory.customFlooring.blockID, new RenderCustomItem());
			MinecraftForgeClient.registerItemRenderer(Factory.customSlabs.blockID, new RenderCustomItem());
			MinecraftForgeClient.registerItemRenderer(Factory.customSlabsDouble.blockID, new RenderCustomItem());
			MinecraftForgeClient.registerItemRenderer(Factory.customStairs.blockID, new RenderCustomItem());
			MinecraftForgeClient.registerItemRenderer(Factory.customFence.blockID, new RenderCustomItem());
			MinecraftForgeClient.registerItemRenderer(Factory.customGate.blockID, new RenderCustomItem());
			MinecraftForgeClient.registerItemRenderer(Factory.customLight.blockID, new RenderCustomItem());
			MinecraftForgeClient.registerItemRenderer(Factory.customWall.blockID, new RenderCustomItem());
			MinecraftForgeClient.registerItemRenderer(Factory.customRFBlock.blockID, new RenderCustomItem());
			MinecraftForgeClient.registerItemRenderer(Factory.fludd.itemID, new RenderSingleItem());
			ClientRegistry.bindTileEntitySpecialRenderer(TileFLUDDStand.class, new RenderSingle(new ModelFLUDD(scale), FLUDD));
			ClientRegistry.bindTileEntitySpecialRenderer(TileTurbineWater.class, new RenderSingle(new ModelTurbineWater(scale), TURBINE));
			ClientRegistry.bindTileEntitySpecialRenderer(TileTurbineGas.class, new RenderSingle(new ModelTurbineGas(scale), TURBINE_GAS));
			ClientRegistry.bindTileEntitySpecialRenderer(TileTurbineHand.class, new RenderSingle(new ModelTurbineHand(scale), TURBINE_HAND));
		}
		
		if(Modules.fishery.isActive()) {
			RenderingRegistry.registerEntityRenderingHandler(EntityFishing.class, new RenderFishingHook());
			RenderingRegistry.registerEntityRenderingHandler(EntityBass.class, new RenderProjectileFish(Fishery.bass.fishID));
			MinecraftForgeClient.registerItemRenderer(Fishery.siftBlock.blockID, new RenderSingleItem());
			ClientRegistry.bindTileEntitySpecialRenderer(TileFeeder.class, new RenderSingle(new ModelFeeder(scale), FEEDER));
			ClientRegistry.bindTileEntitySpecialRenderer(TileSift.class, new RenderSingle(new ModelSift(scale), SIFT));
			ClientRegistry.bindTileEntitySpecialRenderer(TileFishTank.class, new FishTankSpecialRenderer());
		}
		
		if(Modules.sealife.isActive()) {
			RenderingRegistry.registerEntityRenderingHandler(EntityHammerhead.class, new RenderHammerhead());
		}
		
		if(Modules.transport.isActive()) {
			RenderingRegistry.registerEntityRenderingHandler(EntitySpeedBoat.class, new RenderSpeedBoat());
			MinecraftForgeClient.registerItemRenderer(Transport.speedBoat.itemID, new RenderSpeedBoatItem());
		}
	}
	
	public static Document breeding;
	public static Document diving;
	public static Document enchants;
	public static Document fishing;
	public static Document machines;
	public static Document processing;
	
	@Override
	public void loadBooks() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		breeding = loadGuide("breeding", factory);
        diving = loadGuide("diving", factory);
        enchants = loadGuide("enchants", factory);
        fishing = loadGuide("fishing", factory);
        machines = loadGuide("machines", factory);
        processing = loadGuide("processing", factory);
        GuideRegistry.init();
	}
	
	private Document loadGuide (String location, DocumentBuilderFactory factory) {
        try {
        	String lang = FMLClientHandler.instance().getCurrentLanguage();
            InputStream stream = Mariculture.class.getResourceAsStream("/assets/mariculture/xml/" + location + "_" + lang + ".xml");
            if(stream == null)
            	stream = Mariculture.class.getResourceAsStream("/assets/mariculture/xml/" + location + "_en_US.xml");
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(stream);
            doc.getDocumentElement().normalize();
            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public static Document getDocument(String xml) {
		if(xml.equals("breeding"))
			return breeding;
		if(xml.equals("diving"))
			return diving;
		if(xml.equals("enchants"))
			return enchants;
		if(xml.equals("fishing"))
			return fishing;
		if(xml.equals("machines"))
			return machines;
		if(xml.equals("processing"))
			return processing;
		return CompatBooks.getDocument(xml);
	}
}