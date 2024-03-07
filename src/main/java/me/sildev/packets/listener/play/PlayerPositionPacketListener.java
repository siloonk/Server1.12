package me.sildev.packets.listener.play;

import me.sildev.ClientHandler;
import me.sildev.packets.PacketListener;
import me.sildev.packets.clientbound.play.BlockChangePacket;
import me.sildev.packets.clientbound.play.MultiBlockChangePacket;
import me.sildev.packets.serverbound.play.PlayerPositionPacket;
import me.sildev.utils.DataTypes;

import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerPositionPacketListener implements PacketListener<PlayerPositionPacket> {

    private static final int SECTION_SIZE = 16 * 16 * 16; // Blocks per section

    @Override
    public void handle(ClientHandler client, PlayerPositionPacket packet) throws IOException {
        client.getPlayer().getLocation().setX(packet.getX());
        client.getPlayer().getLocation().setY(packet.getY());
        client.getPlayer().getLocation().setZ(packet.getZ());
        client.getPlayer().setGrounded(packet.isGrounded());
        //client.sendPacket(new BlockChangePacket((int) client.getPlayer().getLocation().getX(), (int) (client.getPlayer().getLocation().getY()-2), (int) client.getPlayer().getLocation().getZ(), 1));
    }
}
