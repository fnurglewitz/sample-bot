package it.pwned.telegram.samplebot.image;

import it.pwned.telegram.samplebot.image.type.SectionInfo;
import it.pwned.telegram.samplebot.imgur.ImgurClient;
import it.pwned.telegram.samplebot.imgur.type.GalleryImage;
import it.pwned.telegram.samplebot.imgur.type.GalleryImageRowMapper;
import it.pwned.telegram.samplebot.imgur.type.SubredditSort;
import it.pwned.telegram.samplebot.imgur.type.SubredditWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Random;

public class ImageSource {

    private static final Logger log = LoggerFactory.getLogger(ImageSource.class);

    private final ImgurClient imgur;
    private final JdbcTemplate jdbc;
    private final Random rnd;

    public ImageSource(ImgurClient imgur, JdbcTemplate jdbc) {
        this.imgur = imgur;
        this.jdbc = jdbc;
        this.rnd = new Random();
    }

    public List<SectionInfo> getSectionsInfo() {
        List<SectionInfo> sections = jdbc.query("SELECT I.SECTION ,(SELECT COUNT(*) FROM PUBLIC.IMAGE I2 WHERE I2.SECTION = I.SECTION AND I2.BOT_SCORE = 0) FROM PUBLIC.IMAGE I GROUP BY I.SECTION;", (rs,i) -> new SectionInfo(rs.getString(1), rs.getInt(2)) );
        return sections;
    }

    public List<GalleryImage> getImagesBySection(String section, int n) {

        jdbc.query("SELECT COUNT(*) FROM PUBLIC.IMAGE WHERE SECTION = ?;", new Object[] {section}
        , (rs) -> {
            final int count = rs.getInt(1);
            if(count<=0) {
                refreshSectionImages(section);
            }
        });

        List<GalleryImage> images = jdbc.query(
                "SELECT TOP ? * FROM PUBLIC.IMAGE WHERE SECTION= ? ORDER BY BOT_SCORE ASC, RAND()",
                new Object[] {n,section},
                new GalleryImageRowMapper());

        return images;
    }

    public void refreshSectionImages(String section) {

        final int randomPage = rnd.nextInt(49)+1;

        List<GalleryImage> imgs = imgur.subredditGallery(section, SubredditSort.TIME, randomPage, SubredditWindow.ALL);

        // TODO: find a way to understand how many pages a subreddit has
        if(imgs.size() == 0)
            imgs = imgur.subredditGallery(section, SubredditSort.TIME, randomPage, SubredditWindow.ALL);

        for(GalleryImage img : imgs) {

            jdbc.update("MERGE INTO PUBLIC.IMAGE (ID,TITLE,DESCRIPTION,DATETIME,TYPE,ANIMATED,WIDTH,HEIGHT,SIZE,VIEWS,BANDWIDTH,DELETEHASH,LINK,GIFV,MP4,MP4_SIZE,LOOPING,VOTE,FAVORITE,NSFW,COMMENT_COUNT,TOPIC,TOPIC_ID,SECTION,ACCOUNT_URL,ACCOUNT_ID,UPS,DOWNS,POINTS,SCORE,IS_ALBUM,IN_MOST_VIRAL) KEY ( ID ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? );",
                    new Object[] {
                        img.id, img.title, img.description, img.datetime, img.type, img.animated, img.width, img.height, img.size, img.views, img.bandwidth,
                        img.deletehash, img.link, img.gifv, img.mp4, img.mp4Size, img.looping, img.vote, img.favorite, img.nsfw, img.commentCount, img.topic,
                        img.topicId, img.section, img.accountUrl, img.accountId, img.ups, img.downs, img.points, img.score, img.isAlbum, img.inMostViral
                    });

        }

    }

}
