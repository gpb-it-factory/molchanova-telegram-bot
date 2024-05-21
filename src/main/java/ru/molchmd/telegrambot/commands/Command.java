package ru.molchmd.telegrambot.commands;

public enum Command {
    START("/start", "стартовое сообщение", false),
    HELP("/help", "список доступных команд", true),
    PING("/ping", "отвечу pong", true);

    public final String name;
    public final String description;
    public final boolean isDisplayToMenu;

    Command(String name, String description, boolean isDisplayToMenu) {
        this.name = name;
        this.description = description;
        this.isDisplayToMenu = isDisplayToMenu;
    }
}
