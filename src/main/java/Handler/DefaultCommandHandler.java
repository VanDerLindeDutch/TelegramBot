package Handler;

import Bot.Bot;
import Command.ParsedCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.logging.Logger;

public class DefaultCommandHandler extends AbstractHandler {
    private static final Logger log = Logger.getLogger(DefaultCommandHandler.class.getName());

    public DefaultCommandHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        bot.sendQueue.add(getDefaultComHandler(chatId));
        return "";
    }

    private SendMessage getDefaultComHandler(String chatID) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.enableMarkdown(true);
        StringBuilder text = new StringBuilder();
        text.append("Sorry, don't know this command").append(END_LINE);
        sendMessage.setText(text.toString());
        return sendMessage;
    }


}