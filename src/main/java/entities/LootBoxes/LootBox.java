package entities.LootBoxes;


import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.PlayerRef;


public interface LootBox {

    void givePlayerALootBox(PlayerRef player);

}
