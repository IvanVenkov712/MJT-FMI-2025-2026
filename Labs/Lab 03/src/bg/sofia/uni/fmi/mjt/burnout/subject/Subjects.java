package bg.sofia.uni.fmi.mjt.burnout.subject;

import bg.sofia.uni.fmi.mjt.burnout.subject.comparator.SubjectComparator;

import java.util.Arrays;

public class Subjects {

    public static void sort(UniversitySubject[] subjects, SubjectComparator comparator) {
        if (subjects == null || subjects.length <= 1) {
            return;
        }
        int mid = subjects.length / 2;
        UniversitySubject[] arr1 = Arrays.copyOfRange(subjects, 0, mid);
        UniversitySubject[] arr2 = Arrays.copyOfRange(subjects, mid, subjects.length);
        sort(arr1, comparator);
        sort(arr2, comparator);
        merge(subjects, arr1, arr2, comparator);
    }

    private static void merge(
        UniversitySubject[] dst, UniversitySubject[] src1, UniversitySubject[] src2, SubjectComparator comparator
    ) {
        int n1 = src1.length;
        int n2 = src2.length;
        int k = 0;
        int i1 = 0;
        int i2 = 0;
        while (i1 < n1 && i2 < n2) {
            if (comparator.compare(src1[i1], src2[i2]) <= 0) {
                dst[k] = src1[i1];
                ++i1;
            } else {
                dst[k] = src2[i2];
                ++i2;
            }
            ++k;
        }
        while (i1 < n1) {
            dst[k] = src1[i1];
            ++i1;
            ++k;
        }
        while (i2 < n2) {
            dst[k] = src2[i2];
            ++i2;
            ++k;
        }
    }

    /*
    public static UniversitySubject[] sortSubjectsByCreditsDesc(UniversitySubject[] subjects) {
        UniversitySubject[] res = Arrays.copyOf(subjects, subjects.length);
        sortHelperCredits(res);
        return res;
    }

    private static void sortHelperCredits(UniversitySubject[] subjects) {
        if (subjects == null || subjects.length <= 1) {
            return;
        }
        int mid = subjects.length / 2;
        UniversitySubject[] arr1 = Arrays.copyOfRange(subjects, 0, mid);
        UniversitySubject[] arr2 = Arrays.copyOfRange(subjects, mid, subjects.length);
        sortHelperCredits(arr1);
        sortHelperCredits(arr2);
        mergeCredits(subjects, arr1, arr2);
    }

    private static void mergeCredits(UniversitySubject[] dst, UniversitySubject[] src1, UniversitySubject[] src2) {
        int n1 = src1.length, n2 = src2.length;
        int k = 0, i1 = 0, i2 = 0;
        while (i1 < n1 && i2 < n2) {
            if (src1[i1].credits() >= src2[i2].credits()) {
                dst[k] = src1[i1];
                ++i1;
            } else {
                dst[k] = src2[i2];
                ++i2;
            }
            ++k;
        }
        while (i1 < n1) {
            dst[k] = src1[i1];
            ++i1;
            ++k;
        }
        while (i2 < n2) {
            dst[k] = src2[i2];
            ++i2;
            ++k;
        }
    }

    public static UniversitySubject[] sortSubjectsByRatingDesc(UniversitySubject[] subjects) {
        UniversitySubject[] res = Arrays.copyOf(subjects, subjects.length);
        sortHelperRating(res);
        return res;
    }

    private static void sortHelperRating(UniversitySubject[] subjects) {
        if (subjects == null || subjects.length <= 1) {
            return;
        }
        int mid = subjects.length / 2;
        UniversitySubject[] arr1 = Arrays.copyOfRange(subjects, 0, mid);
        UniversitySubject[] arr2 = Arrays.copyOfRange(subjects, mid, subjects.length);
        sortHelperRating(arr1);
        sortHelperRating(arr2);
        mergeRating(subjects, arr1, arr2);
    }

    private static void mergeRating(UniversitySubject[] dst, UniversitySubject[] src1, UniversitySubject[] src2) {
        int n1 = src1.length, n2 = src2.length;
        int k = 0, i1 = 0, i2 = 0;
        while (i1 < n1 && i2 < n2) {
            if (src1[i1].rating() >= src2[i2].rating()) {
                dst[k] = src1[i1];
                ++i1;
            } else {
                dst[k] = src2[i2];
                ++i2;
            }
            ++k;
        }
        while (i1 < n1) {
            dst[k] = src1[i1];
            ++i1;
            ++k;
        }
        while (i2 < n2) {
            dst[k] = src2[i2];
            ++i2;
            ++k;
        }
    }
    */
}
