package org.example.plugin;

import PlayerJoinListener.PlayerJoinListener;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import hytale_vorifier_events.LocalVoteEvent;
import hytale_votiefier_command.LocalVoteCommand;

import javax.annotation.Nonnull;

/**
 * This class serves as the entrypoint for your plugin. Use the setup method to register into game registries or add
 * event listeners.
 */
public class ExamplePlugin extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public ExamplePlugin(@Nonnull JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Hello from " + this.getName() + " version " + this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {
        LOGGER.atInfo().log("Setting up plugin " + this.getName());
        //test command
        this.getCommandRegistry().registerCommand(
                new ExampleCommand(this.getName(), this.getManifest().getVersion().toString())
        );

        //register player join message
        this.getEventRegistry().register(PlayerConnectEvent.class, new PlayerJoinListener()::onPlayerJoin);

        LocalVoteEvent voteEvent = new LocalVoteEvent();
        this.getCommandRegistry().registerCommand(new LocalVoteCommand(voteEvent));

    }

}