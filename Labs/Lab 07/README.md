# Lab 07: ImageKit

An image-processing library using `BufferedImage` and Java Image I/O, with luminosity-based grayscale conversion and Sobel edge detection.

## Structure

The source package is `bg.sofia.uni.fmi.mjt.imagekit`.

- `algorithm/ImageAlgorithm` is the common processing contract.
- `algorithm/grayscale/LuminosityGrayscale` converts an image to grayscale.
- `algorithm/detection/SobelEdgeDetection` detects edges.
- `filesystem/FileSystemImageManager` defines image loading and saving; `LocalFileSystemImageManager` provides the local implementation.

Sample images and generated results are included in the lab directory, and `test/car.jpg` is available as an input fixture.

## Use and tests

Use JDK 25 and mark `src/` as the source root, or follow the [PowerShell compilation instructions](../../README.md#compile-from-powershell). Production code requires only the JDK.

Load an image with `LocalFileSystemImageManager.loadImage(File)`, pass it to an algorithm's `process(...)` method, and save the result with `saveImage(...)`. The current manager writes PNG output and rejects an output file that already exists; choose a new path with an existing parent directory.

There is no console entry point. Mark `test/` as a test source root, configure JUnit Jupiter, and run the tests in the IDE. Tests cover both algorithms and filesystem operations, including temporary output files.

[Repository overview and dependency setup](../../README.md)
