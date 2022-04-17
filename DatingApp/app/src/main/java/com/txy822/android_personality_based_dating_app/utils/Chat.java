package com.txy822.android_personality_based_dating_app.utils;

import com.google.firebase.Timestamp;

/**
 * Chat class conversation detail
 */
public class Chat {
    String from;
    String to;
    String message;
    Timestamp time_stamp;
    /**
     *
     * @return time stamp
     */
    public Timestamp getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Timestamp time_stamp) {
        this.time_stamp = time_stamp;
    }

    public Chat() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
