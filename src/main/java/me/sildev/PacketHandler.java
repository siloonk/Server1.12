package me.sildev;

import me.sildev.packets.Packet;
import me.sildev.packets.PacketListener;
import me.sildev.packets.listener.HandshakingPacketListener;
import me.sildev.packets.serverbound.HandshakingPacket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PacketHandler {

    ClientHandler client;

    public PacketHandler(ClientHandler client) {
        this.client = client;
    }

    private static ConcurrentHashMap<Class<? extends Packet>, List<PacketListener>> packetListeners = new ConcurrentHashMap<>();

    public void handle(Packet packet) {
        List<PacketListener> packetListenerList = packetListeners.get(packet.getClass());
        for (PacketListener listener : packetListenerList) {
            try {
                listener.handle(client, packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void register(Class<? extends Packet> packetClass, PacketListener listener) {
        List<PacketListener> listeners = packetListeners.getOrDefault(packetClass, new ArrayList<>());
        listeners.add(listener);
        packetListeners.put(packetClass, listeners);
    }

    public static void registerAllPacketListeners() {
        register(HandshakingPacket.class, new HandshakingPacketListener());
    }
}
