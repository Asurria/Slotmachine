package slotmachine;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.bus.api.IEventBus;

/**
 * The PokéSlot Machine block. Handles right-click to open GUI.
 */
public class PokeSlotMachineBlock extends Block {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PokeSlotMod.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PokeSlotMod.MODID);

    public static final DeferredBlock<Block> POKESLOT_MACHINE = BLOCKS.registerSimpleBlock("pokeslot_machine",
            BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> POKESLOT_MACHINE_ITEM = ITEMS.registerSimpleBlockItem("pokeslot_machine", POKESLOT_MACHINE);

    public PokeSlotMachineBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            player.openMenu(new net.minecraft.world.MenuProvider() {
                @Override
                public net.minecraft.network.chat.Component getDisplayName() {
                    return net.minecraft.network.chat.Component.literal("PokéSlot Machine");
                }
                @Override
                public net.minecraft.world.inventory.AbstractContainerMenu createMenu(int windowId, net.minecraft.world.entity.player.Inventory inv, Player p) {
                    return new slotmachine.PokeSlotMachineMenu(windowId, inv);
                }
            }, pos);
        }
        return InteractionResult.SUCCESS;
    }

    /**
     * Register block and item to the event bus.
     */
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
