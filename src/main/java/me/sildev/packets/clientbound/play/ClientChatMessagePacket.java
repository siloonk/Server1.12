package me.sildev.packets.clientbound.play;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.IOException;

public class ClientChatMessagePacket extends Packet {
    public ClientChatMessagePacket(String message) throws IOException {
        super(0x0F);
        String json = "{\"text\": \"%s\"}".formatted(message);
        DataTypes.writeString(getWrapper(), json);
        getWrapper().write(0);
    }
}
