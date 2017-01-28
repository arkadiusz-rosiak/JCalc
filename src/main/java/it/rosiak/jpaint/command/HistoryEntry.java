package it.rosiak.jpaint.command;

import java.time.LocalDateTime;

public class HistoryEntry {

    private final Command command;
    private final LocalDateTime time = LocalDateTime.now();
    private boolean isUndone = false;

    public HistoryEntry(Command cmd) {
        this.command = cmd;
    }

    public Command getCommand() {
        return command;
    }

    public boolean isUndone() {
        return isUndone;
    }

    void setUndone(boolean undone){
        this.isUndone = undone;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time + ": " + command.toString();
    }
}
