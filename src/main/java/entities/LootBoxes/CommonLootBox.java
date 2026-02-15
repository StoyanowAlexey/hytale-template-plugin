package entities.LootBoxes;


import com.dailoss.lootboxitems.LootReward;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;


import java.util.Random;


public class CommonLootBox implements LootBox{

    @Override
    public void givePlayerALootBox(Player player) {
        Inventory inventory = player.getInventory();
        ItemContainer storageContainer = inventory.getStorage();

        ItemStack [] arr = generateLootBox();
        for (ItemStack item : arr) {
            storageContainer.addItemStack(item);
        }
    }

    @Override
    public ItemStack[] generateLootBox() {
        LootReward[] rewards = new LootReward[]{
                new LootReward("Weapon_Axe_Adamantite", 1, 2, 100.0),
                new LootReward("Ore_Copper", 1, 2, 100.0),
                new LootReward("Food_Bread", 1, 2, 100.0)
        };
        ItemStack[] items = new ItemStack[rewards.length];
        Random rand = new Random();
        for (int i = 0; i < rewards.length; i++) {
            items[i] = rewards[i].generateItemStack(rand);
        }
        return items;
    }
}
