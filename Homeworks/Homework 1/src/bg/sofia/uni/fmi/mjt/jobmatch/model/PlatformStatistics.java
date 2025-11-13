package bg.sofia.uni.fmi.mjt.jobmatch.model;

import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validate;

public record PlatformStatistics(int totalCandidates, int totalEmployers, int totalJobPostings,
                                 String mostCommonSkillName, String highestPaidJobTitle) {
    public PlatformStatistics {
        validate(totalCandidates, "totalCandidates");
        validate(totalEmployers, "totalEmployees");
        validate(totalJobPostings, "totalJobPostings");
        validate(mostCommonSkillName, "mostCommonSkillName");
        validate(highestPaidJobTitle, "highestPaidJobTitle");
    }
}
