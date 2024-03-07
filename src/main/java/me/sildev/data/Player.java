package me.sildev.data;

import me.sildev.ClientHandler;
import me.sildev.packets.clientbound.play.*;

import java.io.IOException;
import java.util.UUID;

public class Player {

    String username;
    UUID uuid;
    Location location;
    ClientHandler handler;
    int lastTeleportID;
    boolean isGrounded;

    public Player(String username, UUID uuid, ClientHandler handler) {
        this.username = username;
        this.uuid = uuid;
        this.handler = handler;
        this.location = new Location(8, 100, 8);
        isGrounded= false;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUUID() {
        return uuid;
    }


    public Location getLocation() {
        return location;
    }

    public void setLastTeleportID(int id) {
        this.lastTeleportID = id;
    }

    public int getLastTeleportID() {
        return lastTeleportID;
    }

    public void setGrounded(boolean isGrounded) {
        this.isGrounded = isGrounded;
    }

    public boolean isGrounded() {
        return isGrounded;
    }



    public void play() throws IOException {

        handler.sendPacket(new ClientJoinGamePacket());
        handler.sendPacket(new PlayerPositionAndLookPacket(this));
        //handler.sendPacket(new SpawnPlayerPacket(this));
        handler.sendPacket(new ClientChatMessagePacket("§eWelcome to the server!"));
        handler.sendPacket(new KeepAlivePacket());
        handler.sendPacket(new PlayerListHeaderAndFooterPacket("§eTest header", "§aTest Footer"));
        handler.sendPacket(new AddPlayerListItemPacket(this));
        /*for (int i = -20; i < 20; i++) {
            for (int j = -20; j < 20; j++) {
                handler.sendPacket(new ChunkDataPacket(i, j));
            }
        }*/
        handler.sendPacket(new ChunkDataPacket(0, 0));
        handler.sendPacket(new ClientChatMessagePacket("§eWelcome to the server (after the chunk load packets were sent)!"));
        System.out.println("Sent keep alive packet!");

        //handler.sendPacket(new MultiBlockChangePacket(0, 0, 1));
    }

}
