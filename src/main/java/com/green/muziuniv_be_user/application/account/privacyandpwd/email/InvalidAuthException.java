package com.green.muziuniv_be_user.application.account.privacyandpwd.email;

public class InvalidAuthException extends IllegalArgumentException {
    public InvalidAuthException(String message) {
        super(message);
    }
}