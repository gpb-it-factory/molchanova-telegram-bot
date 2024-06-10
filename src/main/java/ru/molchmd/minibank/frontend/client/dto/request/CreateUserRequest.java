package ru.molchmd.minibank.frontend.client.dto.request;

public class CreateUserRequest {
    private final Long userId;

    public CreateUserRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
