package ru.home.weathbot_telegramBot.botapi;

/**Возможные состояния бота
 */

public enum BotState {
    WELCOME,
    NO_ANSWER,
    ASK_CITY,
    ASK_PERIOD,
    FILLING_PROFILE,
    PROFILE_FILLED,
    SHOW_USER_PROFILE,
    SHOW_MAIN_MENU,
    SHOW_HELP_MENU;
}
