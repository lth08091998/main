package seedu.address.model.person;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;

import java.util.HashMap;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;

/**
 * Represents a composite ID for objects without a field as primary key
 * but uses a tuple of IDs as a unique identifier instead such as (sid, aid) for Progress
 */


public class CompositeID extends ID {
    public final HashMap<Prefix, ID> ids;

    public CompositeID(HashMap<Prefix, ID> ids) {
        this.ids = ids;
    }

    public ID getStudentID() throws CommandException {
        if(!ids.containsKey(PREFIX_STUDENTID)) {
            throw new CommandException("ProgressID does not contain StudentID - is invalid ProgressID!");
        } else {
            return ids.get(PREFIX_STUDENTID);
        }
   }

    public ID getAssignmentID() throws CommandException {
        if(!ids.containsKey(PREFIX_ASSIGNMENTID)) {
            throw new CommandException("ProgressID does not contain AssignmentID - is invalid ProgressID!");
        } else {
            return ids.get(PREFIX_ASSIGNMENTID);
        }
    }

    @Override
    public String toString() {
        return ids.toString() ;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompositeID // instanceof handles nulls
                && ids.equals(((CompositeID) other).ids)); // state check
    }

    @Override
    public int hashCode() {
        return ids.hashCode();
    }
}
