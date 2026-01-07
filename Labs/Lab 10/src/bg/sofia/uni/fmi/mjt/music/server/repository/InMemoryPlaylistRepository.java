package bg.sofia.uni.fmi.mjt.music.server.repository;

import bg.sofia.uni.fmi.mjt.music.server.model.Playlist;
import bg.sofia.uni.fmi.mjt.music.server.model.Song;
import bg.sofia.uni.fmi.mjt.music.server.repository.exception.PlaylistAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.music.server.repository.exception.PlaylistNotFoundException;
import bg.sofia.uni.fmi.mjt.music.server.repository.exception.SongAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.music.server.repository.exception.SongNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPlaylistRepository implements PlaylistRepository {
    private final Map<String, Playlist> playlists;

    InMemoryPlaylistRepository(Map<String, Playlist> playlists) {
        this.playlists = playlists;
    }

    public InMemoryPlaylistRepository() {
        this(new ConcurrentHashMap<>());
    }

    @Override
    public void createPlaylist(String playlistName) throws PlaylistAlreadyExistsException {
        if (playlists.containsKey(playlistName)) {
            throw new PlaylistAlreadyExistsException("Playlist exists!");
        }
        playlists.put(playlistName, new Playlist(playlistName, new ConcurrentHashMap<>()));
    }

    @Override
    public Song addSong(String playlistName, String songTitle, String artistName, int duration)
        throws PlaylistNotFoundException, SongAlreadyExistsException {
        if (!playlists.containsKey(playlistName)) {
            throw new PlaylistNotFoundException("Playlist not found");
        }
        Song toAdd = new Song(songTitle, artistName, duration);
        Map<Song, Integer> songs =  playlists.get(playlistName).songs();
        if (songs.containsKey(toAdd)) {
            throw new SongAlreadyExistsException("Song already exists!");
        }
        songs.put(toAdd, 0);
        return toAdd;
    }

    @Override
    public int likeSong(String playlistName, String songTitle, String artistName)
        throws PlaylistNotFoundException, SongNotFoundException {
        if (!playlists.containsKey(playlistName)) {
            throw new PlaylistNotFoundException("Playlist not found");
        }
        Song toSearch = new Song(songTitle, artistName, 0);
        Map<Song, Integer> songs =  playlists.get(playlistName).songs();
        if (!songs.containsKey(toSearch)) {
            throw new SongNotFoundException("Song not found!");
        }
        int likes = songs.get(toSearch);
        likes++;
        songs.put(toSearch, likes);
        return likes;
    }

    @Override
    public int unlikeSong(String playlistName, String songTitle, String artistName)
        throws PlaylistNotFoundException, SongNotFoundException {
        if (!playlists.containsKey(playlistName)) {
            throw new PlaylistNotFoundException("Playlist not found");
        }
        Song toSearch = new Song(songTitle, artistName, 0);
        Map<Song, Integer> songs =  playlists.get(playlistName).songs();
        if (!songs.containsKey(toSearch)) {
            throw new SongNotFoundException("Song not found!");
        }
        int likes = songs.get(toSearch);
        if (likes > 0) {
            likes--;
        }
        songs.put(toSearch, likes);
        return likes;
    }

    @Override
    public Collection<String> getAllPlaylists() {
        return playlists.keySet();
    }

    @Override
    public Playlist getPlaylist(String playlistName) throws PlaylistNotFoundException {
        if (!playlists.containsKey(playlistName)) {
            throw new PlaylistNotFoundException("Playlist not found!");
        }
        return playlists.get(playlistName);
    }
}
