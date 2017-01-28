package it.rosiak.jpaint.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CommandStack {

    private final Deque<HistoryEntry> doneCommands = new ArrayDeque<>();
    private final Deque<HistoryEntry> undoneCommands = new ArrayDeque<>();

    int getDoneCommandsCount() {
        return doneCommands.size();
    }

    public Command getLastDoneCommand() {
        return doneCommands.peekFirst().getCommand();
    }

    public void addCommandToExecute(Command cmd) {
        cmd.execute();
        doneCommands.addLast(new HistoryEntry(cmd));
        undoneCommands.clear();
    }

    public void undo() {
        if (!doneCommands.isEmpty()) {
            HistoryEntry he = doneCommands.pollLast();
            he.getCommand().undo();
            he.setUndone(true);
            undoneCommands.addFirst(he);
        }
    }

    public void redo() {
        if (!undoneCommands.isEmpty()) {
            HistoryEntry he = undoneCommands.pollFirst();
            he.getCommand().redo();
            he.setUndone(false);
            doneCommands.addLast(he);
        }
    }

    public List<HistoryEntry> getHistory(){
        List<HistoryEntry> history = new ArrayList<>(doneCommands.size() + undoneCommands.size());
        history.addAll(doneCommands);
        history.addAll(undoneCommands);
        return history;
    }

}
