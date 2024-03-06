package me.sildev.data;

import com.google.gson.Gson;

public class ServerStatusResponse {

    final Version version;
    final Players players;
    final String description;

    public ServerStatusResponse(Players player, Version version, String description) {
        this.players = player;
        this.version = version;
        this.description = description;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static class Version {
        final int protocol;
        final String name;

        public Version(int protocol, String name) {
            this.protocol = protocol;
            this.name = name;
        }
    }

    public static class Players {
        final int online, max;

        public Players(int online, int max) {
            this.online = online;
            this.max = max;
        }
    }
}
