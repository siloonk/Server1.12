package me.sildev.packets;

import me.sildev.packets.serverbound.HandshakingPacket;
import me.sildev.packets.serverbound.login.ClientLoginStartPacket;
import me.sildev.packets.serverbound.play.ClientKeepAlivePacket;
import me.sildev.packets.serverbound.play.PlayerPositionPacket;
import me.sildev.packets.serverbound.status.StatusPingRequestPacket;
import me.sildev.packets.serverbound.status.StatusRequestPacket;
import me.sildev.utils.GameState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketRegistry {
    public static final ConcurrentHashMap<GameState, Map<Integer, Class<? extends Packet>>> PACKETS = new ConcurrentHashMap<>();

    static {
        PACKETS.put(GameState.HANDSHAKE, new ConcurrentHashMap<>(){
            {
                put(0x00, HandshakingPacket.class);
            }
        });

        PACKETS.put(GameState.STATUS, new ConcurrentHashMap<>(){
            {
                put(0x00, StatusRequestPacket.class);
                put(0x01, StatusPingRequestPacket.class);
            }
        });

        PACKETS.put(GameState.LOGIN, new ConcurrentHashMap<>(){
            {
                put(0x00, ClientLoginStartPacket.class);
            }
        });

        PACKETS.put(GameState.PLAY, new ConcurrentHashMap<>(){
            {
                put(0x0D, PlayerPositionPacket.class);
                put(0x0B, ClientKeepAlivePacket.class);
            }
        });
    }

    public static Class<? extends Packet> get(GameState state, int id) {
        return PACKETS.get(state).get(id);
    }
}
