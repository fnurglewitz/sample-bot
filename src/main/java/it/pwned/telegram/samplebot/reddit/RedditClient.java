package it.pwned.telegram.samplebot.reddit;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class RedditClient {

    private static final Logger log = LoggerFactory.getLogger(RedditClient.class);

    public RedditClient() {
    }

    public Optional<SubredditInfo> getSubredditInfo(String subreddit) {

        if (subreddit.length() < 3)
            return Optional.empty();

        log.trace("Checking existence of subreddit: " + subreddit);

        final String url = "https://www.reddit.com/r/" + subreddit;
        int statusCode;
        boolean nsfw = false;

        try {
            HttpClient client = HttpClientBuilder.create().setRedirectStrategy(new DefaultRedirectStrategy() {
                @Override
                protected boolean isRedirectable(String method) {
                    return false;
                }
            }).build();

            HttpHead method = new HttpHead(url);
            method.addHeader("User-agent", "Telegram Bot");
            HttpResponse response = client.execute(method);

            statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 302 && response.getHeaders("location")[0].getValue().startsWith("https://www.reddit.com/over18")) {
                statusCode = 200;
                nsfw = true;
            }


        } catch (IOException e1) {
            statusCode = -1;
        }

        if (statusCode == 200) {
            log.trace(String.format("Subreddit [%s] exists.",subreddit));
            return Optional.of(new SubredditInfo(subreddit, url, nsfw));
        }
        else {
            log.trace(String.format("Subreddit [%s] does NOT exists.",subreddit));
            return Optional.empty();
        }

    }



}
