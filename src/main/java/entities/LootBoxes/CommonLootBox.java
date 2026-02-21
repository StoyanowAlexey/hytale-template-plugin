package entities.LootBoxes;


import com.hypixel.hytale.server.core.universe.PlayerRef;
import fr.gunter423.plugin.lootchest.LootChestBalance;
import fr.gunter423.plugin.lootchest.LootChestBalanceManager;
import fr.gunter423.plugin.lootchest.Rarity;


public class CommonLootBox implements LootBox{

    @Override
    public void givePlayerALootBox(PlayerRef player) {
        LootChestBalance balance = LootChestBalanceManager.getBalance(player.getUuid());
        balance.setUsername(player.getUsername());
        balance.addChests(Rarity.COMMON, 1);
        LootChestBalanceManager.save(player.getUuid());

    }

}
