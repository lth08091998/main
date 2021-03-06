package seedu.address.logic.parser.parserEdit;

import seedu.address.logic.commands.commandEdit.EditFinanceCommand;
import seedu.address.logic.commands.commandEdit.EditFinanceCommand.EditFinanceDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ID;
import seedu.address.model.tag.Tag;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditFinanceCommandParser implements Parser<EditFinanceCommand> {

  /**
   * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
   * EditCommand object for execution.
   *
   * @throws ParseException if the user input does not conform the expected format
   */
  public EditFinanceCommand parse(String args) throws ParseException {
    requireNonNull(args);
    ArgumentMultimap argMultimap =
        ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_AMOUNT, PREFIX_TAG);

    ID id;

    try {
      id = ParserUtil.parseID(argMultimap.getPreamble());
    } catch (ParseException pe) {
      throw new ParseException(
          String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditFinanceCommand.MESSAGE_USAGE), pe);
    }

    EditFinanceDescriptor editFinanceDescriptor = new EditFinanceDescriptor();
    if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
      editFinanceDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
    }
    if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
      editFinanceDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
    }
    if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
      editFinanceDescriptor
          .setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
    }

    parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG))
        .ifPresent(editFinanceDescriptor::setTags);

    if (!editFinanceDescriptor.isAnyFieldEdited()) {
      throw new ParseException(EditFinanceCommand.MESSAGE_NOT_EDITED);
    }

    return new EditFinanceCommand(id, editFinanceDescriptor);
  }

  /**
   * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty. If
   * {@code tags} contain only one element which is an empty string, it will be parsed into a {@code
   * Set<Tag>} containing zero tags.
   */
  private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
    assert tags != null;

    if (tags.isEmpty()) {
      return Optional.empty();
    }
    Collection<String> tagSet =
        tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
    return Optional.of(ParserUtil.parseTags(tagSet));
  }

}
