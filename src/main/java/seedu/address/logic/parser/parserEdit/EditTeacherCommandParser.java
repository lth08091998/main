package seedu.address.logic.parser.parserEdit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.commandEdit.EditTeacherCommand;
import seedu.address.logic.commands.commandEdit.EditTeacherCommand.EditTeacherDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ID;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditTeacherCommandParser implements Parser<EditTeacherCommand> {

  /**
   * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
   * EditCommand object for execution.
   *
   * @throws ParseException if the user input does not conform the expected format
   */
  public EditTeacherCommand parse(String args) throws ParseException {
    requireNonNull(args);
    ArgumentMultimap argMultimap =
        ArgumentTokenizer
            .tokenize(args, PREFIX_NAME, PREFIX_TEACHERID, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_SALARY, PREFIX_ADDRESS,
                PREFIX_TAG);

    ID id;

    try {
      id = ParserUtil.parseID(argMultimap.getPreamble());
    } catch (ParseException pe) {
      throw new ParseException(
          String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTeacherCommand.MESSAGE_USAGE), pe);
    }

    EditTeacherDescriptor editTeacherDescriptor = new EditTeacherDescriptor();
    if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
      editTeacherDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
    }
    if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
      editTeacherDescriptor
          .setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
    }
    if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
      editTeacherDescriptor
          .setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
    }
    if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
      editTeacherDescriptor
          .setSalary(ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get()));
    }
    if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
      editTeacherDescriptor
          .setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
    }
    parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG))
        .ifPresent(editTeacherDescriptor::setTags);

    if (!editTeacherDescriptor.isAnyFieldEdited()) {
      throw new ParseException(EditTeacherCommand.MESSAGE_NOT_EDITED);
    }

    return new EditTeacherCommand(id, editTeacherDescriptor);
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
