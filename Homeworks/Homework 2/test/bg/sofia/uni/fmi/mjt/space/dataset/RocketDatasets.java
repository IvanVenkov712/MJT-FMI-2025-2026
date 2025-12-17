package bg.sofia.uni.fmi.mjt.space.dataset;

import bg.sofia.uni.fmi.mjt.space.rocket.Rocket;

import java.util.List;
import java.util.Optional;

public class RocketDatasets {
    public static final String DATASET1_STRING = """
        "",Name,Wiki,Rocket Height
        88,Atlas-SLV3 Agena-B,https://en.wikipedia.org/wiki/Atlas-Agena,
        89,Atlas-SLV3 Agena-D,https://en.wikipedia.org/wiki/Atlas-Agena,
        90,Atlas-SLV3B Agena-D,https://en.wikipedia.org/wiki/Atlas-Agena,
        91,Atlas-SLV3 Burner-2,https://en.wikipedia.org/wiki/Atlas_SLV-3,
        92,Atlas-SLV3C Centaur-D,https://en.wikipedia.org/wiki/Atlas-Centaur,
        93,Atlas-SLV3D Centaur-D1A,https://en.wikipedia.org/wiki/Atlas-Centaur,
        94,Atlas-SLV3D Centaur-D1AR,https://en.wikipedia.org/wiki/Atlas-Centaur,
        95,Atlas V 401,https://en.wikipedia.org/wiki/Atlas_V,58.3 m
        96,Atlas V 411,https://en.wikipedia.org/wiki/Atlas_V,58.3 m
        97,Atlas V 421,https://en.wikipedia.org/wiki/Atlas_V,58.3 m
        98,Atlas V 431,https://en.wikipedia.org/wiki/Atlas_V,59.1 m
        """;

    public static final List<Rocket> DATASET1_PARSED = List.of(
        new Rocket("88","Atlas-SLV3 Agena-B", Optional.of("https://en.wikipedia.org/wiki/Atlas-Agena"), Optional.empty()),
        new Rocket("89","Atlas-SLV3 Agena-D",Optional.of("https://en.wikipedia.org/wiki/Atlas-Agena"),Optional.empty()),
        new Rocket("90","Atlas-SLV3B Agena-D",Optional.of("https://en.wikipedia.org/wiki/Atlas-Agena"),Optional.empty()),
        new Rocket("91","Atlas-SLV3 Burner-2",Optional.of("https://en.wikipedia.org/wiki/Atlas_SLV-3"),Optional.empty()),
        new Rocket("92","Atlas-SLV3C Centaur-D",Optional.of("https://en.wikipedia.org/wiki/Atlas-Centaur"),Optional.empty()),
        new Rocket("93","Atlas-SLV3D Centaur-D1A",Optional.of("https://en.wikipedia.org/wiki/Atlas-Centaur"),Optional.empty()),
        new Rocket("94","Atlas-SLV3D Centaur-D1AR",Optional.of("https://en.wikipedia.org/wiki/Atlas-Centaur"),Optional.empty()),
        new Rocket("95","Atlas V 401",Optional.of("https://en.wikipedia.org/wiki/Atlas_V"),Optional.of(58.3)),
        new Rocket("96","Atlas V 411",Optional.of("https://en.wikipedia.org/wiki/Atlas_V"),Optional.of(58.3)),
        new Rocket("97","Atlas V 421",Optional.of("https://en.wikipedia.org/wiki/Atlas_V"),Optional.of(58.3)),
        new Rocket("98","Atlas V 431",Optional.of("https://en.wikipedia.org/wiki/Atlas_V"),Optional.of(59.1))
    );
}
