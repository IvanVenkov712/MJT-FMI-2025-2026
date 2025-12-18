package bg.sofia.uni.fmi.mjt.space;

import bg.sofia.uni.fmi.mjt.space.algorithm.Rijndael;
import bg.sofia.uni.fmi.mjt.space.algorithm.SymmetricBlockCipher;
import bg.sofia.uni.fmi.mjt.space.exception.CipherException;
import bg.sofia.uni.fmi.mjt.space.exception.TimeFrameMismatchException;
import bg.sofia.uni.fmi.mjt.space.mission.Mission;
import bg.sofia.uni.fmi.mjt.space.mission.MissionLoader;
import bg.sofia.uni.fmi.mjt.space.mission.MissionStatus;
import bg.sofia.uni.fmi.mjt.space.rocket.Rocket;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketLoader;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;

import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MJTSpaceScanner implements SpaceScannerAPI {
    private static final double RELIABILITY_SUCCESSFUL_MULTIPLIER = 2.0;
    private static final double RELIABILITY_ALL_MULTIPLIER = 2.0;

    private final Collection<Mission> missions;
    private final Collection<Rocket> rockets;
    private final SymmetricBlockCipher symmetricBlockCipher;

    private Map<String, Collection<Mission>> cachedMissionsPreCountry;
    private Map<String, String> cachedMostDesiredLocationForMissionsPerCompany;
    private Map<String, List<Rocket>> cachedRocketByName;
    private Map<String, Optional<String>> cachedWikiPageForRocket;
    private Map<MissionStatus, List<Mission>> cachedMissionsByMissionStatus;
    private List<Rocket> rocketsSortedByHeight;

    public MJTSpaceScanner(Reader missionsReader, Reader rocketsReader, SecretKey secretKey) {
        if (missionsReader == null) {
            throw new IllegalArgumentException("missionReader is null");
        }
        if (rocketsReader == null) {
            throw new IllegalArgumentException("rockets is null");
        }
        missions = MissionLoader.load(missionsReader);
        rockets = RocketLoader.load(rocketsReader);
        symmetricBlockCipher = new Rijndael(secretKey);
    }

    // For tests
    MJTSpaceScanner(Collection<Mission> missions, Collection<Rocket> rockets,
                    SymmetricBlockCipher symmetricBlockCipher) {
        this.missions = missions;
        this.rockets = rockets;
        this.symmetricBlockCipher = symmetricBlockCipher;
    }

    @Override
    public Collection<Mission> getAllMissions() {
        return List.copyOf(missions);
    }

    @Override
    public Collection<Mission> getAllMissions(MissionStatus missionStatus) {
        if (missionStatus == null) {
            throw new IllegalArgumentException("missionStatus is null");
        }
        if (cachedMissionsByMissionStatus == null) {
            cachedMissionsByMissionStatus = missions.stream().collect(Collectors.groupingBy(Mission::missionStatus));
        }
        return cachedMissionsByMissionStatus.getOrDefault(missionStatus, List.of());
    }

    @Override
    public String getCompanyWithMostSuccessfulMissions(LocalDate from, LocalDate to) {
        validateFromTo(from, to);
        return missions.stream().filter(mission -> dateInInterval(mission.date(), from, to))
            .filter(mission -> mission.missionStatus() == MissionStatus.SUCCESS)
            .collect(Collectors.groupingBy(Mission::company, Collectors.counting())).entrySet().stream()
            .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");
    }

    @Override
    public Map<String, Collection<Mission>> getMissionsPerCountry() {
        if (cachedMissionsPreCountry == null) {
            cachedMissionsPreCountry = missions.stream()
                .collect(Collectors.groupingBy(Mission::getCountry, Collectors.toCollection(ArrayList::new)));
        }
        return cachedMissionsPreCountry;
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
        if (cachedMostDesiredLocationForMissionsPerCompany == null) {
            cachedMostDesiredLocationForMissionsPerCompany = missions.stream().collect(
                Collectors.groupingBy(Mission::company,
                    Collectors.collectingAndThen(Collectors.groupingBy(Mission::location, Collectors.counting()),
                        map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(Map.entry("", 0L))
                            .getKey())));

        }
        return cachedMostDesiredLocationForMissionsPerCompany;
    }

    @Override
    public Map<String, String> getLocationWithMostSuccessfulMissionsPerCompany(LocalDate from, LocalDate to) {
        validateFromTo(from, to);
        return missions.stream().filter(mission -> dateInInterval(mission.date(), from, to))
            .filter(mission -> mission.missionStatus() == MissionStatus.SUCCESS).collect(
                Collectors.groupingBy(Mission::company,
                    Collectors.collectingAndThen(Collectors.groupingBy(Mission::location, Collectors.counting()),
                        map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(Map.entry("", 0L))
                            .getKey())));

    }

    @Override
    public Collection<Rocket> getAllRockets() {
        return List.copyOf(rockets);
    }

    @Override
    public List<Rocket> getTopNTallestRockets(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        if (rocketsSortedByHeight == null) {
            rocketsSortedByHeight = rockets.stream().sorted(
                    Comparator.comparingDouble((Rocket rocket) -> rocket.height().orElse(Double.MIN_VALUE)).reversed())
                .toList();
            // return rockets.stream()
            //    .sorted(
            //        Comparator.comparingDouble((Rocket rocket) -> rocket.height().orElse(Double.MIN_VALUE))
            //        .reversed())
            //    .limit(n).toList();
        }
        return rocketsSortedByHeight.subList(0, Math.min(n, rocketsSortedByHeight.size()));
    }

    @Override
    public Map<String, Optional<String>> getWikiPageForRocket() {
        if (cachedWikiPageForRocket == null) {
            cachedWikiPageForRocket = rockets.stream().collect(Collectors.toMap(Rocket::name, Rocket::wiki));
        }
        return cachedWikiPageForRocket;
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
            .sorted(Comparator.comparing((Mission mission) -> mission.cost().orElse(Double.MIN_VALUE)).reversed())
            .limit(n).map(mission -> mission.detail().rocketName()).distinct()
            .map(rocketName -> getRocketWiki(rocketName).orElse("")).toList();

    }

    @Override
    public void saveMostReliableRocket(OutputStream outputStream, LocalDate from, LocalDate to) throws CipherException {
        validateFromTo(from, to);
        if (outputStream == null) {
            throw new IllegalArgumentException("outputStream is null");
        }
        String rockedName = getMostReliableRocket(from, to);
        try (InputStream rocketNameStream = new ByteArrayInputStream(rockedName.getBytes(StandardCharsets.UTF_8))) {
            symmetricBlockCipher.encrypt(rocketNameStream, outputStream);
        } catch (Exception e) {
            throw new CipherException("saveMostReliableRocket failed", e);
        }
    }

    //For testing
    String getMostReliableRocket(LocalDate from, LocalDate to) {
        return rockets.stream().map(Rocket::name)
            .max(Comparator.comparingDouble((String rName) -> getRocketReliability(rName, from, to))).orElse("");
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

    private double getRocketReliability(String rocketName, LocalDate from, LocalDate to) {
        long countSuccessful = missions.stream().filter(mission -> dateInInterval(mission.date(), from, to))
            .filter(mission -> mission.detail().rocketName().equals(rocketName))
            .filter(mission -> mission.missionStatus() == MissionStatus.SUCCESS).count();
        long countUnsuccessful = missions.stream().filter(mission -> dateInInterval(mission.date(), from, to))
            .filter(mission -> mission.detail().rocketName().equals(rocketName))
            .filter(mission -> mission.missionStatus() != MissionStatus.SUCCESS).count();

        long countAll = countSuccessful + countUnsuccessful;
        if (countAll == 0) {
            return 0.0;
        }
        return (RELIABILITY_SUCCESSFUL_MULTIPLIER * countSuccessful + countUnsuccessful) /
            (RELIABILITY_ALL_MULTIPLIER * countAll);
    }

    private Optional<Rocket> getRocketByName(String rocketName) {
        if (cachedRocketByName == null) {
            cachedRocketByName = rockets.stream().collect(Collectors.groupingBy(Rocket::name));
        }
        if (cachedRocketByName.containsKey(rocketName)) {
            return Optional.of(cachedRocketByName.get(rocketName).getFirst());
        }
        return Optional.empty();
    }

    private Optional<String> getRocketWiki(String rocketName) {
        return getRocketByName(rocketName).orElse(new Rocket("", "", Optional.of(""), Optional.of(0.0))).wiki();
    }

    private static boolean dateInInterval(LocalDate date, LocalDate from, LocalDate to) {
        return date.compareTo(to) <= 0 && date.compareTo(from) >= 0;
    }
}