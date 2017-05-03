package it.pwned.telegram.samplebot.imgur.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class GalleryImage {

    private final static String JSON_FIELD_ID = "id";
    private final static String JSON_FIELD_TITLE = "title";
    private final static String JSON_FIELD_DESCRIPTION = "description";
    private final static String JSON_FIELD_DATETIME = "datetime";
    private final static String JSON_FIELD_TYPE = "type";
    private final static String JSON_FIELD_ANIMATED = "animated";
    private final static String JSON_FIELD_WIDTH = "width";
    private final static String JSON_FIELD_HEIGHT = "height";
    private final static String JSON_FIELD_SIZE = "size";
    private final static String JSON_FIELD_VIEWS = "views";
    private final static String JSON_FIELD_BANDWIDTH = "bandwidth";
    private final static String JSON_FIELD_DELETEHASH = "deletehash";
    private final static String JSON_FIELD_LINK = "link";
    private final static String JSON_FIELD_GIFV = "gifv";
    private final static String JSON_FIELD_MP4 = "mp4";
    private final static String JSON_FIELD_MP4_SIZE = "mp4_size";
    private final static String JSON_FIELD_LOOPING = "looping";
    private final static String JSON_FIELD_VOTE = "vote";
    private final static String JSON_FIELD_FAVORITE = "favorite";
    private final static String JSON_FIELD_NSFW = "nsfw";
    private final static String JSON_FIELD_COMMENT_COUNT = "comment_count";
    private final static String JSON_FIELD_TOPIC = "topic";
    private final static String JSON_FIELD_TOPIC_ID = "topic_id";
    private final static String JSON_FIELD_SECTION = "section";
    private final static String JSON_FIELD_ACCOUNT_URL = "account_url";
    private final static String JSON_FIELD_ACCOUNT_ID = "account_id";
    private final static String JSON_FIELD_UPS = "ups";
    private final static String JSON_FIELD_DOWNS = "downs";
    private final static String JSON_FIELD_POINTS = "points";
    private final static String JSON_FIELD_SCORE = "score";
    private final static String JSON_FIELD_IS_ALBUM = "is_album";
    private final static String JSON_FIELD_IN_MOST_VIRAL = "in_most_viral";

    @JsonProperty(JSON_FIELD_ID)
    public final String id;

    @JsonProperty(JSON_FIELD_TITLE)
    public final String title;

    @JsonProperty(JSON_FIELD_DESCRIPTION)
    public final String description;

    @JsonProperty(JSON_FIELD_DATETIME)
    public final Long datetime;

    @JsonProperty(JSON_FIELD_TYPE)
    public final String type;

    @JsonProperty(JSON_FIELD_ANIMATED)
    public final Boolean animated;

    @JsonProperty(JSON_FIELD_WIDTH)
    public final Integer width;

    @JsonProperty(JSON_FIELD_HEIGHT)
    public final Integer height;

    @JsonProperty(JSON_FIELD_SIZE)
    public final Integer size;

    @JsonProperty(JSON_FIELD_VIEWS)
    public final Integer views;

    @JsonProperty(JSON_FIELD_BANDWIDTH)
    public final Long bandwidth;

    @JsonProperty(JSON_FIELD_DELETEHASH)
    public final String deletehash;

    @JsonProperty(JSON_FIELD_LINK)
    public final String link;

    @JsonProperty(JSON_FIELD_GIFV)
    public final String gifv;

    @JsonProperty(JSON_FIELD_MP4)
    public final String mp4;

    @JsonProperty(JSON_FIELD_MP4_SIZE)
    public final Integer mp4Size;

    @JsonProperty(JSON_FIELD_LOOPING)
    public final Boolean looping;

    @JsonProperty(JSON_FIELD_VOTE)
    public final String vote;

    @JsonProperty(JSON_FIELD_FAVORITE)
    public final Boolean favorite;

    @JsonProperty(JSON_FIELD_NSFW)
    public final Boolean nsfw;

    @JsonProperty(JSON_FIELD_COMMENT_COUNT)
    public final Integer commentCount;

    @JsonProperty(JSON_FIELD_TOPIC)
    public final String topic;

    @JsonProperty(JSON_FIELD_TOPIC_ID)
    public final Long topicId;

    @JsonProperty(JSON_FIELD_SECTION)
    public final String section;

    @JsonProperty(JSON_FIELD_ACCOUNT_URL)
    public final String accountUrl;

    @JsonProperty(JSON_FIELD_ACCOUNT_ID)
    public final Long accountId;

    @JsonProperty(JSON_FIELD_UPS)
    public final Integer ups;

    @JsonProperty(JSON_FIELD_DOWNS)
    public final Integer downs;

    @JsonProperty(JSON_FIELD_POINTS)
    public final Integer points;

    @JsonProperty(JSON_FIELD_SCORE)
    public final Integer score;

    @JsonProperty(JSON_FIELD_IS_ALBUM)
    public final Boolean isAlbum;

    @JsonProperty(JSON_FIELD_IN_MOST_VIRAL)
    public final Boolean inMostViral;

    public GalleryImage(@JsonProperty(JSON_FIELD_ID) String id,
                        @JsonProperty(JSON_FIELD_TITLE) String title,
                        @JsonProperty(JSON_FIELD_DESCRIPTION) String description,
                        @JsonProperty(JSON_FIELD_DATETIME) Long datetime,
                        @JsonProperty(JSON_FIELD_TYPE) String type,
                        @JsonProperty(JSON_FIELD_ANIMATED) Boolean animated,
                        @JsonProperty(JSON_FIELD_WIDTH) Integer width,
                        @JsonProperty(JSON_FIELD_HEIGHT) Integer height,
                        @JsonProperty(JSON_FIELD_SIZE) Integer size,
                        @JsonProperty(JSON_FIELD_VIEWS) Integer views,
                        @JsonProperty(JSON_FIELD_BANDWIDTH) Long bandwidth,
                        @JsonProperty(JSON_FIELD_DELETEHASH) String deletehash,
                        @JsonProperty(JSON_FIELD_LINK) String link,
                        @JsonProperty(JSON_FIELD_GIFV) String gifv,
                        @JsonProperty(JSON_FIELD_MP4) String mp4,
                        @JsonProperty(JSON_FIELD_MP4_SIZE) Integer mp4Size,
                        @JsonProperty(JSON_FIELD_LOOPING) Boolean looping,
                        @JsonProperty(JSON_FIELD_VOTE) String vote,
                        @JsonProperty(JSON_FIELD_FAVORITE) Boolean favorite,
                        @JsonProperty(JSON_FIELD_NSFW) Boolean nsfw,
                        @JsonProperty(JSON_FIELD_COMMENT_COUNT) Integer commentCount,
                        @JsonProperty(JSON_FIELD_TOPIC) String topic,
                        @JsonProperty(JSON_FIELD_TOPIC_ID) Long topicId,
                        @JsonProperty(JSON_FIELD_SECTION) String section,
                        @JsonProperty(JSON_FIELD_ACCOUNT_URL) String accountUrl,
                        @JsonProperty(JSON_FIELD_ACCOUNT_ID) Long accountId,
                        @JsonProperty(JSON_FIELD_UPS) Integer ups,
                        @JsonProperty(JSON_FIELD_DOWNS) Integer downs,
                        @JsonProperty(JSON_FIELD_POINTS) Integer points,
                        @JsonProperty(JSON_FIELD_SCORE) Integer score,
                        @JsonProperty(JSON_FIELD_IS_ALBUM) Boolean isAlbum,
                        @JsonProperty(JSON_FIELD_IN_MOST_VIRAL) Boolean inMostViral) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        this.type = type;
        this.animated = animated;
        this.width = width;
        this.height = height;
        this.size = size;
        this.views = views;
        this.bandwidth = bandwidth;
        this.deletehash = deletehash;
        this.link = link;
        this.gifv = gifv;
        this.mp4 = mp4;
        this.mp4Size = mp4Size;
        this.looping = looping;
        this.vote = vote;
        this.favorite = favorite;
        this.nsfw = nsfw;
        this.commentCount = commentCount;
        this.topic = topic;
        this.topicId = topicId;
        this.section = section;
        this.accountUrl = accountUrl;
        this.accountId = accountId;
        this.ups = ups;
        this.downs = downs;
        this.points = points;
        this.score = score;
        this.isAlbum = isAlbum;
        this.inMostViral = inMostViral;

    }

}
