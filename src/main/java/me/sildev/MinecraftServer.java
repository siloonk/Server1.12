package me.sildev;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MinecraftServer implements AutoCloseable {

    /*
        Server constants
     */
    public static final int PROTOCOL_VERSION = 340;
    public static final int MAX_PLAYERS = 2024;
    public static final String VERSION_NAME = "JAVServer 1.12.2";
    public static final String MOTD = "§aA JAVServer on version 1.12.2!";


    ArrayList<ClientHandler> clients = new ArrayList<>();

    ServerSocket server;

    public MinecraftServer(String ip, int port) throws IOException {
        server = new ServerSocket();
        server.bind(new InetSocketAddress(ip, port));
        System.out.println("Registering all incoming packets...");
        PacketHandler.registerAllPacketListeners();
        System.out.println("Registered all incoming packets!");
    }

    public void run() throws IOException {
        System.out.println("Listening for connections...");
        while (!isClosed()) {
            Socket socket = server.accept();
            System.out.println("Connection made with " + socket.getInetAddress().getHostAddress());
            ClientHandler handler = new ClientHandler(socket, this);
            clients.add(handler);
            new Thread(handler).start();
        }
    }

    public boolean containsPlayer(String username) {
        for (ClientHandler handler : clients) {
            if (handler.getPlayer() == null) continue;
            if (handler.getPlayer().getUsername().equals(username)) return true;
        }
        return false;
    }

    public boolean isClosed() {
        return server.isClosed() || !server.isBound();
    }

    public void removeClient(ClientHandler client) {
        this.clients.remove(client);
    }

    @Override
    public void close() throws Exception {
    }
}
