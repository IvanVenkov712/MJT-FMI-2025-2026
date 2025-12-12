package bg.sofia.uni.fmi.mjt.space;

import bg.sofia.uni.fmi.mjt.space.algorithm.Rijndael;
import bg.sofia.uni.fmi.mjt.space.algorithm.SymmetricBlockCipher;
import bg.sofia.uni.fmi.mjt.space.exception.CipherException;
import bg.sofia.uni.fmi.mjt.space.exception.TimeFrameMismatchException;
import bg.sofia.uni.fmi.mjt.space.mission.Mission;
import bg.sofia.uni.fmi.mjt.space.mission.MissionStatus;
import bg.sofia.uni.fmi.mjt.space.rocket.Rocket;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;

import javax.crypto.SecretKey;
import java.io.OutputStream;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MJTSpaceScanner implements SpaceScannerAPI {

    private Collection<Mission> missions;
    private Collection<Rocket> rockets;
    private final SymmetricBlockCipher symmetricBlockCipher;

    public MJTSpaceScanner(Reader missionsReader, Reader rocketsReader, SecretKey secretKey) {
        // TODO: Initialize collections
        // Maybe missions need to be sorted by time.
        this.symmetricBlockCipher = new Rijndael(secretKey);

    }

    // For mocking
    MJTSpaceScanner(Collection<Mission> missions, Collection<Rocket> rockets,
                    SymmetricBlockCipher symmetricBlockCipher) {
        this.missions = missions;
        this.rockets = rockets;
        this.symmetricBlockCipher = symmetricBlockCipher;
    }

    @Override
    public Collection<Mission> getAllMissions() {
        return missions;
    }

    @Override
    public Collection<Mission> getAllMissions(MissionStatus missionStatus) {
        if (missionStatus == null) {
            throw new IllegalArgumentException("missionStatus is null");
        }
        return missions.stream().filter(mission -> mission.missionStatus() == missionStatus)
            .collect(Collectors.toList());
    }

    @Override
    public String getCompanyWithMostSuccessfulMissions(LocalDate from, LocalDate to) {
        validateFromTo(from, to);
        return missions.stream()
            .filter(mission -> mission.date().compareTo(to) <= 0 && mission.date().compareTo(from) >= 0)
            .filter(mission -> mission.missionStatus() == MissionStatus.SUCCESS)
            .collect(Collectors.groupingBy(Mission::company, Collectors.counting())).entrySet().stream()
            .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");
    }

    @Override
    public Map<String, Collection<Mission>> getMissionsPerCountry() {
        return missions.stream()
            .collect(Collectors.groupingBy(Mission::location, Collectors.toCollection(ArrayList::new)));
    }

    @Override
    public List<Mission> getTopNLeastExpensiveMissions(int n, MissionStatus missionStatus, RocketStatus rocketStatus) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        if (missionStatus == null) {
            throw new IllegalArgumentException("missionStatus is null");
        }
        if (rocketStatus == null) {
            throw new IllegalArgumentException("rocketStatus is null");
        }
        return missions.stream()
            .filter(mission -> mission.missionStatus() == missionStatus && mission.rocketStatus() == rocketStatus)
            .sorted(Comparator.comparing(mission -> mission.cost().orElse(Double.MAX_VALUE))).limit(n).toList();

    }

    @Override
    public Map<String, String> getMostDesiredLocationForMissionsPerCompany() {
        return missions.stream().collect(Collectors.groupingBy(Mission::company,
            Collectors.collectingAndThen(Collectors.groupingBy(Mission::location, Collectors.counting()),
                map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(Map.entry("", 0L)).getKey())));
    }

    @Override
    public Map<String, String> getLocationWithMostSuccessfulMissionsPerCompany(LocalDate from, LocalDate to) {
        validateFromTo(from, to);
        return missions.stream()
            .filter(mission -> mission.date().compareTo(to) <= 0 && mission.date().compareTo(from) >= 0)
            .filter(mission -> mission.missionStatus() == MissionStatus.SUCCESS).collect(
                Collectors.groupingBy(Mission::company,
                    Collectors.collectingAndThen(Collectors.groupingBy(Mission::location, Collectors.counting()),
                        map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(Map.entry("", 0L))
                            .getKey())));

    }

    @Override
    public Collection<Rocket> getAllRockets() {
        return rockets;
    }

    @Override
    public List<Rocket> getTopNTallestRockets(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        return rockets.stream()
            .sorted(Comparator.comparingDouble((Rocket rocket) -> rocket.height().orElse(Double.MAX_VALUE)).reversed())
            .limit(n).toList();

    }

    @Override
    public Map<String, Optional<String>> getWikiPageForRocket() {
        return rockets.stream()
            .collect(Collectors.toMap(Rocket::name, Rocket::wiki));
    }

    @Override
    public List<String> getWikiPagesForRocketsUsedInMostExpensiveMissions(int n, MissionStatus missionStatus,
                                                                          RocketStatus rocketStatus) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        if (missionStatus == null) {
            throw new IllegalArgumentException("missionStatus is null");
        }
        if (rocketStatus == null) {
            throw new IllegalArgumentException("rocketStatus is null");
        }
        return missions.stream()
            .filter(mission -> mission.missionStatus() == missionStatus && mission.rocketStatus() == rocketStatus)
            .sorted(Comparator.comparing((Mission mission) -> mission.cost().orElse(Double.MAX_VALUE)).reversed())
            .limit(n)
            .map(mission -> mission.detail().rocketName())
            .map(
                rocketName -> rockets.stream()
                    .filter(rocket -> rocket.name().equals(rocketName))
                    .findFirst().orElse(new Rocket("", "", Optional.of(""), Optional.of(0.0)))
                    .wiki().orElse("")
            ).toList();

    }

    @Override
    public void saveMostReliableRocket(OutputStream outputStream, LocalDate from, LocalDate to) throws CipherException {
        validateFromTo(from, to);
        if (outputStream == null) {
            throw new IllegalArgumentException("outputStream is null");
        }

    }

    private static void validateFromTo(LocalDate from, LocalDate to) {
        if (from == null) {
            throw new IllegalArgumentException("from is null");
        }
        if (to == null) {
            throw new IllegalArgumentException("to is null");
        }
        if (to.isBefore(from)) {
            throw new TimeFrameMismatchException("to is before from");
        }
    }
}