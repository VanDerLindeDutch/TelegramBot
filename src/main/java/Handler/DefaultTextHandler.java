package Handler;

import Bot.Bot;
import Command.ParsedCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DefaultTextHandler extends AbstractHandler {

    public DefaultTextHandler(Bot bot) {
        super(bot);
    }


    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        bot.sendQueue.add(getDefaultTxtHandler(chatId, update));
        return "";
    }

    private SendMessage getDefaultTxtHandler(String chatID, Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        StringBuilder string = new StringBuilder();
        string.append(update.getMessage().getText()).append("-What is it?\n");
        string.append("I can't keep up the conversation. I'm not so smart.");
        sendMessage.setText(string.toString());
        return sendMessage;
    }
}
