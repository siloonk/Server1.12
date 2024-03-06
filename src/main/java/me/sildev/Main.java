package me.sildev;

public class Main {
    public static void main(String[] args) {
        try (MinecraftServer server = new MinecraftServer("127.0.0.1", 25565)) {
            System.out.println("Running server on 127.0.0.1:25565");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}