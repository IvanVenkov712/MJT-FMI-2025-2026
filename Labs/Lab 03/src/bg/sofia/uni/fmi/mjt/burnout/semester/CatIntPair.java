package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.subject.Category;

public class CatIntPair {
    public CatIntPair(Category category, int nOccurences) {
        this.category = category;
        this.nOccurences = nOccurences;
    }

    public CatIntPair(Category category) {
        this(category, 0);
    }

    public Category getCategory() {
        return category;
    }

    public int getOccurences() {
        return nOccurences;
    }

    public void increaseOccurs() {
        ++nOccurences;
    }

    private final Category category;
    private int nOccurences;
}
