package Handler.Unsubscribe;

import Bot.Bot;
import Command.ParsedCommand;
import DB.SubscribeEntity;
import Handler.AbstractHandler;
import antlr.debug.TraceAdapter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Iterator;
import java.util.List;

import static Service.MessageReciever.GetSession;

public class UnsubscribeHandler extends AbstractHandler {
    List<Thread> list;


    public UnsubscribeHandler(Bot bot, List<Thread> list) {
        super(bot);
        this.list = list;
    }


    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        String string = null;
        Iterator<Thread> i = list.iterator();
        while (i.hasNext()){
            Thread el = i.next();
            if(el.getName().equals(chatId)){
                Session session = GetSession();
                Transaction transaction = session.beginTransaction();
                String HQL = "UPDATE SubscribeEntity SET isExecute = true where user.ID_chat = :param and isExecute = FALSE";
                Query query = session.createQuery(HQL);
                query.setParameter("param",chatId);
                Integer integer = query.executeUpdate();
                transaction.commit();
                session.close();
                string = el.getName();
                el.stop();
                i.remove();
            }
        }
        if(string==null){
            string = "oh shiiit, nothing to unsubscribe";
        }
        else {
            string = "Unsubscribed";
        }
        bot.sendQueue.add(new SendMessage().setChatId(chatId).setText(string));
        return "";
    }
}
