package ru.home.weathbot_telegramBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuService {
    private LocaleMessageService localeMessageService;

    public MainMenuService(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }


    private ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(localeMessageService.getMessage("button.update")));
        row1.add(new KeyboardButton(localeMessageService.getMessage("button.changeCity")));
        keyboard.add(row1);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public SendMessage getMainMenuMessage(SendMessage sendMessage) {

        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(getMainMenuKeyboard());

        return sendMessage;
    }
}
