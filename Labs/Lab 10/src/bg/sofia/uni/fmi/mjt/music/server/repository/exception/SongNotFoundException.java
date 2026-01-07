package bg.sofia.uni.fmi.mjt.music.server.repository.exception;

public class SongNotFoundException extends Exception {
    public SongNotFoundException(String message) {
        super(message);
    }

    public SongNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
