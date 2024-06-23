package ru.molchmd.minibank.frontend.client.dto.request;

public class CreateAccountRequest {
    private final String accountName;

    public CreateAccountRequest(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }
}
