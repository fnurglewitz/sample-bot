package it.pwned.telegram.samplebot.worker;

import it.pwned.telegram.samplebot.worker.type.ScoreVariation;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.BlockingQueue;

public class ScoreKeeper implements Runnable {

    private final JdbcTemplate jdbc;
    private final BlockingQueue<ScoreVariation> variationQueue;

    public ScoreKeeper(JdbcTemplate jdbc, BlockingQueue<ScoreVariation> variationQueue) {
        this.jdbc = jdbc;
        this.variationQueue = variationQueue;
    }

    public void submit(ScoreVariation v) {
        variationQueue.add(v);
    }

    @Override
    public void run() {

        while(!Thread.currentThread().isInterrupted()) {

            try {
                final ScoreVariation var = variationQueue.take();
                jdbc.update("UPDATE PUBLIC.IMAGE SET BOT_SCORE = BOT_SCORE + (?) WHERE ID = ? ;", new Object[] { var.variation, var.imageId });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
