package Handler;

import Bot.Bot;
import Command.ParsedCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class UnsubscribeHandler extends AbstractHandler {
    List<Thread> list;


    public UnsubscribeHandler(Bot bot, List<Thread> list) {
        super(bot);
        this.list = list;
    }


    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        String string = null;
        for(Thread i:list){
            if(i.getName().equals(chatId)){
                string = i.getName();
                i.stop();
            }
        }
        if(string==null){
            string = " oh shiiit, nothing to unsubscribe";
        }
        else {
            string = "Unsubscribed";
        }
        bot.sendQueue.add(new SendMessage().setChatId(chatId).setText(string));
        return "";
    }
}
