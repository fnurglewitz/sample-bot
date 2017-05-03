package it.pwned.telegram.samplebot.imgur.type;

public enum SubredditSort {
    TIME("time"), TOP("top");

    private final String value;

    SubredditSort(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
