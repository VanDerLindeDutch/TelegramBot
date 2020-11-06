package Handler.SubscribePack;

import Bot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ListenForCoordThread implements Runnable {
    private final String chatID;
    private final Bot bot;
    private boolean isActive = true;
    private final List<Thread> list;
    int sub_id;

    public ListenForCoordThread(String chatID, Bot bot, List<Thread> list) {
        this.chatID = chatID;
        this.bot = bot;
        this.list = list;
    }

    @Override
    public void run() {
        while (isActive) {
            for (Object object = bot.receiveQueue.poll(); object != null; object = bot.receiveQueue.poll()) {
                if (object instanceof Update) {
                    Update update = (Update) (object);
                    if (update.getMessage().getChatId().toString().equals(chatID)) {
                        if (!update.getMessage().hasLocation()) {
                            try {
                                bot.execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText("Send coordinates"));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ExecutorService executor;
                            executor = Executors.newFixedThreadPool(1);
                            Future<Integer> future, future1;
                            future = executor.submit(new InsertUserSubLocThread(chatID, update.getMessage().getLocation()));

                            try {
                                SubThread subThread = new SubThread(chatID, update.getMessage().getLocation(), bot, future.get());
                                Thread thread = new Thread(subThread, chatID);
                                thread.start();
                                list.add(thread);
                                isActive = false;
                            } catch (InterruptedException | ExecutionException interruptedException) {
                                interruptedException.printStackTrace();
                            }

                        }
                    }
                }
            }
        }
    }
}
