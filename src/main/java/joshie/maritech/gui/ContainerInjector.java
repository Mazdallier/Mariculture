package joshie.maritech.gui;

import joshie.mariculture.api.core.interfaces.IItemUpgrade;
import joshie.mariculture.core.gui.ContainerMachine;
import joshie.mariculture.core.gui.SlotFluidContainer;
import joshie.mariculture.core.gui.SlotOutput;
import joshie.mariculture.core.helpers.FluidHelper;
import joshie.mariculture.fishery.FishFoodHandler;
import joshie.mariculture.fishery.Fishery;
import joshie.mariculture.fishery.items.ItemFishy;
import joshie.maritech.extensions.modules.ExtensionFishery;
import joshie.maritech.tile.TileInjector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;

public class ContainerInjector extends ContainerMachine {
    public ContainerInjector(TileInjector tile, InventoryPlayer playerInventory) {
        super(tile);

        addUpgradeSlots(tile);
        addPowerSlot(tile);
        addSlotToContainer(new SlotFluidContainer(tile, 4, 28, 25));
        addSlotToContainer(new SlotFluidContainer(tile, 5, 86, 25));
        addSlotToContainer(new SlotOutput(tile, 6, 28, 56));
        addSlotToContainer(new SlotOutput(tile, 7, 86, 56));
        addSlotToContainer(new SlotFish(tile, 8, 108, 39));
        addSlotToContainer(new SlotOutput(tile, 9, 152, 39));
        addSlotToContainer(new SlotDNA(tile, 10, -19, 14));
        addSlotToContainer(new SlotDNA(tile, 11, -19, 32));
        addSlotToContainer(new SlotDNA(tile, 12, -19, 50));
        bindPlayerInventory(playerInventory, 10);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        int size = getSizeInventory();
        int low = size + 27;
        int high = low + 9;
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            if (slotID < size) {
                if (!mergeItemStack(stack, size, high, true)) return null;

                slot.onSlotChange(stack, itemstack);
            } else if (slotID >= size) {
                if (stack.getItem() instanceof ItemFishy) {
                    if (!mergeItemStack(stack, 8, 9, false)) return null; //Slot 8 for the Fish
                } else if (stack.getItem() instanceof IItemUpgrade) {
                    if (!mergeItemStack(stack, 0, 3, false)) return null; //Slot 0-2 for the Upgrades
                } else if (stack.getItem() instanceof IEnergyContainerItem) {
                    if (!mergeItemStack(stack, 3, 4, false)) return null; //Slot 3 for the Energy
                } else if (FluidHelper.isFluidOrEmpty(stack) || FishFoodHandler.isFishFood(stack)) {
                    if (!mergeItemStack(stack, 4, 6, false)) return null; // Slot 4-5 for the Liquids
                } else if (stack.getItem() == ExtensionFishery.dna) {
                    if (!mergeItemStack(stack, 10, 13, false)) return null; // Slot 10-12 for the dna
                } else if (slotID >= size && slotID < low) {
                    if (!mergeItemStack(stack, low, high, false)) return null;
                } else if (slotID >= low && slotID < high && !mergeItemStack(stack, high, low, false)) return null;
            } else if (!mergeItemStack(stack, size, high, false)) return null;

            if (stack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (stack.stackSize == itemstack.stackSize) return null;

            slot.onPickupFromSlot(player, stack);
        }

        return itemstack;
    }

    private class SlotFish extends Slot {
        public SlotFish(IInventory inventory, int id, int x, int y) {
            super(inventory, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() == Fishery.fishy;
        }
    }

    private class SlotDNA extends Slot {
        public SlotDNA(IInventory inventory, int id, int x, int y) {
            super(inventory, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() == ExtensionFishery.dna;
        }
    }
}