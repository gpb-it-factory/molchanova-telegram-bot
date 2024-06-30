package ru.molchmd.minibank.frontend.telegrambot.commands;

public enum Command {
    START("/start", "стартовое сообщение", false),
    HELP("/help", "список доступных команд", true),
    PING("/ping", "отвечу pong", false),
    REGISTER("/register", "зарегистрироваться", true),
    CREATE_ACCOUNT("/createaccount", "создать счет", true),
    CURRENT_BALANCE("/currentbalance", "посмотреть счета", true),
    TRANSFER("/transfer", "перевести деньги", true);

    public final String name;
    public final String description;
    public final boolean isDisplayToMenu;

    Command(String name, String description, boolean isDisplayToMenu) {
        this.name = name;
        this.description = description;
        this.isDisplayToMenu = isDisplayToMenu;
    }
}
