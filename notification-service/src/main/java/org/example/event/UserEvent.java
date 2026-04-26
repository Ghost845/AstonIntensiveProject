package org.example.event;

import java.io.Serializable;

public class UserEvent implements Serializable {

    public UserOperation operation;

    public String email;

    public UserEvent() {}

    public UserEvent(UserOperation operation, String email) {
        this.operation = operation;
        this.email = email;
    }
}