package ru.home.weathbot_telegramBot.cache;

import ru.home.weathbot_telegramBot.botapi.BotState;
import ru.home.weathbot_telegramBot.model.UserProfileData;


public interface DataCache {
    void setUsersCurrentBotState(long userId, BotState botState);

    BotState getUsersCurrentBotState(long userId);

    UserProfileData getUserProfileData(long userId);

    void saveUserProfileData(long userId, UserProfileData userProfileData);
}
