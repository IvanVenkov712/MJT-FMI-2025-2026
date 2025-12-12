package bg.sofia.uni.fmi.mjt.space.mission;

import java.util.Arrays;
import java.util.List;

public record Detail(String rocketName, String payload) {
    public static Detail of(String detailStr) {
        List<String> values = Arrays.stream(detailStr.split("\\|"))
            .map(String::strip).toList();
        return new Detail(values.get(0), values.get(1));
    }
}
