# Homework 2: Space Scanner

Load and analyze space mission and rocket datasets using Java streams, readers, records, and optional values. The project also supports AES encryption and decryption of stream data.

## Features

- Filter missions by status and rank missions by cost.
- Group missions by country and analyze companies and launch locations.
- Rank rockets by height and retrieve their Wikipedia links.
- Select the most reliable rocket for a date interval and save its name as encrypted output.

The source package is `bg.sofia.uni.fmi.mjt.space`. `SpaceScannerAPI` defines the operations, implemented by `MJTSpaceScanner`. `mission/` and `rocket/` contain models and loaders; `CSVHelper` handles CSV tokenization; `algorithm/` provides `SymmetricBlockCipher` and its AES implementation, `Rijndael`.

## Data and usage

The datasets are included in `dataset/`:

- [all-missions-from-1957.csv](dataset/all-missions-from-1957.csv)
- [all-rockets-from-1957.csv](dataset/all-rockets-from-1957.csv)

Use JDK 25 and the [shared compilation instructions](../../README.md#compile-from-powershell). Production code requires only the JDK. There is no console entry point.

Construct `MJTSpaceScanner` with mission and rocket readers and an AES `SecretKey`. For example, from a driver or JShell with this homework as the working directory:

```java
import java.nio.file.Files;
import java.nio.file.Path;
import javax.crypto.KeyGenerator;
import bg.sofia.uni.fmi.mjt.space.MJTSpaceScanner;

var keyGenerator = KeyGenerator.getInstance("AES");
keyGenerator.init(128);
var key = keyGenerator.generateKey();

try (var missions = Files.newBufferedReader(Path.of("dataset/all-missions-from-1957.csv"));
     var rockets = Files.newBufferedReader(Path.of("dataset/all-rockets-from-1957.csv"))) {
    var scanner = new MJTSpaceScanner(missions, rockets, key);
    System.out.println(scanner.getTopNTallestRockets(5));
}
```

Use `saveMostReliableRocket(outputStream, from, to)` for encrypted output and retain the same key if you need to decrypt it with `Rijndael`.

## Tests

Mark `test/` as a test source root and configure JUnit Jupiter. Run tests with this homework directory as the working directory so dataset paths resolve.

Suites cover the scanner, CSV helper, mission and rocket parsing and loaders, status enums, and cipher operations. Test fixtures also include small in-memory datasets. Some scanner tests are marked `@Disabled`.

[Repository overview and dependency setup](../../README.md)
