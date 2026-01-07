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
    private static final int BUFFER_SIZE = 512;

    public void start() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(serverHost, serverPort));

            System.out.println("Connected to the server.");

            while (true) {
                System.out.print("Enter command: ");
                String message = scanner.nextLine(); // read a line from the console

                if ("disconnect".equals(message)) {
                    break;
                }

                System.out.println("Sending command <" + message + "> to the server...");

                buffer.clear(); // switch to writing mode
                buffer.put(message.getBytes(StandardCharsets.UTF_8)); // buffer fill
                buffer.flip(); // switch to reading mode
                socketChannel.write(buffer); // buffer drain

                buffer.clear(); // switch to writing mode
                socketChannel.read(buffer); // buffer fill
                buffer.flip(); // switch to reading mode

                byte[] byteArray = new byte[buffer.remaining()];
                buffer.get(byteArray);
                String reply = new String(byteArray, "UTF-8"); // buffer drain

                // if the buffer is a non-direct one, it has a wrapped array and we can get it
                //String reply = new String(buffer.array(), 0, buffer.position(), "UTF-8"); // buffer drain

                System.out.println("Server responded: " + reply);
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }

}