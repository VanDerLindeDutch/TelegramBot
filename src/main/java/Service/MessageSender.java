package Service;


import org.telegram.telegrambots.meta.api.methods.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.logging.Level;
import java.util.logging.Logger;
import Bot.*;
public class MessageSender implements Runnable {
    private static final Logger log = Logger.getLogger(MessageSender.class.getName());
    private final int SENDER_SLEEP_TIME = 1000;
    private final Bot bot;

    public MessageSender(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        log.info("[STARTED] MsgSender.  App.Bot class: " + bot);
        try {
            while (true) {
                if(!bot.sendQueue.isEmpty()){
                    Object object = bot.sendQueue.poll();
                    log.info("Get new msg to send " + object);
                    send(object);
                }
                try {
                    Thread.sleep(SENDER_SLEEP_TIME);
                } catch (InterruptedException e) {
                    log.log(Level.INFO, "Take interrupt while operate msg list", e);
                }
            }
        } catch (Exception e) {
            log.log(Level.INFO, e.getMessage(), e);
        }
    }

    private void send(Object object) {
        try {
            MessageType messageType = messageType(object);
            switch (messageType) {
                case EXECUTE -> {
                    BotApiMethod<Message> message = (BotApiMethod<Message>) object;
                    log.info("Use Execute for " + object);
                    bot.execute(message);
                }
                default -> log.info("Cant detect type of object. " + object);
            }
        } catch (Exception e) {
            log.log(Level.INFO, e.getMessage(), e);
        }
    }

    private MessageType messageType(Object object) {
        if (object instanceof BotApiMethod) return MessageType.EXECUTE;
        return MessageType.NOT_DETECTED;
    }

    enum MessageType {
        EXECUTE, NOT_DETECTED
    }
}