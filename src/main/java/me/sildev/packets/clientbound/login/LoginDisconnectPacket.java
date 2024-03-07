package me.sildev.packets.clientbound.login;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.IOException;

public class LoginDisconnectPacket extends Packet {
    public LoginDisconnectPacket(String reason) throws IOException {
        super(0x00);
        DataTypes.writeString(getWrapper(), String.format("{\"text\": \"%s\"}", reason));
    }
}
