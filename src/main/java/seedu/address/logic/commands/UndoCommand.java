package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success: [%s]";
    public static final String MESSAGE_FAILURE = "Sorry! There's no Undoable Command!";

    @Override
    public final CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!undoRedoStack.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        CommandResult commandResult = undoRedoStack.popUndo().undo(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, commandResult.getFeedbackToUser()));
    }

}
