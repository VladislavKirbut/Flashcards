package flashcards.project.exception;

public class IncorrectCommand extends RuntimeException {
    public IncorrectCommand() {
        super("Incorrect command");
    }
}
