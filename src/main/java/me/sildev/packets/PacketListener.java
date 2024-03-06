package me.sildev.packets;

import me.sildev.ClientHandler;

import java.io.IOException;

public interface PacketListener<T extends Packet> {

    void handle(ClientHandler client, T packet) throws IOException;
}
