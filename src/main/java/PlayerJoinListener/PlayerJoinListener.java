package PlayerJoinListener;

import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.util.ColorParseUtil;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.awt.Color;

public class PlayerJoinListener implements IEvent {

    // Метод, який буде викликаний при підключенні гравця
    public void onPlayerJoin(PlayerConnectEvent event) {
        PlayerRef player = event.getPlayerRef();

        if (player != null) {
            String gradientText = "<gradient:#ff0000:#00ff00>Ласкаво просимо на сервер!</gradient>";
            // Градієнтне повідомлення
            player.sendMessage(Message.raw("Ласкаво просимо на сервер!").color(Color.MAGENTA));

            // Окреме повідомлення зі стандартним кольором
            player.sendMessage(Message.raw("Щоб отримати лутбокс, введіть /vote!").color(Color.ORANGE));
        }
    }
}