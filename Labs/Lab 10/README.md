# Lab 10: Playlist Client and Server

A TCP client-server exercise for managing playlists, songs, and likes. It uses Java NIO channels, a selector-based server, command parsing, and JSON responses. The implemented commands manage playlist metadata.

## Structure

The source package is `bg.sofia.uni.fmi.mjt.music`.

- `server/MusicStreamingServer` accepts client connections and executes commands.
- `server/repository/InMemoryPlaylistRepository` stores playlists and songs in memory.
- `client/` provides a stream-based client and a client that works directly with NIO buffers and channels.
- `command/` parses commands, executes them, and formats responses.
- `ServerMain`, `ClientMain`, and `ClientNIOMain` are the runnable entry points.

## Setup and run

Use JDK 25 and add Gson (`com.google.code.gson:gson`) as a production dependency. Gson is referenced by the module but its jar is not bundled. See the [repository setup instructions](../../README.md#setup).

In the IDE, run `bg.sofia.uni.fmi.mjt.music.ServerMain` first. Then run `bg.sofia.uni.fmi.mjt.music.ClientMain` or `bg.sofia.uni.fmi.mjt.music.ClientNIOMain`. The entry points use port **7777**, and clients connect to `localhost`.

For PowerShell, compile from this directory using the [Gson compilation example](../../README.md#compile-from-powershell), then start the server:

```powershell
java -cp "out;$gsonJar" bg.sofia.uni.fmi.mjt.music.ServerMain
```

In another terminal in the same directory, set `$gsonJar` to the same local jar path and run:

```powershell
java -cp "out;$gsonJar" bg.sofia.uni.fmi.mjt.music.ClientMain
```

## Commands

Quote arguments containing spaces. For example:

```text
create-playlist "Study Mix"
add-song "Study Mix" "Example Song" "Example Artist" 180
like-song "Study Mix" "Example Song" "Example Artist"
unlike-song "Study Mix" "Example Song" "Example Artist"
list-playlists
get-playlist "Study Mix"
disconnect
```

Song duration must be a positive integer. Playlists are held in memory and are lost when the server stops.

## Tests

Mark `test/` as a test source root and configure JUnit Jupiter. `CommandCreatorTest` covers command parsing, including quoted arguments. Run it in the IDE with Gson available for compilation.

[Repository overview](../../README.md)
