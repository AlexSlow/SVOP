package com.svop.message.tabloSeazon;

/**
 * Предназначен как параметр для обращения из Ajax на сервер
 */
public class TabloControleMessage {
    private boolean active;

    public TabloControleMessage() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "TabloControleMessage{" +
                "active=" + active +
                '}';
    }
}
