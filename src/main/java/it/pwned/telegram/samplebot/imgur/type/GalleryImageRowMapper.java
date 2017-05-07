package it.pwned.telegram.samplebot.imgur.type;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GalleryImageRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        GalleryImage gi = new GalleryImage(
                rs.getString("ID"),
                rs.getString("TITLE"),
                rs.getString("DESCRIPTION"),
                rs.getLong("DATETIME"),
                rs.getString("TYPE"),
                rs.getBoolean("ANIMATED"),
                rs.getInt("WIDTH"),
                rs.getInt("HEIGHT"),
                rs.getInt("SIZE"),
                rs.getInt("VIEWS"),
                rs.getLong("BANDWIDTH"),
                rs.getString("DELETEHASH"),
                rs.getString("LINK"),
                rs.getString("GIFV"),
                rs.getString("MP4"),
                rs.getInt("MP4_SIZE"),
                rs.getBoolean("LOOPING"),
                rs.getString("VOTE"),
                rs.getBoolean("FAVORITE"),
                rs.getBoolean("NSFW"),
                rs.getInt("COMMENT_COUNT"),
                rs.getString("TOPIC"),
                rs.getLong("TOPIC_ID"),
                rs.getString("SECTION"),
                rs.getString("ACCOUNT_URL"),
                rs.getLong("ACCOUNT_ID"),
                rs.getInt("UPS"),
                rs.getInt("DOWNS"),
                rs.getInt("POINTS"),
                rs.getInt("SCORE"),
                rs.getBoolean("IS_ALBUM"),
                rs.getBoolean("IN_MOST_VIRAL")
        );

        return gi;
    }
}
