package it.pwned.telegram.samplebot.handler;

import it.pwned.telegram.bot.api.ApiClient;
import it.pwned.telegram.bot.api.type.Update;
import it.pwned.telegram.bot.handler.UpdateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.BlockingQueue;

public class ImageHandler implements UpdateHandler, Runnable {

    private static final Logger log = LoggerFactory.getLogger(ImageHandler.class);

    private final ApiClient client;
    private final BlockingQueue<Update> updateQueue;
    private final JdbcTemplate jdbc;

    public ImageHandler(ApiClient client, BlockingQueue<Update> updateQueue, JdbcTemplate jdbc) {
        this.client = client;
        this.updateQueue = updateQueue;
        this.jdbc = jdbc;
    }

    @Override
    public boolean submit(Update update) {
        if (Update.Util.isInline(update))
            updateQueue.add(update);

        return false;
    }

    @Override
    public boolean requiresThread() {
        return true;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public String getName() {
        return "ImageHandler";
    }

    @Override
    public void run() {

    }
}
