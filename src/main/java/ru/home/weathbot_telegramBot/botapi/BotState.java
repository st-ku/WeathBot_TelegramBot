package ru.home.weathbot_telegramBot.botapi;

/**Возможные состояния бота
 */

public enum BotState {
    WELCOME,
    ASK_CITY,
    CHECK_CITY,
    FILLING_PROFILE,
    PROFILE_FILLED,
}
