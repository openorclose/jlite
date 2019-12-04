package common;

public class Location {

    private final int line;
    private final int col;

    public Location(int line, int col) {
        this.line = line + 1;
        this.col = col + 1;
    }

    @Override
    public String toString() {
        return "Line: " + line + ", Col: " + col;
    }
}
