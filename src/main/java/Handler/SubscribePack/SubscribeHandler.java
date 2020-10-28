package Handler.SubscribePack;

import Bot.Bot;
import Command.Command;
import Command.ParsedCommand;
import Handler.AbstractHandler;
import Handler.Location.ParseThread;
import kotlin.Pair;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


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
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.enableMarkdown(true);
        StringBuilder text = new StringBuilder();
        text.append("Send me coordinates");
        sendMessage.setText(text.toString());
        new Thread(new ListenForCoordThread(chatID, bot, list)).start();

        return sendMessage;
    }
}
