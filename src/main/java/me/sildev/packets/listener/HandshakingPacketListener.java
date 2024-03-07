package me.sildev.packets.listener;

import me.sildev.ClientHandler;
import me.sildev.packets.PacketListener;
import me.sildev.packets.serverbound.HandshakingPacket;
import me.sildev.utils.GameState;

import java.io.IOException;

public class HandshakingPacketListener implements PacketListener<HandshakingPacket> {


    @Override
    public void handle(ClientHandler client, HandshakingPacket packet) throws IOException {
        client.setProtocolVersion(packet.getProtocolVersion());
        if (packet.getNextState() == 1) client.setState(GameState.STATUS);
        else client.setState(GameState.LOGIN);
    }
}
