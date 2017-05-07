package it.pwned.telegram.samplebot.config;

import java.util.concurrent.LinkedBlockingQueue;

import it.pwned.telegram.samplebot.handler.ImageHandler;
import it.pwned.telegram.samplebot.image.ImageSource;
import it.pwned.telegram.samplebot.imgur.ImgurClient;
import it.pwned.telegram.samplebot.reddit.RedditClient;
import it.pwned.telegram.samplebot.worker.ImageRefresher;
import it.pwned.telegram.samplebot.worker.ScoreKeeper;
import it.pwned.telegram.samplebot.worker.type.ScoreVariation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import it.pwned.telegram.bot.api.ApiClient;
import it.pwned.telegram.bot.api.type.Update;
import it.pwned.telegram.bot.api.type.User;
import it.pwned.telegram.bot.handler.InlineHandler;
import it.pwned.telegram.bot.handler.UpdateHandler;
import it.pwned.telegram.samplebot.handler.UserDataHandler;
import it.pwned.telegram.samplebot.trivia.TriviaHandler;
import it.pwned.telegram.samplebot.trivia.TriviaHelpHandler;
import it.pwned.telegram.samplebot.trivia.api.OpenTdbApi;
import it.pwned.telegram.samplebot.trivia.api.OpenTdbRestApi;

@Configuration
@EnableScheduling
public class HandlerConfig {

	@Bean
	@Order(value = 1)
	public UpdateHandler userDataHandler(JdbcTemplate jdbc) {
		return new UserDataHandler(jdbc, new LinkedBlockingQueue<User>());
	}

	@Bean
	public ImgurClient imgur(@Value("${imgur.client-id}") String clientId, RestTemplate rest) {
		return new ImgurClient(clientId, rest);
	}

	@Bean
	public RedditClient reddit() {
		return new RedditClient();
	}

	@Bean
	public ImageSource imageSource(ImgurClient imgurClient, JdbcTemplate jdbc) {
		return new ImageSource(imgurClient,jdbc);
	}

	@Bean
	public ScoreKeeper scoreKeeper(JdbcTemplate jdbc) {
		LinkedBlockingQueue<ScoreVariation> variationQueue = new LinkedBlockingQueue<>();
		return new ScoreKeeper(jdbc,variationQueue);
	}

	@Bean
	public ImageRefresher refresher(ImageSource imageSource) {
		return new ImageRefresher(imageSource);
	}

	@InlineHandler
	public UpdateHandler imageHandler(ApiClient client, RedditClient reddit, ImageSource imageSource, ScoreKeeper scoreKeeper, ThreadPoolTaskExecutor executor) {
		LinkedBlockingQueue<Update> updateQueue = new LinkedBlockingQueue<Update>();
		ImageHandler handler = new ImageHandler(client, updateQueue, reddit, imageSource, scoreKeeper, executor);
		return handler;
	}

}
