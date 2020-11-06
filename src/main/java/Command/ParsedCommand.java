package Command;


public class ParsedCommand {

    private String text;
    private Command command ;

    public ParsedCommand(String text, Command command) {
        this.text = text;
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
