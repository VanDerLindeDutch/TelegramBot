package Handler.SubscribePack;

import Bot.Bot;
import org.glassfish.jersey.message.internal.StringHeaderProvider;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.tools.DocumentationTool;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;

public class ListenForCoordThread implements Runnable {
    private final String chatID;
    private final Bot bot;
    private boolean isActive = true;
    List<Thread> list;

    public ListenForCoordThread(String chatID, Bot bot, List<Thread> list) {
        this.chatID = chatID;
        this.bot = bot;
        this.list = list;
    }

    @Override
    public void run() {
        while (isActive) {
            for (Object object = bot.receiveQueue.poll(); object != null; object = bot.receiveQueue.poll()) {
                if(object instanceof Update){
                    Update update = (Update)(object);
                    if(update.getMessage().getChatId().toString().equals(chatID)){
                        if(!update.getMessage().hasLocation()){
                            try {
                                bot.execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText("Send coordinates"));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            SubThread subThread = new SubThread(chatID, update.getMessage().getLocation(), bot);
                            Thread thread= new Thread(subThread, chatID);
                            thread.start();
                            list.add(thread);
                            isActive = false;
                        }
                    }
                }
            }

        }
    }
}
