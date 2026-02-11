package hytale_votiefier_command;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import hytale_vorifier_events.LocalVoteEvent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class LocalVoteCommand extends CommandBase {
    private final LocalVoteEvent localVote;

    // Передаємо івент сюди, а не створюємо новий
    public LocalVoteCommand(LocalVoteEvent localVote) {
        super("vote", "Vote for getting a reward");
        this.localVote = localVote;
    }

    @Override
    protected void executeSync(@NonNullDecl CommandContext commandContext) {
        Player player = (Player) commandContext.senderAs(Player.class);

        // Якщо команду ввела консоль, player буде null
        if (player == null) {
            commandContext.sendMessage(Message.parse("§cЦю команду може вводити тільки гравець!"));
            return;
        }

        localVote.onVoteCommand(player);
    }
}
