package bg.sofia.uni.fmi.mjt.music;

import bg.sofia.uni.fmi.mjt.music.client.MusicStreamingClientNIO;

public class ClientNIOMain {
    static void main() {
        MusicStreamingClientNIO client = new MusicStreamingClientNIO("localhost", 7777);
        client.start();
    }
}
