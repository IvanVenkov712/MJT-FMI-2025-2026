package bg.sofia.uni.fmi.mjt.burnout.subject;

public enum Category {
    MATH(0.2, 0),
    PROGRAMMING(0.1, 1),
    THEORY(0.15, 2),
    PRACTICAL(0.05, 3);

    public double getCoef() {
        return coef;
    }

    public int getIndex() {
        return index;
    }

    Category(double coef, int index) {
        this.coef = coef;
        this.index = index;
    }

    private final double coef;
    private final int index;

}