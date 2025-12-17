package bg.sofia.uni.fmi.mjt.space.mission;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record Detail(String rocketName, String payload) {
    public static Detail of(String detailStr) {
        List<String> values = Arrays.stream(detailStr.split("\\|"))
            .map(String::strip).toList();
        return new Detail(values.get(0), values.get(1));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Detail detail = (Detail) o;
        return Objects.equals(payload, detail.payload) && Objects.equals(rocketName, detail.rocketName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(rocketName);
        result = 31 * result + Objects.hashCode(payload);
        return result;
    }
}
