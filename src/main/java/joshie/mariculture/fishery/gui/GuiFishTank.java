package joshie.mariculture.fishery.gui;

import joshie.lib.helpers.ClientHelper;
import joshie.mariculture.Mariculture;
import joshie.mariculture.core.gui.GuiMariculture;
import joshie.mariculture.core.gui.feature.Feature;
import joshie.mariculture.core.network.PacketClick;
import joshie.mariculture.core.network.PacketHandler;
import joshie.mariculture.fishery.tile.TileFishTank;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiFishTank extends GuiMariculture {
    private TileFishTank tile;

    public GuiFishTank(InventoryPlayer player, TileFishTank tile) {
        super(new ContainerFishTank(tile, player), "fishtank", 52);

        this.tile = tile;
    }

    @Override
    public int getX() {
        return 22;
    }

    @Override
    protected void onMouseClick(int x, int y) {
        if (mouseX >= -18 && mouseX <= 2 && mouseY >= 104 && mouseY <= 124) {
            tile.thePage -= 1;
            if (tile.thePage < 0) {
                tile.thePage = tile.MAX_PAGES - 1;
            }
            
            PacketHandler.sendToServer(new PacketClick(tile.xCoord, tile.yCoord, tile.zCoord, tile.previous));
            PacketHandler.sendToServer(new PacketClick(tile.xCoord, tile.yCoord, tile.zCoord, ClientHelper.getPlayer().getEntityId()));
            ClientHelper.getPlayer().openGui(Mariculture.instance, -1, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
        }

        if (mouseX >= 172 && mouseX <= 192 && mouseY >= 104 && mouseY <= 124) {
            tile.thePage += 1;
            if (tile.thePage >= tile.MAX_PAGES) {
                tile.thePage = 0;
            }
            
            PacketHandler.sendToServer(new PacketClick(tile.xCoord, tile.yCoord, tile.zCoord, tile.next));
            PacketHandler.sendToServer(new PacketClick(tile.xCoord, tile.yCoord, tile.zCoord, ClientHelper.getPlayer().getEntityId()));
            ClientHelper.getPlayer().openGui(Mariculture.instance, -1, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
        }
    }

    @Override
    public void drawForeground() {
        fontRendererObj.drawString("Page: " + (tile.thePage + 1) + "/" + TileFishTank.MAX_PAGES, 100, ySize - 96 + 3, 4210752);
        fontRendererObj.drawString("Page: " + (tile.thePage + 1) + "/" + TileFishTank.MAX_PAGES, 100, nameHeight, 4210752);
    }

    @Override
    public void drawBackground(int x, int y) {
        mc.renderEngine.bindTexture(Feature.texture);
        drawTexturedModalRect(x - 18, y + 103, 176, 99, 21, 22);
        drawTexturedModalRect(x + 173, y + 103, 176, 122, 21, 22);
        drawTexturedModalRect(x - 18 + 3, y + 103 + 2, 54, 220, 18, 18);
        if (mouseX >= -18 && mouseX <= 2 && mouseY >= 104 && mouseY <= 124) {
            drawTexturedModalRect(x - 18 + 3, y + 103 + 2, 0, 220, 18, 18);
        }

        drawTexturedModalRect(x + 173, y + 103 + 2, 36, 220, 18, 18);
        if (mouseX >= 172 && mouseX <= 192 && mouseY >= 104 && mouseY <= 124) {
            drawTexturedModalRect(x + 173, y + 103 + 2, 18, 220, 18, 18);
        }
        
        mc.renderEngine.bindTexture(TEXTURE);
    }
}
