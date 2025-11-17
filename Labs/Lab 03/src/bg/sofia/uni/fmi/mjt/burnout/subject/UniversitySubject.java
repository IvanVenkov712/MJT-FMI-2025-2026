package bg.sofia.uni.fmi.mjt.burnout.subject;

/**
 * @param name            the name of the subject
 * @param credits         number of credit hours for this subject
 * @param rating          difficulty rating of the subject (1-5)
 * @param category        the academic category this subject belongs to
 * @param neededStudyTime estimated study time in days required for this subject
 * @throws IllegalArgumentException if the name of the subject is null or blank
 * @throws IllegalArgumentException if the credits are not positive
 * @throws IllegalArgumentException if the rating is not in its bounds
 * @throws IllegalArgumentException if the Category is null
 * @throws IllegalArgumentException if the neededStudy time is not positive
 */
public record UniversitySubject(String name, int credits, int rating, Category category, int neededStudyTime) {
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;

    public UniversitySubject {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Null or empty name");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("not positive credits");
        }
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException("rating is not in bounds");
        }
        if (category == null) {
            throw new IllegalArgumentException("category is null");
        }
        if (neededStudyTime <= 0) {
            throw new IllegalArgumentException("neededStudyTime is not positive");
        }
    }

    public int getRestTime() {
        return (int) Math.ceil(neededStudyTime * category.getCoef());
    }
}
