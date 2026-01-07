package bg.sofia.uni.fmi.mjt.music.server.model;

import bg.sofia.uni.fmi.mjt.music.command.JSONResult;

import java.util.Map;
import java.util.Objects;

public record Playlist(String name, Map<Song, Integer> songs) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Playlist playlist = (Playlist) o;
        return Objects.equals(name, playlist.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return String.format("{\"name\":\"%s\",\"songs\":%s}", name, getJSONFromSongs());
    }

    private String getJSONFromSongs() {
        return JSONResult.asJSONArray(
            songs.entrySet().stream()
            .map(e -> JSONResult.getJSONFromSong(e.getKey(), e.getValue()))
            .toList()
        );
    }
}
