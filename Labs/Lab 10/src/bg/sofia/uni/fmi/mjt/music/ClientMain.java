package bg.sofia.uni.fmi.mjt.music;

import bg.sofia.uni.fmi.mjt.music.client.MusicStreamingClient;

public class ClientMain {
    static void main() {
        MusicStreamingClient client = new MusicStreamingClient("localhost", 7777);
        client.start();
    }
}
