import Bot.Bot;
import Service.MessageReciever;
import Service.MessageSender;
import org.telegram.telegrambots.ApiContextInitializer;

import java.util.logging.Logger;

public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());
    private static final int PRIORITY_FOR_RECEIVER = 1;
    private static final int PRIORITY_FOR_SENDER = 3;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot weather_bot = new Bot("bitummitum_bot", "1172924268:AAEZGTU69qi09otwTcp3YIFwa8bK7pnoX7o");
        weather_bot.botConnect();
        MessageReciever messageReciever = new MessageReciever(weather_bot);
        MessageSender messageSender = new MessageSender(weather_bot);

        Thread receiver = new Thread(messageReciever);
        receiver.setDaemon(true);
        receiver.setName("MsgReciever");
        receiver.setPriority(PRIORITY_FOR_RECEIVER);
        receiver.start();

        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.setPriority(PRIORITY_FOR_SENDER);
        sender.start();


    }
}