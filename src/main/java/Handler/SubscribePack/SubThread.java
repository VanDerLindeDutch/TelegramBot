package Handler.SubscribePack;


import Bot.Bot;
import Handler.Location.ParseThread;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.util.concurrent.*;

public class SubThread implements Runnable {
    private final String chatID;
    private final Location location;
    private final Bot bot;
    private final Integer id_sub;

    public SubThread(String chatID, Location location, Bot bot, Integer id_sub) {
        this.chatID = chatID;
        this.location = location;
        this.bot = bot;
        this.id_sub = id_sub;
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
                new Thread(new InsertWeatherThread(id_sub, result)).start();
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
