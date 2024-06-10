package ru.molchmd.minibank.frontend.factory;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateFactory {
    public static Update createUpdate() {
        Update update = new Update();

        Chat chat = new Chat();
        chat.setId(88005553535L);
        chat.setType("private");
        chat.setFirstName("Tester");

        Message message = new Message();
        message.setMessageId(1);
        message.setChat(chat);
        message.setText("default text");

        update.setMessage(message);

        return update;
    }

    public static Update createUpdate(String text) {
        Update update = new Update();

        Chat chat = new Chat();
        chat.setId(88005553535L);
        chat.setType("private");
        chat.setFirstName("Tester");

        Message message = new Message();
        message.setMessageId(1);
        message.setChat(chat);
        message.setText(text);

        update.setMessage(message);

        return update;
    }
}
