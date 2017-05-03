package it.pwned.telegram.samplebot.reddit;

public class SubredditInfo {

    public final String name;
    public final String url;
    public final boolean nsfw;

    public SubredditInfo(String name, String url, boolean nsfw) {
        this.name = name;
        this.url = url;
        this.nsfw = nsfw;
    }

}
