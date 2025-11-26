package bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators;

import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;

import java.util.Comparator;

public class JobPostingByTitleComparator implements Comparator<JobPosting> {
    @Override
    public int compare(JobPosting o1, JobPosting o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
