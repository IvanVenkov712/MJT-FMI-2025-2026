package bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators;

import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;

import java.util.Comparator;

public class JobPostingBySalaryComparator implements Comparator<JobPosting> {
    @Override
    public int compare(JobPosting o1, JobPosting o2) {
        return MathUtils.compareDouble(o1.getSalary(), o2.getSalary());
    }
}
