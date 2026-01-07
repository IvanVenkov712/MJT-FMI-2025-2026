package bg.sofia.uni.fmi.mjt.music.command;

import bg.sofia.uni.fmi.mjt.music.server.repository.PlaylistRepository;
import bg.sofia.uni.fmi.mjt.music.server.repository.exception.PlaylistAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.music.server.repository.exception.PlaylistNotFoundException;
import bg.sofia.uni.fmi.mjt.music.server.repository.exception.SongAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.music.server.repository.exception.SongNotFoundException;

@SuppressWarnings("CheckStyle")
public class CommandExecutor {
    private static final String INVALID_ARGS_COUNT_MESSAGE_FORMAT =
        "Invalid count of arguments: \"%s\" expects %d arguments. Example: \"%s\"";

    private static final String CREATE_PLAYLIST = "create-playlist";
    private static final String ADD_SONG = "add-song";
    private static final String LIKE_SONG = "like-song";
    private static final String UNLIKE_SONG = "unlike-song";
    private static final String LIST_PLAYLISTS = "list-playlists";
    private static final String GET_PLAYLIST = "get-playlist";
    private static final String DISCONNECT = "disconnect";

    private PlaylistRepository repository;

    public CommandExecutor(PlaylistRepository repository) {
        this.repository = repository;
    }

    public String execute(Command cmd) {
        return switch (cmd.command()) {
            case CREATE_PLAYLIST -> createPlaylist(cmd.arguments());
            case ADD_SONG -> addSong(cmd.arguments());
            case LIKE_SONG -> likeSong(cmd.arguments());
            case UNLIKE_SONG -> unlikeSong(cmd.arguments());
            case LIST_PLAYLISTS -> listPlaylists(cmd.arguments());
            case GET_PLAYLIST -> getPlaylists(cmd.arguments());
            case DISCONNECT -> disconnect(cmd.arguments());
            default -> "Unknown command";
        };
    }

    private String createPlaylist(String[] args) {
        if (args.length != 1) {
            return JSONResult.formatAsJSON(Status.ERROR, "Invalid args count!");
        }
        String playlistName = args[0];
        try {
            repository.createPlaylist(playlistName);
            return JSONResult.formatAsJSON(Status.OK, "Playlist %s created successfully.", args[0]);
        } catch (PlaylistAlreadyExistsException e) {
            return JSONResult.formatAsJSON(Status.ERROR, "Playlist %s already exists.", args[0]);
        }
    }

    private String addSong(String[] args) {
        if (args.length != 4) {
            return JSONResult.formatAsJSON(Status.ERROR, "Incorrect number of args!");
        }
        String playlistName = args[0];
        String songTitle = args[1];
        String artistName = args[2];
        int duration = Integer.parseInt(args[3]);
        if(duration <= 0) {
            return JSONResult.formatAsJSON(Status.ERROR, "Duration must be a positive integer");
        }
        try {
            repository.addSong(playlistName, songTitle, artistName, duration);
            return JSONResult.formatAsJSON(Status.OK, "Song %s by %s added successfully.",
                songTitle, artistName);
        } catch (PlaylistNotFoundException e) {
            return JSONResult.formatAsJSON(Status.ERROR, "Playlist %s does not exist.", playlistName);
        } catch (SongAlreadyExistsException e) {
            return JSONResult.formatAsJSON(Status.ERROR, "Song Imagine by John-Lennon already exists in playlist MyFavorites.");
        } catch (Exception e) {
            return JSONResult.formatAsJSON(Status.ERROR, "Unknown error!");
        }
    }

    private String likeSong(String[] args) {
        if(args.length != 3) {
            return JSONResult.formatAsJSON(Status.ERROR, "Invalid arguments count");
        }
        String playlistName = args[0];
        String songTitle = args[1];
        String artistName = args[2];
        try {
            repository.likeSong(playlistName, songTitle, artistName);
            int numberOfLikes = repository.getPlaylist(playlistName).songs().get(songTitle);
            return JSONResult.formatAsJSON(Status.OK, "Song %s by %s liked. Likes: %d",
                songTitle, artistName, numberOfLikes);
        } catch (PlaylistNotFoundException e) {
            return JSONResult.formatAsJSON(Status.ERROR, "Playlist %s does not exist.", playlistName);
        } catch (SongNotFoundException e) {
            return JSONResult.formatAsJSON(Status.ERROR,"Song %s by %s does not exist in playlist %s.",
                songTitle, artistName, playlistName);
        }
    }

    private String unlikeSong(String[] args) {
        if(args.length != 3) {
            return JSONResult.formatAsJSON(Status.ERROR, "Invalid arguments count");
        }
        String playlistName = args[0];
        String songTitle = args[1];
        String artistName = args[2];
        try {
            repository.unlikeSong(playlistName, songTitle, artistName);
            int numberOfLikes = repository.getPlaylist(playlistName).songs().get(songTitle);
            return JSONResult.formatAsJSON(Status.OK, "Song %s by %s liked. Likes: %d",
                songTitle, artistName, numberOfLikes);
        } catch (PlaylistNotFoundException e) {
            return JSONResult.formatAsJSON(Status.ERROR, "Playlist %s does not exist.", playlistName);
        } catch (SongNotFoundException e) {
            return JSONResult.formatAsJSON(Status.ERROR,"Song %s by %s does not exist in playlist %s.",
                songTitle, artistName, playlistName);
        }
    }

    private String complete(String[] args) {
        if (args.length != 2) {
            return String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, COMPLETE, 2,
                COMPLETE + " <username> <todo_item_id>");
        }

        String user = args[0];
        int todoID;
        try {
            todoID = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return "Invalid ID provided for command \"complete-todo\": only integer values are allowed";
        }

        repository.remove(user, todoID);
        return String.format("Completed To Do with ID %s for user %s", todoID, user);
    }

    private String list(String[] args) {
        if (args.length != 1) {
            return String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, LIST, 1, LIST + " <username>");
        }
        String user = args[0];
        var todos = repository.list(user);
        if (todos.isEmpty()) {
            return "No To-Do items found for user with name " + user;
        }

        StringBuilder response = new StringBuilder(String.format("To-Do list of user %s:%n", user));
        todos.forEach((k, v) -> response.append(String.format("[%d] %s%n", k, v)));

        return response.toString();
    }
}
