package bg.sofia.uni.fmi.mjt.music.server.model;

import bg.sofia.uni.fmi.mjt.music.command.Status;

import java.util.List;

public record ListPlaylistsResponse(Status status, String message, List<String> playlists) {
}
