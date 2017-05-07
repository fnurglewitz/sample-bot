package it.pwned.telegram.samplebot.handler;

import it.pwned.telegram.bot.api.ApiClient;
import it.pwned.telegram.bot.api.method.inline.AnswerInlineQuery;
import it.pwned.telegram.bot.api.type.CallbackQuery;
import it.pwned.telegram.bot.api.type.TelegramBotApiException;
import it.pwned.telegram.bot.api.type.Update;
import it.pwned.telegram.bot.api.type.inline.*;
import it.pwned.telegram.bot.handler.UpdateHandler;
import it.pwned.telegram.samplebot.image.ImageSource;
import it.pwned.telegram.samplebot.imgur.type.GalleryImage;
import it.pwned.telegram.samplebot.reddit.RedditClient;
import it.pwned.telegram.samplebot.reddit.SubredditInfo;
import it.pwned.telegram.samplebot.worker.ScoreKeeper;
import it.pwned.telegram.samplebot.worker.type.ScoreVariation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class ImageHandler implements UpdateHandler, Runnable {

    private static final Logger log = LoggerFactory.getLogger(ImageHandler.class);

    private final ApiClient client;
    private final BlockingQueue<Update> updateQueue;
    private final RedditClient reddit;
    private final ImageSource imageSource;
    private final ScoreKeeper scoreKeeper;
    private final ThreadPoolTaskExecutor executor;

    public ImageHandler(ApiClient client, BlockingQueue<Update> updateQueue, RedditClient reddit, ImageSource imageSource, ScoreKeeper scoreKeeper, ThreadPoolTaskExecutor executor) {
        this.client = client;
        this.updateQueue = updateQueue;
        this.reddit = reddit;
        this.imageSource = imageSource;
        this.scoreKeeper = scoreKeeper;
        this.executor = executor;
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

        Thread tScoreKeeper = new Thread(scoreKeeper);

        tScoreKeeper.start();

        while (!Thread.currentThread().isInterrupted()) {

            try {
                final Update u = updateQueue.take();

                if (Update.Util.hasInlineQuery(u))
                    handleInlineQuery(u.inlineQuery);

                if (Update.Util.hasInlineResult(u))
                    handleChosenResult(u.chosenInlineResult);

                if (Update.Util.hasCallbackQuery(u))
                    handleCallbackQuery(u.callbackQuery);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                tScoreKeeper.interrupt();
            }
        }

    }

    private void handleInlineQuery(InlineQuery query) {

        executor.execute(() -> {

            final Optional<SubredditInfo> maybeSubreddit = reddit.getSubredditInfo(query.query);

            if (maybeSubreddit.isPresent()) {

                final SubredditInfo info = maybeSubreddit.get();

                List<GalleryImage> images = imageSource.getImagesBySection(info.name, 10);

                Collections.shuffle(images);
                List<InlineQueryResult> results = images.stream().map(ImageHandler::convertGalleryImageToInlineResult).collect(Collectors.toList());

                try {
                    client.call(new AnswerInlineQuery(query.id, results, 10, null, null, null, null));
                } catch (TelegramBotApiException e) {
                    return;
                }

                for (GalleryImage img : images) {
                    scoreKeeper.submit(new ScoreVariation(img.id, 1));
                }
            }

        });
    }

    private void handleChosenResult(ChosenInlineResult chosenInlineResult) {
        scoreKeeper.submit(new ScoreVariation(chosenInlineResult.resultId, 1));
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {

    }

    private static InlineQueryResult convertGalleryImageToInlineResult(GalleryImage image) {

        InlineQueryResult result;

        if(!image.animated) {

            result = new InlineQueryResultPhoto(
                    image.id,
                    image.link,
                    image.link,
                    image.width,
                    image.height,
                    image.title,
                    image.description,
                    image.title,
                    null,
                    null
            );

        } else {

            result = new InlineQueryResultMpeg4Gif(
                    image.id,
                    image.mp4,
                    image.width,
                    image.height,
                    image.link,
                    image.title,
                    image.title,
                    null,
                    null
            );

        }

        return result;
    }
}
