package Handler;

import Bot.Bot;
import Command.ParsedCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractHandler {
    protected final String END_LINE = "\n";
    protected Bot bot;

    protected AbstractHandler(Bot bot) {
        this.bot = bot;
    }

    public abstract String operate(String chatId, ParsedCommand parsedCommand, Update update);

}
