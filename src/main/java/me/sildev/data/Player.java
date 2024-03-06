package me.sildev.data;

import java.util.UUID;

public class Player {

    String username;
    UUID uuid;

    public Player(String username, UUID uuid) {
        this.username = username;
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUUID() {
        return uuid;
    }
}
