# Lab 09: Multithreaded Image Steganography

Embed secret images inside PNG cover images and extract them using a producer-consumer processing pipeline.

## How it works

`ImageCodec` defines bulk embedding and extraction. `ImageCodecImpl` delegates to threaded processors in `embed/` and `extract/`, which coordinate producers and consumers through blocking queues. Its default consumer count is the number of available processors; an alternate constructor accepts a count.

The encoder stores secret-image dimensions in the first eight cover pixels. It then averages each secret pixel's RGB channels and stores the three most significant grayscale bits in the least significant RGB bits of a cover pixel. Extraction therefore produces an approximate grayscale image. Each cover must have at least eight more pixels than its paired secret, and secret dimensions must fit in 12 bits (at most 4095 per dimension).

The source package is `bg.sofia.uni.fmi.mjt.steganography`. `queue/` also contains a separate blocking-queue exercise; the image processors use `java.util.concurrent.BlockingQueue`.

## Run

Use JDK 25. From this directory in PowerShell:

```powershell
$sourceFiles = @(Get-ChildItem -Path src -Recurse -Filter '*.java' | ForEach-Object { $_.FullName })
javac -d out @sourceFiles
java -cp out bg.sofia.uni.fmi.mjt.steganography.Main
```

The working directory must be this lab directory. The demo reads PNGs from `cover/` and `secret/`, writes embedded images to `output_embedded/`, then extracts them to `output_extracted/`. It pairs input images in lexicographic filename order. Supply the same number of cover and secret images, with sufficient cover capacity for every pair.

Sample inputs and outputs are included. Running the demo writes to the output directories, including replacing files with matching names. To keep the samples, call `ImageCodecImpl` from a driver with different output paths.

Only the JDK is required. There is no automated test source directory in this lab.

[Repository overview](../../README.md)
