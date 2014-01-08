package mariculture.core.blocks;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeIngotCasting;
import mariculture.api.core.RecipeSmelter.SmelterOutput;
import mariculture.core.blocks.base.TileStorageTank;
import mariculture.core.lib.MetalRates;
import mariculture.core.network.Packet118FluidUpdate;
import mariculture.core.network.Packet120ItemSync;
import mariculture.core.network.Packets;
import mariculture.factory.blocks.Tank;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TileIngotCaster extends TileStorageTank implements ISidedInventory {
	public int machineTick;
	public boolean canWork;
	public int freezeTick;
	
	public TileIngotCaster() {
		inventory = new ItemStack[4];
		tank = new Tank(MetalRates.INGOT * 4);
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	public boolean onTick(int i) {
		return machineTick %i == 0;
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			machineTick++;
			
			if(onTick(30)) {
				canWork = canWork();
			}
			
			if(canWork) {
				freezeTick++;
				if(freezeTick >= 50) {
					RecipeIngotCasting result = MaricultureHandlers.casting.getResult(tank.getFluid());
					if(result != null) {
						for(int i = 0; i < inventory.length; i++) {
							if(inventory[i] == null) {
								drain(ForgeDirection.UP, result.fluid.copy(), true);
								setInventorySlotContents(i, result.output.copy());
								break;
							}
						}
					}
					
					freezeTick = 0;
					canWork = canWork();
				}
			}
		}
	}
	
	public boolean canWork() {
		return !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && hasRoom() && canFreeze();
	}
	
	public boolean canFreeze() {
		return MaricultureHandlers.casting.getResult(tank.getFluid()) != null;
	}
	
	public boolean hasRoom() {
		return inventory[0] == null || inventory[1] == null || inventory[2] == null || inventory[3] == null;
	}
	
	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		
		if(!worldObj.isRemote) {
			 Packets.updateTile(this, 64, new Packet120ItemSync(xCoord, yCoord, zCoord, inventory).build());
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int amount =  tank.fill(resource, doFill);
        if (amount > 0 && doFill)
        	Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
        return amount;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack amount = tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain)
        	Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
        return amount;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side == ForgeDirection.UP.ordinal())
			return new int[] { };
		else
			return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return side != ForgeDirection.UP.ordinal();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		freezeTick = nbt.getInteger("FreezeTick");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("FreezeTick", freezeTick);
	}
}
