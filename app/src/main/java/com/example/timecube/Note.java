package com.example.timecube;

import org.litepal.crud.DataSupport;

public class Note extends DataSupport {
     private String note;
     private long remind_time;
     private long deadline;

    public void setNote(String note) {
        this.note = note;
    }

    public void setRemind_time(long remind_time) {
        this.remind_time = remind_time;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getNote() {
        return note;
    }

    public long getRemind_time() {
        return remind_time;
    }

    public long getDeadline() {
        return deadline;
    }
}
