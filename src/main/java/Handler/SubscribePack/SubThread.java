package Handler.SubscribePack;


import Bot.Bot;
import Handler.Location.ParseThread;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.*;

public class SubThread implements Runnable {
    private final String chatID;
    private final Location location;
    private final Bot bot;

    public SubThread(String chatID, Location location, Bot bot) {
        this.chatID = chatID;
        this.location = location;
        this.bot = bot;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            ExecutorService executor = Executors.newFixedThreadPool(1);
            Callable<String> callable = new ParseThread(location.getLongitude(), location.getLatitude());
            Future<String> future = executor.submit(callable);
            String result = null;
            try {
                result = future.get() + "\nfrom Subscribe\n";
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            if (result != null) {
                bot.sendQueue.add(new SendMessage().setChatId(chatID).setText(result));
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
