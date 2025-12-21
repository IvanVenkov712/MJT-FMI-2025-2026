package bg.sofia.uni.fmi.mjt.space.mission;

import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;

import java.time.LocalDate;
import java.util.Optional;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.List;

import static bg.sofia.uni.fmi.mjt.space.CSVHelper.parseCsvLine;

public record Mission(String id, String company, String location, LocalDate date, Detail detail,
                      RocketStatus rocketStatus, Optional<Double> cost, MissionStatus missionStatus) {

    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("EEE MMM dd, yyyy", Locale.ENGLISH);

    private static final int ID_INDEX = 0;
    private static final int COMPANY_INDEX = 1;
    private static final int LOCATION_INDEX = 2;
    private static final int DATE_INDEX = 3;
    private static final int DETAIL_INDEX = 4;
    private static final int ROCKET_STATUS_INDEX = 5;
    private static final int COST_INDEX = 6;
    private static final int MISSION_STATUS_INDEX = 7;

    public String getCountry() {
        return List.of(location.split(",")).getLast().strip();
    }

    public static Mission of(String line) {
        List<String> fields = parseCsvLine(line);

        String id = fields.get(ID_INDEX).strip();
        String company = fields.get(COMPANY_INDEX).strip();
        String location = fields.get(LOCATION_INDEX).strip();
        String dateStr = fields.get(DATE_INDEX).strip();
        LocalDate date = dateStr.isEmpty() ? null : LocalDate.parse(dateStr, DATE_FORMATTER);
        Detail detail = Detail.of(fields.get(DETAIL_INDEX).strip());
        RocketStatus rocketStatus = RocketStatus.of(fields.get(ROCKET_STATUS_INDEX));
        Optional<Double> cost = readCost(fields.get(COST_INDEX));
        MissionStatus missionStatus = MissionStatus.of(fields.get(MISSION_STATUS_INDEX));
        return new Mission(id, company, location, date, detail, rocketStatus, cost, missionStatus);
    }

    /*
    private static RocketStatus readRocketStatus(String str) {
        RocketStatus rocketStatus = null;
        if (!str.isBlank()) {
            rocketStatus = RocketStatus.valueOf(
                str.strip().toUpperCase().replaceAll("\\s", "_")
            );
        }
        return rocketStatus;
    }
     */

    private static Optional<Double> readCost(String str) {
        Optional<Double> cost = Optional.empty();
        String rawCost = str.strip().replace("\"", "").strip().replace(",", "");
        if (!rawCost.isEmpty()) {
            cost = Optional.of(Double.parseDouble(rawCost));
        }
        return cost;
    }

    /*
    private static MissionStatus readMissionStatus(String str) {
        MissionStatus missionStatus = null;
        if (!str.isBlank()) {
            missionStatus = MissionStatus.valueOf(
                str.strip().toUpperCase().replaceAll("\\s", "_")
            );
        }
        return missionStatus;
    }
     */


    /*
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Mission mission = (Mission) o;
        return Objects.equals(id, mission.id) && Objects.equals(detail, mission.detail) &&
            Objects.equals(company, mission.company) && Objects.equals(date, mission.date) &&
            Objects.equals(location, mission.location) && Objects.equals(cost, mission.cost) &&
            rocketStatus == mission.rocketStatus && missionStatus == mission.missionStatus;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(company);
        result = 31 * result + Objects.hashCode(location);
        result = 31 * result + Objects.hashCode(date);
        result = 31 * result + Objects.hashCode(detail);
        result = 31 * result + Objects.hashCode(rocketStatus);
        result = 31 * result + Objects.hashCode(cost);
        result = 31 * result + Objects.hashCode(missionStatus);
        return result;
    }
     */
}
