package util;

import model.Client;

public class AuthResult {

    private boolean success;
    private String message;
    private Client client;

    public AuthResult(boolean success, String message, Client client) {
        this.success = success;
        this.message = message;
        this.client = client;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Client getClient() {
        return client;
    }
}
