package me.sildev.packets.clientbound.login;

import me.sildev.data.Player;
import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.IOException;

public class LoginSuccesspacket extends Packet {
    public LoginSuccesspacket(Player player) throws IOException {
        super(0x02);

        DataTypes.writeString(getWrapper(), player.getUUID().toString());
        DataTypes.writeString(getWrapper(), player.getUsername());
    }
}
