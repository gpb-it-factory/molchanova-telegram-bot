package ru.molchmd.minibank.frontend.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountsListResponse {
    private final String accountName;
    private final String amount;

    public AccountsListResponse(@JsonProperty("accountName") String accountName,
                                @JsonProperty("amount") String amount) {
        this.accountName = accountName;
        this.amount = amount;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAmount() {
        return amount;
    }
}
