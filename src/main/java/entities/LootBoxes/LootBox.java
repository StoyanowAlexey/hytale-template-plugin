package entities.LootBoxes;


import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;


public interface LootBox {

    void givePlayerALootBox(Player player);

    ItemStack[] generateLootBox();
}
