package hytale_vorifier_events;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import entities.LootBoxes.CommonLootBox;
import entities.VotedPlayer;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class LocalVoteEvent {

    private final Map<String, VotedPlayer> votedPlayers = new HashMap<>();

    private final File file = new File("plugins/MyVotePlugin/players_voted.dat");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public LocalVoteEvent() {
        createFileIfNotExists();
        loadVotedPlayers();
    }

    private void createFileIfNotExists() {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            if (!file.exists()) {
                if (file.createNewFile()) {

                    saveVotedPlayers();
                    System.out.println("[MyVotePlugin] Файл успішно створено!");
                }
            }
        } catch (IOException e) {
            System.err.println("[MyVotePlugin] Помилка створення файлу: " + e.getMessage());
        }
    }

    public void onVoteCommand(PlayerRef player) {
        String playerName = player.getUsername();

        VotedPlayer votedPlayer = votedPlayers.computeIfAbsent(
                playerName,
                VotedPlayer::new
        );

        if (votedPlayer.isVotedToday()) {
            player.sendMessage(Message.raw("Ви вже отримали нагороду за голос!").color(Color.RED));
            return;
        }

        votedPlayer.setVoted();
        player.sendMessage(Message.raw("Дякуємо за голос! Ви отримали нагороду!").color(Color.GREEN));
        player.sendMessage(Message.raw("Command for check all your lootboxes -   /lb").color(Color.CYAN));
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