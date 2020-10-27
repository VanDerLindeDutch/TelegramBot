package Handler.Location;

import Bot.Bot;
import Command.ParsedCommand;


import Handler.AbstractHandler;
import net.aksingh.owmjapis.model.CurrentWeather;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.*;

public class LocationHandler extends AbstractHandler {
    private static final char END_LINE = '\n';

    public LocationHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        try {
            bot.sendQueue.add(getLocationMessage(chatId, update));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    private SendMessage getLocationMessage(String chatID, Update update) throws ExecutionException, InterruptedException {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.enableMarkdown(true);
        StringBuilder text = new StringBuilder();
        text.append("Weather : ");
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<String> callable = new ParseThread(update.getMessage().getLocation().getLongitude(), update.getMessage().getLocation().getLatitude());
        Future<String> future = executor.submit(callable);
        String result = future.get();
        text.append(result);
        executor.shutdown();
        sendMessage.setText(text.toString());

        return sendMessage;
    }
}
