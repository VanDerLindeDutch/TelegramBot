package Handler.SubscribePack;

import Bot.Bot;
import Command.ParsedCommand;
import Handler.AbstractHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


public class SubscribeHandler extends AbstractHandler {

    List<Thread> list;

    public SubscribeHandler(Bot bot, List<Thread> list) {
        super(bot);
        this.list = list;
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        try {
            bot.sendQueue.add(getSubscribeHandler(chatId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return "";
    }

    private SendMessage getSubscribeHandler(String chatID) throws TelegramApiException {
        boolean isAlreadyExist = false;

        for (Thread i : list) {
            if (i.getName().equals(chatID)) {
                isAlreadyExist = true;
                break;
            }
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.enableMarkdown(true);
        StringBuilder text = new StringBuilder();
        if (!isAlreadyExist) {
            text.append("Send me coordinates");
            new Thread(new ListenForCoordThread(chatID, bot, list)).start();
        } else {
            text.append("You have already subscribed");
        }
        sendMessage.setText(text.toString());
        return sendMessage;
    }
}
