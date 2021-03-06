package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.bone;
import static joshie.mariculture.core.lib.MCLib.bonemeal;
import static joshie.mariculture.core.lib.MCLib.dropletEarth;
import static joshie.mariculture.core.lib.MCLib.skull;
import static joshie.mariculture.core.lib.MCLib.witherSkull;

import java.util.ArrayList;

import joshie.mariculture.api.core.CachedCoords;
import joshie.mariculture.api.core.Environment.Height;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import joshie.mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishBoneless extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 8;
    }

    @Override
    public int getTemperatureTolerance() {
        return 13;
    }

    @Override
    public Salinity getSalinityBase() {
        return FRESH;
    }

    @Override
    public int getSalinityTolerance() {
        return 1;
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 30;
    }

    @Override
    public int getFertility() {
        return 50;
    }

    @Override
    public int getFoodConsumption() {
        return 0;
    }

    @Override
    public boolean requiresFood() {
        return false;
    }

    @Override
    public int getWaterRequired() {
        return 150;
    }
    
    @Override
    public boolean isValidWater(Block block) {
        return super.isValidWater(block) || Fluids.isEnder(block);
    }

    @Override
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN ? 1 : 0;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 1D);
        addProduct(bonemeal, 5D);
        addProduct(bone, 1.5D);
        addProduct(skull, 1D);
        addProduct(witherSkull, 0.1D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.0D;
    }

    @Override
    public int getLiquifiedProductChance() {
        return 1;
    }

    @Override
    public int getFishMealSize() {
        return 0;
    }

    @Override
    public int getFoodStat() {
        return -1;
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(500) == 0) {
            EntitySkeleton skeleton = new EntitySkeleton(world);
            skeleton.setPosition(x, y, z);
            if (world.rand.nextInt(5000) == 0) {
                skeleton.setSkeletonType(1);
                skeleton.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
                skeleton.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
            }

            world.spawnEntityInWorld(skeleton);
        }
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return !isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isCave(height) ? 10D : !world.isDaytime() ? 10D : 0D;
    }
}
