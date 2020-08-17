package ru.home.weathbot_telegramBot.cache;

import org.springframework.stereotype.Component;
import ru.home.weathbot_telegramBot.botapi.BotState;
import ru.home.weathbot_telegramBot.model.UserProfileData;
import ru.home.weathbot_telegramBot.service.UsersProfileDataService;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory cache.
 * usersBotStates: user_id and user's bot state
 * usersProfileData: user_id  and user's profile data.
 */

@Component
public class UserDataCache implements DataCache {
    private UsersProfileDataService usersProfileDataService;
    private Map<Long, BotState> usersBotStates = new HashMap<>();
    private Map<Long, UserProfileData> usersProfileData = new HashMap<>();

    public UserDataCache(UsersProfileDataService usersProfileDataService) {
        this.usersProfileDataService = usersProfileDataService;
    }

    @Override
    public void setUsersCurrentBotState(long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(long userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = getUsersCurrentBotStateFromDb(userId);
        }

        return botState;
    }

    public BotState getUsersCurrentBotStateFromDb(long userId) {
        UserProfileData userProfileData = getUserProfileData(userId);
        if (userProfileData.getBotState() == null) {
            return  BotState.WELCOME;
        }
        return userProfileData.getBotState();
    }

    @Override
    public UserProfileData getUserProfileData(long userId) {
        UserProfileData userProfileData = usersProfileData.get(userId);
        if (userProfileData == null) {
            userProfileData=getUserProfileDataFromDb(userId);
        }
        return userProfileData;
    }
    public UserProfileData getUserProfileDataFromDb(long chatId) {
        UserProfileData userProfileData = usersProfileDataService.getUserProfileData(chatId);
        if (userProfileData == null) {
            userProfileData = new UserProfileData();
        }
        return userProfileData;
    }

    @Override
    public void saveUserProfileData(long userId, UserProfileData userProfileData) {
        usersProfileData.put(userId, userProfileData);
    }

}
