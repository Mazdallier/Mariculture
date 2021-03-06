package joshie.mariculture.core.lib;

import net.minecraft.util.DamageSource;

public class MaricultureDamage extends DamageSource {
    public static DamageSource piranha = new MaricultureDamage("piranha").setDamageBypassesArmor();
    public static DamageSource oneRing = new MaricultureDamage("oneRing").setDamageBypassesArmor();
    public static DamageSource scald = new MaricultureDamage("scald");
    public static DamageSource turbine = new MaricultureDamage("turbine").setDamageBypassesArmor();
    public static DamageSource siamese = new MaricultureDamage("siamese").setDamageBypassesArmor();

    private MaricultureDamage(String name) {
        super(name);
    }
}
