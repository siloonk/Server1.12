package me.sildev.packets.listener.login;

import me.sildev.ClientHandler;
import me.sildev.MinecraftServer;
import me.sildev.data.Player;
import me.sildev.packets.PacketListener;
import me.sildev.packets.clientbound.login.LoginDisconnectPacket;
import me.sildev.packets.clientbound.login.LoginSuccesspacket;
import me.sildev.packets.clientbound.play.ClientJoinGamePacket;
import me.sildev.packets.clientbound.play.PlayerPositionAndLookPacket;
import me.sildev.packets.serverbound.login.ClientLoginStartPacket;
import me.sildev.utils.GameState;

import java.io.IOException;
import java.util.UUID;

public class ClientLoginStartPacketListener implements PacketListener<ClientLoginStartPacket> {


    @Override
    public void handle(ClientHandler client, ClientLoginStartPacket packet) throws IOException {
        if (client.getProtocolVersion() != MinecraftServer.PROTOCOL_VERSION) {
            client.sendPacket(new LoginDisconnectPacket("The server is still on version 1.12.2!"));
        }

        String uuidString = "OfflinePlayer:" + packet.getUsername();
        client.setPlayer(new Player(packet.getUsername(), UUID.nameUUIDFromBytes(uuidString.getBytes()), client));
        client.sendPacket(new LoginSuccesspacket(client.getPlayer()));
        client.setState(GameState.PLAY);
        client.getPlayer().play();
    }
}
