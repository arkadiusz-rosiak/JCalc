package it.rosiak.jpaint.command;

public interface Command {
    void execute();

    void undo();

    void redo();
}
