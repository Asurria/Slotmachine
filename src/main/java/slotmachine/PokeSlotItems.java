package slotmachine;

import net.neoforged.neoforge.registries.DeferredItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.bus.api.IEventBus;

/**
 * Registers custom items for PokeSlot mod.
 */
public class PokeSlotItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PokeSlotMod.MODID);

    /**
     * PokéCoin item utilisé pour la machine à sous.
     */
    public static final DeferredItem<Item> POKECOIN = ITEMS.registerSimpleItem("pokecoin",
        new Item.Properties().stacksTo(64)); // Ajout à la tab via l'événement BuildCreativeModeTabContentsEvent

    /**
     * Enregistre les items sur l'event bus.
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
