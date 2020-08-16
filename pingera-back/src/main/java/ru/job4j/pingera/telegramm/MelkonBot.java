package ru.job4j.pingera.telegramm;

import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.job4j.pingera.clasez.Constant;

import javax.annotation.PostConstruct;

@Component
public class MelkonBot extends TelegramLongPollingBot {

    @Setter
    private long chatId = 0L;

    private Update upd = new Update();

    private TelegramBotsApi botsApi;

    static {
        ApiContextInitializer.init();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() ) {
            upd = update;
            chatId = update.getMessage().getChatId();
            this.send(update.getMessage().getChatId(), this.answer(update.getMessage().getText()));
        }
    }

    @Override
    public String getBotUsername() {
        return Constant.TELEGRAM_BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return Constant.TELEGRAM_BOT_KEY;
    }

    @PostConstruct
    public void initBot() {
        botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new MelkonBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void send(Long chatId, String text) {
        SendMessage mess = new SendMessage().setChatId(chatId).setText(text);
            try {
                execute(mess);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
    }

    public void send(String text) {
        if (chatId != 0L) {
            SendMessage mess = new SendMessage().setChatId(this.chatId).setText(text);
            try {
                execute(mess);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String answer(String q) {
        String msg;
        switch (q) {
            case Constant.TELEGRAM_START_WORD:
                send("This bot is only needed to display reports programm PINGER");
                msg = String.format("Id chat = %s", upd.getMessage().getChatId());
                break;
            case Constant.TELEGRAM_ID_WORD:
                msg = String.format("Id chat = %s", upd.getMessage().getChatId());
                break;
            case Constant.TELEGRAM_REPORT_WORD:
                msg = "Ok.. Wait report..";
                break;
            case Constant.TELEGRAM_HELP_WORD:
                msg = "Help.. Wait report..";
                break;
            default:
                msg = "Don't valid command";
                break;
        }
        return  msg;
    }
}
