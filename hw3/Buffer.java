package edu.dt;

import java.util.Observable;

public class Buffer extends Observable {
    private Message message;

    public Buffer(){
    }
    public Buffer(Message message) {
        this.message = message;
    }

    public Message  getMessage() {
        return message;
    }

    public void setMessage(Message message ,Processor processor) {
        this.message = message;
        setChanged();
        notifyObservers(processor);
    }
}

