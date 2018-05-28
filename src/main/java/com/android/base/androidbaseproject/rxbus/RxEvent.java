package com.android.base.androidbaseproject.rxbus;

public class RxEvent<T> {

    private int eventId;
    private T t;

    public RxEvent(int eventId, T t) {
        this.eventId = eventId;
        this.t = t;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
