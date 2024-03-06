package me.sildev.packets;

import me.sildev.packets.serverbound.HandshakingPacket;
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

            }
        });

        PACKETS.put(GameState.LOGIN, new ConcurrentHashMap<>(){
            {

            }
        });

        PACKETS.put(GameState.CONFIGURATION, new ConcurrentHashMap<>(){
            {

            }
        });

        PACKETS.put(GameState.PLAY, new ConcurrentHashMap<>(){
            {

            }
        });
    }

    public static Class<? extends Packet> get(GameState state, int id) {
        return PACKETS.get(state).get(id);
    }
}
