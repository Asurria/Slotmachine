package slotmachine;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.inventory.MenuType;
import javax.annotation.Nonnull;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.minecraft.world.item.ItemStack;
import java.util.Random;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Handles server-side logic for the PokéSlot Machine menu/container.
 */
public class PokeSlotMachineMenu extends AbstractContainerMenu {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(net.minecraft.core.registries.Registries.MENU, PokeSlotMod.MODID);
    public static final DeferredHolder<MenuType<?>, MenuType<PokeSlotMachineMenu>> POKESLOT_MENU =
        MENUS.register("pokeslot_machine_menu", PokeSlotMachineMenu::createMenuType);

    public static MenuType<PokeSlotMachineMenu> createMenuType() {
    return MenuType.create(PokeSlotMachineMenu::new);
    }

    private final ContainerLevelAccess access;

    private static final Random RANDOM = new Random();

    /**
     * Constructeur du menu.
     */
    public PokeSlotMachineMenu(int id, Inventory playerInventory) {
        super(POKESLOT_MENU.get(), id);
        this.access = ContainerLevelAccess.NULL;
    }

    public PokeSlotMachineMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory);
    }

    // public PokeSlotMachineMenu(int id, Inventory playerInventory, FriendlyByteBuf data) {
    //     super(POKESLOT_MENU.get(), id);
    //     this.access = ContainerLevelAccess.NULL;
    // }

    /**
     * Effectue un spin, retire un PokéCoin, génère les symboles, calcule le gain et retourne le résultat.
     * @param player Le joueur qui spin
     * @return Résultat du spin (indices des symboles, gain, message)
     */
    public SpinResult spinSlots(Player player) {
        // Vérifie que le joueur a un PokéCoin
        int coinSlot = findPokecoinSlot(player);
        if (coinSlot == -1) {
            return new SpinResult(new int[]{0,0,0}, 0, "Pas de PokéCoin !");
        }
        // Retire un PokéCoin
        player.getInventory().getItem(coinSlot).shrink(1);

        // Tire 3 symboles aléatoires
        int[] indices = new int[3];
        for (int i = 0; i < 3; i++) {
            indices[i] = RANDOM.nextInt(PokeSlotSymbols.values().length);
        }

        // Calcule le gain
        int gain = calculateGain(indices);
        String message = gain > 0 ? "Gagné : " + gain + " PokéCoins !" : "Perdu !";
        if (gain > 0) {
        player.getInventory().add(new ItemStack((net.minecraft.world.item.Item) PokeSlotItems.POKECOIN.get(), gain));
        }
        return new SpinResult(indices, gain, message);
    }

    /**
     * Cherche un PokéCoin dans l'inventaire du joueur.
     */
    private int findPokecoinSlot(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.getItem() == PokeSlotItems.POKECOIN.get()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Calcule le gain selon les symboles tirés.
     */
    private int calculateGain(int[] indices) {
        PokeSlotSymbols s1 = PokeSlotSymbols.values()[indices[0]];
        PokeSlotSymbols s2 = PokeSlotSymbols.values()[indices[1]];
        PokeSlotSymbols s3 = PokeSlotSymbols.values()[indices[2]];
        // Règle temporaire : 3 identiques = 5, 2 identiques = 1, sinon 0
        if (s1 == s2 && s2 == s3) {
            return 5;
        }
        if (s1 == s2 || s2 == s3 || s1 == s3) {
            return 1;
        }
        return 0;
    }

    /**
     * Structure de résultat de spin.
     */
    public static class SpinResult {
        public final int[] indices;
        public final int gain;
        public final String message;
        public SpinResult(int[] indices, int gain, String message) {
            this.indices = indices;
            this.gain = gain;
            this.message = message;
        }
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return true;
    }

    @Nonnull
    public net.minecraft.world.item.ItemStack quickMoveStack(@Nonnull Player player, int index) {
    return net.minecraft.world.item.ItemStack.EMPTY;
    }

    /**
     * Enregistre le menu sur l'event bus.
     */
    public static void register(IEventBus eventBus) {
        // No-op, registration client is handled via RegisterMenuScreensEvent
    }
}
