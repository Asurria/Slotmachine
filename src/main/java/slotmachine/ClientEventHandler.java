package slotmachine;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class ClientEventHandler {
    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
    event.register((net.minecraft.world.inventory.MenuType<PokeSlotMachineMenu>) PokeSlotMachineMenu.POKESLOT_MENU.get(), (menu, inv, title) -> new PokeSlotMachineScreen(menu, inv, title));
    }
}