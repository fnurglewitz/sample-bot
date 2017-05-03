package it.pwned.telegram.samplebot.worker;

import it.pwned.telegram.samplebot.imgur.ImgurClient;
import it.pwned.telegram.samplebot.imgur.type.GalleryImage;
import it.pwned.telegram.samplebot.imgur.type.SubredditSort;
import it.pwned.telegram.samplebot.imgur.type.SubredditWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Random;

public class ImageRefresher {

    private static final Logger log = LoggerFactory.getLogger(ImageRefresher.class);

    private final ImgurClient imgur;
    private final JdbcTemplate jdbc;
    private final Random rnd;

    public ImageRefresher(ImgurClient imgur, JdbcTemplate jdbc) {
        this.imgur = imgur;
        this.jdbc = jdbc;
        this.rnd = new Random();
    }

    public void refreshAll() {

        jdbc.query("SELECT DISTINCT SUBREDDIT FROM PUBLIC.IMAGE;", (rs) -> {

            final String subreddit = rs.getString(1);

            refreshSubredditImages(subreddit);

        });

    }

    public void refreshSubredditImages(String subreddit) {

        final int randomPage = rnd.nextInt(49)+1;

        List<GalleryImage> imgs = imgur.subredditGallery(subreddit, SubredditSort.TIME, randomPage, SubredditWindow.ALL);

        for(GalleryImage img : imgs) {

            jdbc.update("MERGE INTO PUBLIC.IMAGE VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? );",
                    new Object[] {
                        img.id, img.title, img.description, img.datetime, img.type, img.animated, img.width, img.height, img.size, img.views, img.bandwidth,
                        img.deletehash, img.link, img.gifv, img.mp4, img.mp4Size, img.looping, img.vote, img.favorite, img.nsfw, img.commentCount, img.topic,
                        img.topicId, img.section, img.accountUrl, img.accountId, img.ups, img.downs, img.points, img.score, img.isAlbum, img.inMostViral, subreddit,
                        0
                    });

        }

    }

}
