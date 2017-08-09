package com.klapper.ttsgame.reference;

/**
 * Created by c1103304 on 2017/7/21.
 */

public class message {
    int type;
    String message;

    public message(int type, String message) {
        this.type = type;
        this.message = message;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
