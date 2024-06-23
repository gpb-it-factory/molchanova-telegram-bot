package ru.molchmd.minibank.frontend.telegrambot.util;

public class TextResponse {
    private TextResponse() {
    }

    public static String userIsNotRegistered() {
        return "_Ошибка!_ Вы не зарегистрированы!\nЧтобы зарегистрироваться, введите /register.";
    }

    public static String somethingWentWrong() {
        return "_Ошибка!_ Что-то пошло не так.";
    }

    public static String serverIsNotAvailable() {
        return "Сервер недоступен!\nПопробуйте позже.";
    }
}
