package me.sildev.packets.clientbound.play;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.IOException;

public class PlayDisconnectPacket extends Packet {
    public PlayDisconnectPacket(String reason) throws IOException {
        super(0x1A);
        DataTypes.writeString(getWrapper(), String.format("{\"text\": \"%s\"}", reason));
    }
}
