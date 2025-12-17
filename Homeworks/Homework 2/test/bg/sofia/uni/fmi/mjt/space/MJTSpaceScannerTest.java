package bg.sofia.uni.fmi.mjt.space;

import bg.sofia.uni.fmi.mjt.space.algorithm.SymmetricBlockCipher;
import bg.sofia.uni.fmi.mjt.space.dataset.MissionDatasets;
import bg.sofia.uni.fmi.mjt.space.exception.TimeFrameMismatchException;
import bg.sofia.uni.fmi.mjt.space.mission.Detail;
import bg.sofia.uni.fmi.mjt.space.mission.Mission;
import bg.sofia.uni.fmi.mjt.space.mission.MissionStatus;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static bg.sofia.uni.fmi.mjt.space.dataset.MissionDatasets.DATE_FORMATTER;
import static org.junit.jupiter.api.Assertions.*;

class MJTSpaceScannerTest {
    MJTSpaceScanner spaceScanner;
    @Test
    void loadWholeDataset() throws IOException {
        //try(Reader missions = new FileReader("dataset/all-missions-from-1957.csv");
        //    Reader rockets = new FileReader("dataset/all-rockets-from-1957.csv")) {
        //    spaceScanner = new MJTSpaceScanner(missions, rockets, (SymmetricBlockCipher) null);
        //}
        //return;
    }

    @Test
    void testGetAllMissionsNullStatus() {
        spaceScanner = new MJTSpaceScanner((List<Mission>)null, null, null);
        assertThrows(IllegalArgumentException.class, () -> spaceScanner.getAllMissions(null), "IllegalArgument expected");
    }

    @Test
    void testGetAllMissionsDataset1StatusSuccess() {
        spaceScanner = new MJTSpaceScanner(MissionDatasets.DATASET1_PARSED, null, null);
        Collection<Mission> expected =  List.of(
            new Mission("2395", "RVSN USSR","Site 31/6, Baikonur Cosmodrome, Kazakhstan", LocalDate.parse("Fri Jul 10, 1981", DATE_FORMATTER), new Detail("Vostok-2M", "Meteor-Priroda n†­5 & Iskra 1"), RocketStatus.STATUS_RETIRED, Optional.empty(), MissionStatus.SUCCESS),
            new Mission("2396", "RVSN USSR","Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Wed Jun 24, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block ML", "Molniya-1 n†­92"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
            new Mission("2397", "General Dynamics","SLC-3W, Vandenberg AFB, California, USA", LocalDate.parse("Tue Jun 23, 1981", DATE_FORMATTER),new Detail("Atlas-E/F Star-37S-ISS", "NOAA-C"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
            new Mission("2398", "RVSN USSR", "Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Fri Jun 19, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block 2BL", "Cosmos 1278"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
            new Mission("2399", "ESA", "ELV-1 (SLV), Guiana Space Centre, French Guiana, France", LocalDate.parse("Fri Jun 19, 1981", DATE_FORMATTER),new Detail("Ariane 1", "CAT-3 & Others"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
            new Mission("2400", "RVSN USSR", "Site 41/1, Plesetsk Cosmodrome, Russia", LocalDate.parse("Tue Jun 09, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block ML", "Molniya-3 n†­91"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
            new Mission("2401", "RVSN USSR", "Site 132/2, Plesetsk Cosmodrome, Russia", LocalDate.parse("Thu Jun 04, 1981", DATE_FORMATTER), new Detail("Cosmos-3M (11K65M)", "Cosmos 1275"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
            new Mission("2403", "General Dynamics", "SLC-36B, Cape Canaveral AFS, Florida, USA", LocalDate.parse("Sat May 23, 1981", DATE_FORMATTER), new Detail("Atlas-SLV3D Centaur-D1AR", "Intelsat V -501"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
            new Mission("2404", "RVSN USSR", "Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Tue May 19, 1981", DATE_FORMATTER), new Detail("Vostok-2M", "Cosmos 1271"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS)
        );
        Collection<Mission> actual  = spaceScanner.getAllMissions(MissionStatus.SUCCESS);
        assertEquals(expected, actual, "Not what was expected");
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissionsFromToMismatch() {
        spaceScanner = new MJTSpaceScanner((List<Mission>)null, null, null);
        assertThrows(TimeFrameMismatchException.class, () -> spaceScanner.getCompanyWithMostSuccessfulMissions(LocalDate.of(2023, 12, 30), LocalDate.of(1973, 12, 3)), "TimeMismatch expected");
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissionsFromToNull() {
        spaceScanner = new MJTSpaceScanner((List<Mission>)null, null, null);
        assertThrows(IllegalArgumentException.class, () -> spaceScanner.getCompanyWithMostSuccessfulMissions(null, null), "TimeMismatch expected");
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissionsDataset1All() {
        spaceScanner = new MJTSpaceScanner(MissionDatasets.DATASET1_PARSED, null, null);
        String actual = spaceScanner.getCompanyWithMostSuccessfulMissions(LocalDate.MIN, LocalDate.MAX);
        String expected = "RVSN USSR";
        assertEquals(expected, actual, "Not what was expected");
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissionsDataset1All() {
        spaceScanner = new MJTSpaceScanner(MissionDatasets.DATASET1_PARSED, null, null);
        String actual = spaceScanner.getCompanyWithMostSuccessfulMissions(LocalDate.MIN, LocalDate.MAX);
        String expected = "RVSN USSR";
        assertEquals(expected, actual, "Not what was expected");
    }

}