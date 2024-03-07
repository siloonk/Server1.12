package me.sildev.data;

import me.sildev.ClientHandler;
import me.sildev.packets.clientbound.play.*;
import me.sildev.world.BlockChangeEntry;

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
        handler.sendPacket(new ClientChatMessagePacket("§eWelcome to the server!"));
        handler.sendPacket(new KeepAlivePacket());
        handler.sendPacket(new PlayerListHeaderAndFooterPacket("§eTest header", "§aTest Footer"));
        handler.sendPacket(new AddPlayerListItemPacket(this));

        for (int i = -5; i < 5; i++) {
            for (int j = -5; j < 5; j++) {
                handler.sendPacket(new ChunkDataPacket(i, j));
            }
        }

        handler.sendPacket(new ClientChatMessagePacket("§7Sending the Block Change packet on your position with blockID 1!"));
        handler.sendPacket(new BlockChangePacket(getLocation().getBlockX(), getLocation().getBlockY(), getLocation().getBlockZ(), 1));
        handler.sendPacket(new ClientChatMessagePacket("§aSent the block change packet!"));
        //handler.loadTerrain();
    }

}
