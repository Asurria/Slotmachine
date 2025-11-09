package slotmachine;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.bus.api.IEventBus;

/**
 * Main mod initializer for PokeSlot.
 * Handles registration and event bus setup.
 */
@Mod("slotsmachine")
public class PokeSlotMod {
    public static final String MODID = "slotsmachine";

    public PokeSlotMod(IEventBus modEventBus) {
        // Register items, blocks, etc.
        PokeSlotItems.register(modEventBus);
        PokeSlotMachineBlock.register(modEventBus);

        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
    }

    /**
     * Common setup event.
     */
    private void onCommonSetup(final FMLCommonSetupEvent event) {
        // Setup logic (networking, etc.)
    }

    /**
     * Client setup event.
     */
    private void onClientSetup(final FMLClientSetupEvent event) {
        // Registration du screen via RegisterMenuScreensEvent (voir ClientEventHandler)
    }
}
