package bg.sofia.uni.fmi.mjt.space.dataset;

import bg.sofia.uni.fmi.mjt.space.mission.Detail;
import bg.sofia.uni.fmi.mjt.space.mission.Mission;
import bg.sofia.uni.fmi.mjt.space.mission.MissionStatus;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class MissionDatasets {
    public static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("EEE MMM dd, yyyy", Locale.ENGLISH);

    public static final String DATASET1_STRING = """
        Unnamed: 0,Company Name,Location,Datum,Detail,Status Rocket," Rocket",Status Mission
        2395,RVSN USSR,"Site 31/6, Baikonur Cosmodrome, Kazakhstan","Fri Jul 10, 1981",Vostok-2M | Meteor-Priroda n†­5 & Iskra 1,StatusRetired,,Success
        2396,RVSN USSR,"Site 43/3, Plesetsk Cosmodrome, Russia","Wed Jun 24, 1981",Molniya-M /Block ML | Molniya-1 n†­92,StatusRetired,,Success
        2397,General Dynamics,"SLC-3W, Vandenberg AFB, California, USA","Tue Jun 23, 1981",Atlas-E/F Star-37S-ISS | NOAA-C,StatusRetired,,Success
        2398,RVSN USSR,"Site 43/3, Plesetsk Cosmodrome, Russia","Fri Jun 19, 1981",Molniya-M /Block 2BL | Cosmos 1278,StatusRetired,,Success
        2399,ESA,"ELV-1 (SLV), Guiana Space Centre, French Guiana, France","Fri Jun 19, 1981",Ariane 1 | CAT-3 & Others,StatusRetired,,Success
        2400,RVSN USSR,"Site 41/1, Plesetsk Cosmodrome, Russia","Tue Jun 09, 1981",Molniya-M /Block ML | Molniya-3 n†­91,StatusRetired,,Success
        2401,RVSN USSR,"Site 132/2, Plesetsk Cosmodrome, Russia","Thu Jun 04, 1981",Cosmos-3M (11K65M) | Cosmos 1275,StatusRetired,,Success
        2402,ISRO,"SLV LP, Satish Dhawan Space Centre, India","Sun May 31, 1981",SLV-3 | Rohini RS-D1,StatusRetired,,Partial Failure
        2403,General Dynamics,"SLC-36B, Cape Canaveral AFS, Florida, USA","Sat May 23, 1981",Atlas-SLV3D Centaur-D1AR | Intelsat V -501,StatusRetired,,Success
        2404,RVSN USSR,"Site 43/3, Plesetsk Cosmodrome, Russia","Tue May 19, 1981",Vostok-2M | Cosmos 1271,StatusRetired,,Success
        """;

    public static final List<Mission> DATASET1_PARSED = List.of(
        new Mission("2395", "RVSN USSR","Site 31/6, Baikonur Cosmodrome, Kazakhstan", LocalDate.parse("Fri Jul 10, 1981", DATE_FORMATTER), new Detail("Vostok-2M", "Meteor-Priroda n†­5 & Iskra 1"), RocketStatus.STATUS_RETIRED, Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2396", "RVSN USSR","Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Wed Jun 24, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block ML", "Molniya-1 n†­92"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2397", "General Dynamics","SLC-3W, Vandenberg AFB, California, USA", LocalDate.parse("Tue Jun 23, 1981", DATE_FORMATTER),new Detail("Atlas-E/F Star-37S-ISS", "NOAA-C"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2398", "RVSN USSR", "Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Fri Jun 19, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block 2BL", "Cosmos 1278"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2399", "ESA", "ELV-1 (SLV), Guiana Space Centre, French Guiana, France", LocalDate.parse("Fri Jun 19, 1981", DATE_FORMATTER),new Detail("Ariane 1", "CAT-3 & Others"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2400", "RVSN USSR", "Site 41/1, Plesetsk Cosmodrome, Russia", LocalDate.parse("Tue Jun 09, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block ML", "Molniya-3 n†­91"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2401", "RVSN USSR", "Site 132/2, Plesetsk Cosmodrome, Russia", LocalDate.parse("Thu Jun 04, 1981", DATE_FORMATTER), new Detail("Cosmos-3M (11K65M)", "Cosmos 1275"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2402", "ISRO", "SLV LP, Satish Dhawan Space Centre, India", LocalDate.parse("Sun May 31, 1981", DATE_FORMATTER), new Detail("SLV-3", "Rohini RS-D1"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.PARTIAL_FAILURE),
        new Mission("2403", "General Dynamics", "SLC-36B, Cape Canaveral AFS, Florida, USA", LocalDate.parse("Sat May 23, 1981", DATE_FORMATTER), new Detail("Atlas-SLV3D Centaur-D1AR", "Intelsat V -501"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2404", "RVSN USSR", "Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Tue May 19, 1981", DATE_FORMATTER), new Detail("Vostok-2M", "Cosmos 1271"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS)
    );

    public static final List<Mission> DATASET2_PARSED = List.of(
        new Mission("2395", "A","Site 31/6, Baikonur Cosmodrome, Kazakhstan", LocalDate.parse("Fri Jul 10, 1981", DATE_FORMATTER), new Detail("Vostok-2M", "Meteor-Priroda n†­5 & Iskra 1"), RocketStatus.STATUS_RETIRED, Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2396", "A","Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Wed Jun 24, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block ML", "Molniya-1 n†­92"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2397", "A","SLC-3W, Vandenberg AFB, California, USA", LocalDate.parse("Tue Jun 23, 1981", DATE_FORMATTER),new Detail("Atlas-E/F Star-37S-ISS", "NOAA-C"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2398", "A", "Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Fri Jun 19, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block 2BL", "Cosmos 1278"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2399", "B", "ELV-1 (SLV), Guiana Space Centre, French Guiana, France", LocalDate.parse("Fri Jun 19, 1981", DATE_FORMATTER),new Detail("Ariane 1", "CAT-3 & Others"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2400", "B", "Site 41/1, Plesetsk Cosmodrome, Russia", LocalDate.parse("Tue Jun 09, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block ML", "Molniya-3 n†­91"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2401", "B", "Site 132/2, Plesetsk Cosmodrome, Russia", LocalDate.parse("Thu Jun 04, 1981", DATE_FORMATTER), new Detail("Cosmos-3M (11K65M)", "Cosmos 1275"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2402", "B", "SLV LP, Satish Dhawan Space Centre, India", LocalDate.parse("Sun May 31, 1981", DATE_FORMATTER), new Detail("SLV-3", "Rohini RS-D1"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.PARTIAL_FAILURE),
        new Mission("2403", "B", "SLC-36B, Cape Canaveral AFS, Florida, USA", LocalDate.parse("Sat May 23, 1981", DATE_FORMATTER), new Detail("Atlas-SLV3D Centaur-D1AR", "Intelsat V -501"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.FAILURE),
        new Mission("2404", "B", "Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Tue May 19, 1981", DATE_FORMATTER), new Detail("Vostok-2M", "Cosmos 1271"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.PRELAUNCH_FAILURE)
    );

    public static final List<Mission> DATASET3_PARSED = List.of(
        new Mission("2395", "RVSN USSR","Site 31/6, Baikonur Cosmodrome, Kazakhstan", LocalDate.parse("Fri Jul 10, 1981", DATE_FORMATTER), new Detail("Vostok-2M", "Meteor-Priroda n†­5 & Iskra 1"), RocketStatus.STATUS_RETIRED, Optional.of(29.2), MissionStatus.SUCCESS),
        new Mission("2396", "RVSN USSR","Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Wed Jun 24, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block ML", "Molniya-1 n†­92"), RocketStatus.STATUS_RETIRED,Optional.of(66.89), MissionStatus.SUCCESS),
        new Mission("2397", "General Dynamics","SLC-3W, Vandenberg AFB, California, USA", LocalDate.parse("Tue Jun 23, 1981", DATE_FORMATTER),new Detail("Atlas-E/F Star-37S-ISS", "NOAA-C"), RocketStatus.STATUS_RETIRED,Optional.of(123.37), MissionStatus.SUCCESS),
        new Mission("2398", "RVSN USSR", "Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Fri Jun 19, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block 2BL", "Cosmos 1278"), RocketStatus.STATUS_RETIRED,Optional.of(1.2), MissionStatus.SUCCESS),
        new Mission("2399", "ESA", "ELV-1 (SLV), Guiana Space Centre, French Guiana, France", LocalDate.parse("Fri Jun 19, 1981", DATE_FORMATTER),new Detail("Ariane 1", "CAT-3 & Others"), RocketStatus.STATUS_RETIRED,Optional.of(10.3), MissionStatus.SUCCESS),
        new Mission("2400", "RVSN USSR", "Site 41/1, Plesetsk Cosmodrome, Russia", LocalDate.parse("Tue Jun 09, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block ML", "Molniya-3 n†­91"), RocketStatus.STATUS_RETIRED,Optional.of(3.75), MissionStatus.SUCCESS),
        new Mission("2401", "RVSN USSR", "Site 132/2, Plesetsk Cosmodrome, Russia", LocalDate.parse("Thu Jun 04, 1981", DATE_FORMATTER), new Detail("Cosmos-3M (11K65M)", "Cosmos 1275"), RocketStatus.STATUS_RETIRED,Optional.of(45.6), MissionStatus.SUCCESS),
        new Mission("2402", "ISRO", "SLV LP, Satish Dhawan Space Centre, India", LocalDate.parse("Sun May 31, 1981", DATE_FORMATTER), new Detail("SLV-3", "Rohini RS-D1"), RocketStatus.STATUS_RETIRED,Optional.of(15.3), MissionStatus.PARTIAL_FAILURE),
        new Mission("2403", "General Dynamics", "SLC-36B, Cape Canaveral AFS, Florida, USA", LocalDate.parse("Sat May 23, 1981", DATE_FORMATTER), new Detail("Atlas-SLV3D Centaur-D1AR", "Intelsat V -501"), RocketStatus.STATUS_RETIRED,Optional.of(70.3), MissionStatus.SUCCESS),
        new Mission("2404", "RVSN USSR", "Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Tue May 19, 1981", DATE_FORMATTER), new Detail("Vostok-2M", "Cosmos 1271"), RocketStatus.STATUS_RETIRED,Optional.of(56.3), MissionStatus.SUCCESS)
    );

    public static final List<Mission> DATASET4_PARSED = List.of(
        new Mission("2395", "RVSN USSR","Site 31/6, Baikonur Cosmodrome, Kazakhstan", LocalDate.parse("Fri Jul 10, 1981", DATE_FORMATTER), new Detail("Vostok-2M", "Meteor-Priroda n†­5 & Iskra 1"), RocketStatus.STATUS_RETIRED, Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2396", "RVSN USSR","Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Wed Jun 24, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block ML", "Molniya-1 n†­92"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2397", "General Dynamics","SLC-3W, Vandenberg AFB, California, USA", LocalDate.parse("Tue Jun 23, 1981", DATE_FORMATTER),new Detail("Atlas-E/F Star-37S-ISS", "NOAA-C"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.FAILURE),
        new Mission("2398", "RVSN USSR", "Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Fri Jun 19, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block 2BL", "Cosmos 1278"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2399", "ESA", "ELV-1 (SLV), Guiana Space Centre, French Guiana, France", LocalDate.parse("Fri Jun 19, 1981", DATE_FORMATTER),new Detail("Ariane 1", "CAT-3 & Others"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2400", "RVSN USSR", "Site 41/1, Plesetsk Cosmodrome, Russia", LocalDate.parse("Tue Jun 09, 1981", DATE_FORMATTER), new Detail("Molniya-M /Block ML", "Molniya-3 n†­91"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2401", "RVSN USSR", "Site 132/2, Plesetsk Cosmodrome, Russia", LocalDate.parse("Thu Jun 04, 1981", DATE_FORMATTER), new Detail("Cosmos-3M (11K65M)", "Cosmos 1275"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2402", "ISRO", "SLV LP, Satish Dhawan Space Centre, India", LocalDate.parse("Sun May 31, 1981", DATE_FORMATTER), new Detail("SLV-3", "Rohini RS-D1"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.PARTIAL_FAILURE),
        new Mission("2403", "General Dynamics", "SLC-36B, Cape Canaveral AFS, Florida, USA", LocalDate.parse("Sat May 23, 1981", DATE_FORMATTER), new Detail("Atlas-SLV3D Centaur-D1AR", "Intelsat V -501"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS),
        new Mission("2404", "RVSN USSR", "Site 43/3, Plesetsk Cosmodrome, Russia", LocalDate.parse("Tue May 19, 1981", DATE_FORMATTER), new Detail("Vostok-2M", "Cosmos 1271"), RocketStatus.STATUS_RETIRED,Optional.empty(), MissionStatus.SUCCESS)
    );
}
