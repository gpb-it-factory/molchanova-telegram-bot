package ru.molchmd.minibank.frontend.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.molchmd.minibank.frontend.telegrambot.util.ExtendedStatus;

public class Error {
    private final ExtendedStatus type;

    public Error(@JsonProperty("type") ExtendedStatus type) {
        this.type = type;
    }

    public ExtendedStatus getType() {
        return type;
    }
}
