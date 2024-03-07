package me.sildev;

import me.sildev.data.Location;
import me.sildev.data.Player;
import me.sildev.packets.Packet;
import me.sildev.packets.PacketRegistry;
import me.sildev.packets.clientbound.login.LoginDisconnectPacket;
import me.sildev.packets.clientbound.play.*;
import me.sildev.utils.DataTypes;
import me.sildev.utils.GameState;
import me.sildev.world.BlockChangeEntry;
import me.sildev.world.Chunk;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

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
    ArrayList<Chunk> viewingChunks = new ArrayList<>();
    private int renderDistance = 10;
    private String language = "en_US";

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

    public void loadTerrain() {
        int cx = Math.floorDiv(player.getLocation().getBlockX(), 16);
        int cz = Math.floorDiv(player.getLocation().getBlockZ(), 16);

        int sourceX = cx - renderDistance;
        int sourceZ = cz - renderDistance;
        int targetX = cx + renderDistance;
        int targetZ = cz + renderDistance;

        try {

            for (Chunk loaded : viewingChunks.toArray(new Chunk[0])) {
                int lx = loaded.getX();
                int lz = loaded.getZ();
                if (lx < sourceX || lx > targetX || lz < sourceZ || lz > targetZ) {
                    sendPacket(new UnloadChunkPacket(lx, lz));
                    viewingChunks.remove(loaded);
                    loaded.removeViewer(this);
                    getServer().getWorld().reviewChunk(new Location(lx, 0, lz));
                }
            }

            for (int x = sourceX; x <= targetX; x++)
                for (int z = sourceZ; z <= targetZ; z++) {
                    Chunk chk = getServer().getWorld().getChunkAt(x, z);
                    if (viewingChunks.contains(chk)) continue;
                    sendPacket(new ChunkDataPacket(x, z));
                    viewingChunks.add(chk);
                    chk.addViewer(this);

                    for (Map.Entry<Integer, List<BlockChangeEntry>> entry : Chunk.convertToProtocol(chk.getBlocks())
                            .entrySet()) {
                        sendPacket(new MultiBlockChangePacket(x,
                                entry.getKey(),
                                z,
                                entry.getValue()
                                        .toArray(new BlockChangeEntry[0])));
                    }

                }
        } catch (Exception e) {
            e.printStackTrace();
            disconnect(e.toString());
        }
    }

    public List<Chunk> getViewingChunks() {
        return viewingChunks;
    }

    public void disconnect(String reason) {
        try {
            if (state == GameState.LOGIN) {
                sendPacket(new LoginDisconnectPacket(reason));
            } else if (state == GameState.PLAY) sendPacket(new PlayDisconnectPacket(reason));
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        socket.close();
    }
}
