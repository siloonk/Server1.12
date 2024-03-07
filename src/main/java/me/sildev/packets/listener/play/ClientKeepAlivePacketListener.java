package me.sildev.packets.listener.play;

import me.sildev.ClientHandler;
import me.sildev.packets.PacketListener;
import me.sildev.packets.clientbound.play.KeepAlivePacket;
import me.sildev.packets.serverbound.play.ClientKeepAlivePacket;

import java.io.IOException;

public class ClientKeepAlivePacketListener implements PacketListener<ClientKeepAlivePacket> {


    @Override
    public void handle(ClientHandler client, ClientKeepAlivePacket packet) throws IOException {
        client.sendPacket(new KeepAlivePacket());
    }
}
