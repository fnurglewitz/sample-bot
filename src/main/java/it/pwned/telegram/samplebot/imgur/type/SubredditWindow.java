package it.pwned.telegram.samplebot.imgur.type;

public enum SubredditWindow {
    DAY("day"), WEEK("week"), MONTH("month"), YEAR("year"), ALL("all");

    private final String value;

    SubredditWindow(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
