package it.pwned.telegram.samplebot.handler;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import it.pwned.telegram.bot.api.type.Update;
import it.pwned.telegram.bot.api.type.User;
import it.pwned.telegram.bot.handler.UpdateHandler;

public class UserDataHandler implements UpdateHandler, Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(UserDataHandler.class);

	private final JdbcTemplate jdbc;
	private final BlockingQueue<User> userQueue;

	public UserDataHandler(JdbcTemplate jdbc, BlockingQueue<User> userQueue) {
		this.jdbc = jdbc;
		this.userQueue = userQueue;
	}

	@Override
	public boolean submit(Update u) {

		if (Update.Util.isMessage(u))
			userQueue.add(u.message.from);

		if (Update.Util.hasInlineQuery(u))
			userQueue.add(u.inlineQuery.from);

		if (Update.Util.hasInlineResult(u))
			userQueue.add(u.chosenInlineResult.from);

		if (Update.Util.hasCallbackQuery(u))
			userQueue.add(u.callbackQuery.from);

		return true;
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
		return "UserDataHandler";
	}

	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {

			try {
				final User u = userQueue.take();

				int count;

				count = jdbc.update("UPDATE PUBLIC.USER SET FIRST_NAME = ?, LAST_NAME = ?, USERNAME = ? WHERE USER_ID = ? ;",
						new Object[] { u.firstName, u.lastName, u.username, u.id });

				if (count <= 0) {
					
					jdbc.update("INSERT INTO PUBLIC.USER ( USER_ID, FIRST_NAME, LAST_NAME, USERNAME ) VALUES ( ?, ?, ?, ? );",
							new Object[] { u.id, u.firstName, u.lastName, u.username });
				}
				
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (DataAccessException de) {
				log.error("Could not save user data");
			}

		}

	}

}
