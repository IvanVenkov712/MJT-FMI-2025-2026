package bg.sofia.uni.fmi.mjt.music.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

// NIO specifics wrapped & hidden
public class MusicStreamingClient {
    private final String hostName;
    private final int port;

    public MusicStreamingClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public void start() {
        try (SocketChannel socketChannel = SocketChannel.open();
             BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, "UTF-8"));
             PrintWriter writer = new PrintWriter(Channels.newWriter(socketChannel, "UTF-8"), true);
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(hostName, port));

            System.out.println("Connected to the server.");

            while (true) {
                System.out.print("Enter command: ");
                String message = scanner.nextLine(); // read a line from the console

                if ("disconnect".equals(message)) {
                    break;
                }

                System.out.println("Sending command <" + message + "> to the server...");

                String reply = reader.readLine(); // read the response from the server
                System.out.println(reply);
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }
}
