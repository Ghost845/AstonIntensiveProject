package org.example.event;

import java.io.Serializable;

public class UserEvent implements Serializable {

    public String operation;

    public String email;

    public UserEvent() {}

    public UserEvent(String operation, String email) {
        this.operation = operation;
        this.email = email;
    }
}