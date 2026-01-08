package bg.sofia.uni.fmi.mjt.music.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

// NIO, blocking
public class MusicStreamingClientNIO {
    public MusicStreamingClientNIO(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    private final String serverHost;
    private final int serverPort;
    private static final int BUFFER_SIZE = 4096;
    private ByteBuffer buffer;

    public void start() {
        buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress(serverHost, serverPort));
            System.out.println("Connected to the server.");
            while (true) {
                String command = getCommandFromInput();
                sendCommand(socketChannel, command);
                String response = getServerResponse(socketChannel);
                handleResponse(response);
                if ("disconnect".equals(command)) {
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }

    private String getCommandFromInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter command: ");
        // read a line from the console
        return scanner.nextLine();
    }

    private String getServerResponse(SocketChannel serverChannel) throws IOException {
        buffer.clear();
        int readBytes = serverChannel.read(buffer);
        if (readBytes < 0) {
            //serverChannel.close();
            return null;
        }
        buffer.flip();

        byte[] serverInputBytes = new byte[buffer.remaining()];
        buffer.get(serverInputBytes);

        return new String(serverInputBytes, StandardCharsets.UTF_8);
    }

    private void sendCommand(SocketChannel serverChannel, String command) throws IOException {
        System.out.println("Sending command <" + command + "> to the server...");
        buffer.clear();
        buffer.put(command.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        serverChannel.write(buffer);
    }

    private void handleResponse(String response) {
        if (response != null) {
            System.out.println(response);
        }
    }
}