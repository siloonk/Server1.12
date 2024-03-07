package me.sildev;

import me.sildev.data.Player;
import me.sildev.packets.Packet;
import me.sildev.packets.PacketRegistry;
import me.sildev.packets.clientbound.play.KeepAlivePacket;
import me.sildev.utils.DataTypes;
import me.sildev.utils.GameState;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private DataInputStream in;
    private OutputStream out;
    private GameState state;
    PacketHandler handler;

    MinecraftServer server;

    /*
           Client Data
     */
    int protocol = -1;
    Player player;

    public ClientHandler(Socket socket, MinecraftServer server) throws IOException {
        this.socket = socket;
        this.out = socket.getOutputStream();
        this.in = new DataInputStream(socket.getInputStream());
        handler = new PacketHandler(this);
        state = GameState.HANDSHAKE;
        this.server = server;
    }

    @Override
    public void run() {
        while (!isClosed()) {
            try {
                int packetLength = DataTypes.readVarInt(in);
                int packetID = DataTypes.readVarInt(in);
                byte[] data = new byte[packetLength - DataTypes.getVarIntLength(packetID)];
                in.readFully(data);

                Class<? extends Packet> packetClass = PacketRegistry.get(state, packetID);
                if (packetClass == null) continue;
                try {
                    Packet packet = packetClass.getConstructor(byte[].class).newInstance(data);
                    handler.handle(packet);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                         NoSuchMethodException e) {
                    throw new IOException(e);
                }
            } catch (IOException e) {
                return;
            }
        }
        getServer().removeClient(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MinecraftServer getServer() {
        return server;
    }

    public void setProtocolVersion(int version) {
        this.protocol = version;
    }

    public int getProtocolVersion() {
        return protocol;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void sendPacket(Packet packet) throws IOException {
        out.write(packet.getData());
    }

    public boolean isClosed() {
        return socket.isClosed() || !socket.isConnected();
    }
}
