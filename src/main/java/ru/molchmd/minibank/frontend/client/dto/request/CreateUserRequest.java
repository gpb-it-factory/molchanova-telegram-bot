package ru.molchmd.minibank.frontend.client.dto.request;

public class CreateUserRequest {
    private final Long userId;
    private final String userName;

    public CreateUserRequest(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
