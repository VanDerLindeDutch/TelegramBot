package Service;

import Bot.Bot;
import Command.*;
import Handler.*;
import Handler.Location.LocationHandler;
import Handler.SubscribePack.SubThread;
import Handler.SubscribePack.SubscribeHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageReciever implements Runnable {
    private static final Logger log = Logger.getLogger(MessageReciever.class.getName());
    private final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;
    private final Bot bot;
    private final Parser parser;
    private final List<Thread> list = new ArrayList<>();

    public MessageReciever(Bot bot) {
        this.bot = bot;
        parser = new Parser(bot.getBotUsername());
    }

    @Override
    public void run() {
        log.info("[STARTED] MsgReciever.  Bot class: " + bot);
        while (true) {
            for (Object object = bot.receiveQueue.poll(); object != null; object = bot.receiveQueue.poll()) {
                log.info("New object for analyze in queue " + object.toString());
                analyze(object);
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                log.log(Level.INFO, "Catch interrupt. Exit", e);
                return;
            }
        }
    }

    private void analyze(Object object) {
        if (object instanceof Update) {
            Update update = (Update) object;
            log.info("Update recieved: " + update.toString());
            analyzeForUpdateType(update);
        } else log.info("Cant operate type of object: " + object.toString());
    }

    private void analyzeForUpdateType(Update update) {
        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();


        ParsedCommand parsedCommand = parser.getParsedCommand(inputText);
        System.out.println(parsedCommand.getCommand());
        AbstractHandler handlerForCommand;
        if (update.getMessage().hasLocation()) {
            handlerForCommand = getHandlerForCommand(Command.SENDLOCATION);
        } else {
            handlerForCommand = getHandlerForCommand(parsedCommand.getCommand());
        }
        String operationResult = handlerForCommand.operate(chatId.toString(), parsedCommand, update);

        if (!"".equals(operationResult)) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(operationResult);
            bot.sendQueue.add(message);
        }
    }

    private AbstractHandler getHandlerForCommand(Command command) {
        if (command == null) {
            log.info("Null command accepted. This is not good scenario.");
            return new DefaultCommandHandler(bot);
        }
        switch (command) {
            case START:
                return new SystemHandler(bot);
            case SENDLOCATION:
                return new LocationHandler(bot);
            case NOTFORME:
                return new DefaultTextHandler(bot);
            case SUBSCRIBE:
                return new SubscribeHandler(bot, list);
            case UNSUBSCRIBE:
                return new UnsubscribeHandler(bot, list);
            default:
                log.info("Handler for command[" + command.toString() + "] not Set. Return DefaultHandler");
                return new DefaultCommandHandler(bot);
        }
    }
}
