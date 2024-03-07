package me.sildev.packets.listener.status;

import me.sildev.ClientHandler;
import me.sildev.MinecraftServer;
import me.sildev.data.ServerStatusResponse;
import me.sildev.packets.PacketListener;
import me.sildev.packets.clientbound.status.StatusResponsePacket;
import me.sildev.packets.serverbound.status.StatusRequestPacket;

import java.io.IOException;

public class StatusRequestPacketListener implements PacketListener<StatusRequestPacket> {

    @Override
    public void handle(ClientHandler client, StatusRequestPacket packet) throws IOException {
        ServerStatusResponse response = new ServerStatusResponse(
          new ServerStatusResponse.Players(0, MinecraftServer.MAX_PLAYERS),
          new ServerStatusResponse.Version(MinecraftServer.PROTOCOL_VERSION, MinecraftServer.VERSION_NAME),
                MinecraftServer.MOTD
        );

        client.sendPacket(new StatusResponsePacket(response.toJson()));
    }
}
