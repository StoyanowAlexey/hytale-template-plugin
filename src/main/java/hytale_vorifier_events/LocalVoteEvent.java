package hytale_vorifier_events;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import entities.LootBoxes.CommonLootBox;
import entities.VotedPlayer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class LocalVoteEvent {

    private final Map<String, VotedPlayer> votedPlayers = new HashMap<>();

    // Використовуємо .dat, щоб ядро Hytale не намагалося парсити файл самостійно
    private final File file = new File("plugins/MyVotePlugin/players_voted.dat");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public LocalVoteEvent() {
        // Викликаємо створення файлу перед завантаженням
        createFileIfNotExists();
        loadVotedPlayers();
    }

    // Той самий метод для створення файлу, про який ти питав
    private void createFileIfNotExists() {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs(); // Створюємо папку plugins/MyVotePlugin
            }

            if (!file.exists()) {
                if (file.createNewFile()) {
                    // Одразу записуємо порожню Map, щоб файл містив {}
                    saveVotedPlayers();
                    System.out.println("[MyVotePlugin] Файл успішно створено!");
                }
            }
        } catch (IOException e) {
            System.err.println("[MyVotePlugin] Помилка створення файлу: " + e.getMessage());
        }
    }

    public void onVoteCommand(Player player) {
        String playerName = player.getDisplayName();

        VotedPlayer votedPlayer = votedPlayers.computeIfAbsent(
                playerName,
                VotedPlayer::new
        );

        if (votedPlayer.isVotedToday()) {
            player.sendMessage(Message.raw("Ви вже отримали нагороду за голос!"));
            return;
        }

        votedPlayer.setVoted();
        player.sendMessage(Message.raw("Дякуємо за голос! Ви отримали нагороду!"));
        CommonLootBox commonLootBox = new CommonLootBox();
        commonLootBox.givePlayerALootBox(player);
        saveVotedPlayers();
    }

    private void saveVotedPlayers() {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            GSON.toJson(votedPlayers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadVotedPlayers() {
        try {
            if (!file.exists() || file.length() < 2) return;

            // Читаємо як текст і обрізаємо пробіли для захисту від JsonParseException
            byte[] bytes = Files.readAllBytes(file.toPath());
            String content = new String(bytes, StandardCharsets.UTF_8).trim();

            if (content.isEmpty() || !content.startsWith("{")) return;

            Map<String, VotedPlayer> map = GSON.fromJson(content, new TypeToken<Map<String, VotedPlayer>>(){}.getType());
            if (map != null) votedPlayers.putAll(map);
        } catch (Exception e) {
            System.err.println("[MyVotePlugin] Помилка завантаження JSON: " + e.getMessage());
        }
    }
}