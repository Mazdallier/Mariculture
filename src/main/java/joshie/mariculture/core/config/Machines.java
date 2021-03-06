package joshie.mariculture.core.config;

import static joshie.mariculture.core.helpers.ConfigHelper.getBoolean;
import static joshie.mariculture.core.helpers.ConfigHelper.getInt;
import static joshie.mariculture.core.helpers.ConfigHelper.setCategory;
import static joshie.mariculture.core.helpers.ConfigHelper.setConfig;
import joshie.mariculture.core.events.MaricultureEvents;
import joshie.mariculture.core.lib.MachineSpeeds;
import net.minecraftforge.common.config.Configuration;

public class Machines {
    public static void init(Configuration config) {
        setConfig(config);
        setCategory("Speed Settings");
        MachineSpeeds.feeder = getInt("Fish Feeder", 200);
        MachineSpeeds.crucible = getInt("Crucible Furnace", 40000);
        MachineSpeeds.net = getInt("Fishing Net", 300);
        MachineSpeeds.sawmill = getInt("Sawmill", 650);
        MachineSpeeds.hatchery = getInt("Hatchery", 100);

        setCategory("Client Settings", "The settings only affect clientside");
        Client.GEYSER_ANIM = getBoolean("Geyser - Enable Particles", true);
        Client.PUMP_ANIM = getBoolean("Air Pump - Enable Rotation", true, "This will not work if Enable Ticking is set to false under Tick Settings");
        Client.HAMMER_ANIM = getBoolean("Autohammer - Enabled Animation", true);
        Client.SHOW_FISH = getBoolean("Fish Feeder > Show Fish", true);

        setCategory("Tick Settings");
        Ticks.ITEM_EJECT_TICK = getInt("Item Eject Tick", 20);
        Ticks.FLUID_EJECT_TICK = getInt("Fluid Eject Tick", 100);
        Ticks.EFFECT_TICK = getInt("Fish Feeder > Effect Tick", 20, "This is how many ticks for an effect to occur in a fish tank, such as poison or regen");
        Ticks.PUMP_ENABLE_TICKS = getBoolean("Air Pump - Enable Ticking", true);
        Ticks.FISH_FOOD_TICK = getInt("Fish Feeder > Fish Food Tick Rate", 25, "This is how many minecraft ticks, before attempting to pick up fish food, set to 0 to disable");
        Ticks.PICKUP_TICK = getInt("Fish Feeder > Fish Food Pickup Tick Rate", -1, "How often it tries to pick up fish food, set to less than 0 to disable");

        setCategory("Machine Settings");
        MachineSettings.PACKET_DISTANCE = getInt("How many blocks away to send rendering packet updates to players", 176);
        MachineSettings.PURITY = getInt("Crucible Furnace > mB Per Purity Upgrade Level", 32);
        MachineSettings.ENABLE_PURITY_IN_CRUCIBLE = getBoolean("Crucible Furnace > Enable Purity Bonus", false);
        MaricultureEvents.onConfigure("Machines", config);
    }

    public static class Client {
        public static boolean SHOW_FISH;
        public static boolean PUMP_ANIM;
        public static boolean GEYSER_ANIM;
        public static boolean HAMMER_ANIM;
    }

    public static class MachineSettings {
        public static int PACKET_DISTANCE;
        public static int PURITY;
        public static boolean ENABLE_PURITY_IN_CRUCIBLE;
    }

    public static class Ticks {
        public static int ITEM_EJECT_TICK;
        public static int FLUID_EJECT_TICK;
        public static int EFFECT_TICK;
        public static boolean PUMP_ENABLE_TICKS;
        public static int FISH_FOOD_TICK;
        public static int TANK_UPDATE;
        public static int PICKUP_TICK;
    }
}
