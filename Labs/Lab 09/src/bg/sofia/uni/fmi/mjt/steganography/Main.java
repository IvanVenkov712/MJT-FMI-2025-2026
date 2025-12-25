package bg.sofia.uni.fmi.mjt.steganography;

public class Main {
    static void main() {
        ImageCodec imageCodec = new ImageCodecImpl();
        imageCodec.embedPNGImages("cover", "secret", "output_embedded");
        imageCodec.extractPNGImages("output_embedded", "output_extracted");
    }
}