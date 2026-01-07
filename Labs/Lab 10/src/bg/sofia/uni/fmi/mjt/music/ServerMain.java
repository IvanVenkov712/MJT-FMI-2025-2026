package bg.sofia.uni.fmi.mjt.music;

import bg.sofia.uni.fmi.mjt.music.server.MusicStreamingServer;
import bg.sofia.uni.fmi.mjt.music.server.repository.InMemoryPlaylistRepository;

public class ServerMain {
    static void main() {
        MusicStreamingServer server = new MusicStreamingServer(7777, new InMemoryPlaylistRepository());
        server.start();
    }
}
