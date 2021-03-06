package ru.home.weathbot_telegramBot.botapi.handlers.fillingprofile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.weathbot_telegramBot.botapi.BotState;
import ru.home.weathbot_telegramBot.botapi.InputMessageHandler;
import ru.home.weathbot_telegramBot.cache.UserDataCache;
import ru.home.weathbot_telegramBot.model.UserProfileData;
import ru.home.weathbot_telegramBot.service.MainMenuService;
import ru.home.weathbot_telegramBot.service.WeatherService;
import ru.home.weathbot_telegramBot.service.ReplyMessagesService;
import ru.home.weathbot_telegramBot.service.UsersProfileDataService;


@Slf4j
@Component
public class FillingProfileHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;
    private WeatherService weatherService;
    private UsersProfileDataService profileDataService;
    private MainMenuService mainMenuService;

    public FillingProfileHandler(UserDataCache userDataCache, ReplyMessagesService messagesService, WeatherService weatherService, UsersProfileDataService profileDataService, MainMenuService mainMenuService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.weatherService = weatherService;
        this.profileDataService = profileDataService;
        this.mainMenuService = mainMenuService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_CITY);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();
        String forecastMessage="";

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;


        if (botState.equals(BotState.ASK_CITY)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askCity");
            userDataCache.setUsersCurrentBotState(userId, BotState.CHECK_CITY);
            userDataCache.saveUserProfileData(userId, profileData);

        }

        if (botState.equals(BotState.CHECK_CITY)) {
            profileData.setCity(usersAnswer);
            userDataCache.setUsersCurrentBotState(userId, BotState.PROFILE_FILLED);
            botState=BotState.PROFILE_FILLED;
        }


        if (botState.equals(BotState.PROFILE_FILLED)) {
            profileData.setChatId(chatId);
            profileData.setBotState(BotState.PROFILE_FILLED);
            profileDataService.saveUserProfileData(profileData);
            userDataCache.setUsersCurrentBotState(userId, BotState.PROFILE_FILLED);
            try {
                forecastMessage = weatherService.getWeather(userDataCache.getUserProfileData(userId).getCity());
            } catch (Exception e) {
                replyToUser = messagesService.getReplyMessage(chatId, "reply.cityNotFound");
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_CITY);
                userDataCache.saveUserProfileData(userId, profileData);
                return replyToUser;
            }

            replyToUser = new SendMessage(chatId, String.format("%n%s ", forecastMessage));
            replyToUser=mainMenuService.getMainMenuMessage(replyToUser);
            replyToUser.setParseMode("HTML");

        }

        userDataCache.saveUserProfileData(userId, profileData);

        return replyToUser;
    }



}



