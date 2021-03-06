package joshie.mariculture.fishery.fish.dna;

import joshie.mariculture.api.fishery.fish.FishDNA;
import joshie.mariculture.api.fishery.fish.FishSpecies;

public class FishDNAFertility extends FishDNA {
    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return species.getFertility();
    }
    
    @Override
    public int getCopyChance() {
        return 15;
    }
}
