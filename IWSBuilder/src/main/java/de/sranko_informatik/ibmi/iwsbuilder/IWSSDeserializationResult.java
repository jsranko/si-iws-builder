package de.sranko_informatik.ibmi.iwsbuilder;

import java.util.ArrayList;
import java.util.List;

public class IWSSDeserializationResult {
    private IWSS iwsserver;
    private List<String> messages;

    public IWSS getIwsserver() {
        return iwsserver;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void setIwsserver(IWSS iwsserver) {
        this.iwsserver = iwsserver;
    }

    public IWSSDeserializationResult message(String message) {
        if(messages == null) {
            messages = new ArrayList<String>();
        }
        messages.add(message);
        return this;
    }
}