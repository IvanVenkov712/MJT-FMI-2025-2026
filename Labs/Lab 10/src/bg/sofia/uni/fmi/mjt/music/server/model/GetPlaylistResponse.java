package bg.sofia.uni.fmi.mjt.music.server.model;

import bg.sofia.uni.fmi.mjt.music.command.Status;

public record GetPlaylistResponse(Status status, Playlist playlist) { }
