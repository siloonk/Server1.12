package me.sildev.packets.serverbound.login;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.IOException;

public class ClientLoginStartPacket extends Packet {

    String username;

    public ClientLoginStartPacket(byte[] data) throws IOException {
        super(data);

        this.username = DataTypes.readString(getIStream());
    }

    public String getUsername() {
        return username;
    }
}
