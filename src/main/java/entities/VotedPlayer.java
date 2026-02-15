package entities;

import java.time.Instant;

public class VotedPlayer {
    private String playerName;
    private boolean isVotedToday;
    private long lastVoteTime;

    public VotedPlayer(String playerName) {
        this.playerName = playerName;
        this.isVotedToday = false;
        this.lastVoteTime = 0;
    }

    public boolean isVotedToday() {
        if (lastVoteTime == 0) return false;
        long now = Instant.now().toEpochMilli();
        if (now - lastVoteTime >= 24 * 60 * 60 * 1000) {
            isVotedToday = false;
        }
        return isVotedToday;
    }

    public void setVoted() {
        this.isVotedToday = true;
        this.lastVoteTime = Instant.now().toEpochMilli();
    }
}