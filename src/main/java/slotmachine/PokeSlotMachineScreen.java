package slotmachine;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Client-side screen for PokéSlot Machine. Handles animation, spin button, and result display.
 */
public class PokeSlotMachineScreen extends AbstractContainerScreen<PokeSlotMachineMenu> {
    private static final ResourceLocation BG_TEXTURE = ResourceLocation.parse("pokeslot:textures/gui/slot_machine_bg.png");

    // Animation state
    private int[] slotIndices = new int[] {0, 0, 0};
    private boolean spinning = false;
    private int spinTicks = 0;
    private String resultText = "";

    public PokeSlotMachineScreen(PokeSlotMachineMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        // Draw background
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(BG_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        // Draw slot symbols
        for (int i = 0; i < 3; i++) {
            PokeSlotSymbols symbol = PokeSlotSymbols.values()[slotIndices[i]];
            ResourceLocation symbolTex = ResourceLocation.parse("pokeslot:textures/gui/" + symbol.getTextureName() + ".png");
            int iconX = x + 40 + i * 40;
            int iconY = y + 30;
            guiGraphics.blit(symbolTex, iconX, iconY, 0, 0, 32, 32);
        }

        // Draw result text overlay
        if (!resultText.isEmpty()) {
            guiGraphics.drawCenteredString(this.font, resultText, x + this.imageWidth / 2, y + 80, 0xFF0000);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // ...existing code...
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        // ...existing code...
    }

    @Override
    protected void init() {
        super.init();
        // Add SPIN button
        // ...existing code...
    }

    /**
     * Handles SPIN button click: starts animation and sends spin event.
     */
    private void onSpinButtonPressed() {
        if (!spinning) {
            spinning = true;
            spinTicks = 0;
            resultText = "";
            // Play spin_start sound
            // Send spin event to server
            // TODO: ici on enverra l'action spin au serveur via le menu/container (DataSlot ou callback)
        }
    }

    /**
     * Called when spin result is received from server.
     */
    public void onSpinResult(int[] resultIndices, String resultText) {
        this.slotIndices = resultIndices;
        this.spinning = false;
        this.resultText = resultText;
        // Play appropriate sound (win, lose, jackpot)
    }
}
