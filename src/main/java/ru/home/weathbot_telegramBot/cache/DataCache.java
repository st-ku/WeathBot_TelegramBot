package ru.home.weathbot_telegramBot.cache;

import ru.home.weathbot_telegramBot.botapi.BotState;
import ru.home.weathbot_telegramBot.model.UserProfileData;


public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);

    UserProfileData getUserProfileData(int userId);

    void saveUserProfileData(int userId, UserProfileData userProfileData);
}
