package me.sildev.packets.listener.status;

import me.sildev.ClientHandler;
import me.sildev.packets.PacketListener;
import me.sildev.packets.clientbound.status.StatusPingResponsePacket;
import me.sildev.packets.serverbound.status.StatusPingRequestPacket;

import java.io.IOException;

public class StatusPingPacketListener implements PacketListener<StatusPingRequestPacket> {


    @Override
    public void handle(ClientHandler client, StatusPingRequestPacket packet) throws IOException {
        long payload = packet.getPayload();

        client.sendPacket(new StatusPingResponsePacket(payload));
    }
}
