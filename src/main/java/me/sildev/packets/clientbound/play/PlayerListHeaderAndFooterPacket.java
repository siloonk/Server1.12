package me.sildev.packets.clientbound.play;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.IOException;

public class PlayerListHeaderAndFooterPacket extends Packet {
    public PlayerListHeaderAndFooterPacket(String header, String footer) throws IOException {
        super(0x4A);

        String headerJson = "{\"text\": \"%s\"}".formatted(header);
        String footerJson = "{\"text\": \"%s\"}".formatted(footer);

        DataTypes.writeString(getWrapper(), headerJson);
        DataTypes.writeString(getWrapper(), footerJson);
    }
}
