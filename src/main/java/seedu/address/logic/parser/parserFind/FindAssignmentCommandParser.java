package seedu.address.logic.parser.parserFind;

import seedu.address.logic.commands.commandFind.FindAssignmentCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelAssignment.AssignmentNameContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindAssignmentCommandParser implements Parser<FindAssignmentCommand> {

  /**
   * Parses the given {@code String} of arguments in the context of the FindCommand and returns a
   * FindCommand object for execution.
   *
   * @throws ParseException if the user input does not conform the expected format
   */
  public FindAssignmentCommand parse(String args) throws ParseException {
    String trimmedArgs = args.trim();
    if (trimmedArgs.isEmpty()) {
      throw new ParseException(
          String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAssignmentCommand.MESSAGE_USAGE));
    }

    String[] nameKeywords = trimmedArgs.split("\\s+");

    return new FindAssignmentCommand(
        new AssignmentNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
  }

}
