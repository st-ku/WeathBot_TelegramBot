package ru.home.weathbot_telegramBot.botapi.handlers.weather;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.weathbot_telegramBot.botapi.BotState;
import ru.home.weathbot_telegramBot.botapi.InputMessageHandler;
import ru.home.weathbot_telegramBot.cache.UserDataCache;
import ru.home.weathbot_telegramBot.service.ReplyMessagesService;

@Slf4j
@Component
public class AskWeatherHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;

    public AskWeatherHandler(UserDataCache userDataCache, ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WELCOME;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getChatId();
        int userId = inputMsg.getFrom().getId();
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.hello");
        userDataCache.setUsersCurrentBotState(userId, BotState.ASK_CITY);
        return replyToUser;
    }




}



