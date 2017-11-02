package org.rapidpm.vaadin.trainer.backend.security.login;

import static java.time.Duration.between;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;


import org.rapidpm.frp.model.Pair;
import org.rapidpm.vaadin.trainer.api.security.login.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class LoginServiceInMemory implements LoginService {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceInMemory.class);

  private static final Map<String, Pair<LocalDateTime, Integer>> failedLogins = new ConcurrentHashMap<>();
  public static final int MAX_FAILED_LOGINS = 3;
  public static final int MINUTES_TO_WAIT = 1;
  public static final int MINUTES_TO_BE_CLEANED = 2;
  public static final int MILLISECONDS_TO_BE_CLEANED = 1_000 * 60 * MINUTES_TO_BE_CLEANED;

  public static final int MILLISECONDS_INITIAL_DELAY = 100;


  public static class FailedLoginCleaner {
    private final Timer failedLoginCleanUpTimer = new Timer();

    public FailedLoginCleaner(TimerTask tasknew) {
      failedLoginCleanUpTimer.schedule(tasknew , MILLISECONDS_INITIAL_DELAY , MILLISECONDS_TO_BE_CLEANED);
    }
  }

  private static final LoginServiceInMemory.FailedLoginCleaner FAILED_LOGIN_CLEANER = new LoginServiceInMemory.FailedLoginCleaner(new TimerTask() {
    @Override
    public void run() {
      LOGGER.debug(" start cleaning " + LocalDateTime.now());
      failedLogins
          .keySet()
          .forEach((String key) -> {
            Pair<LocalDateTime, Integer> pair = failedLogins.get(key);
            if (pair != null) {
              LOGGER.debug("work on login/pair = " + key + " - " + pair);
              final Duration duration = between(pair.getT1() , LocalDateTime.now());
              long minutes = duration.toMinutes();
              if (minutes > MINUTES_TO_BE_CLEANED) {
                failedLogins.remove(key); // start from zero
                LOGGER.debug("  ==>  cleaned key = " + key);
              }
            }
          });
    }
  });


  @Override
  public boolean check(String login , String password) {
    if (failedLogins.containsKey(login)) {
      Pair<LocalDateTime, Integer> pair = failedLogins.get(login);
      LocalDateTime failedLoginDate = pair.getT1();
      Integer failedLoginCount = pair.getT2();
      if (failedLoginCount > MAX_FAILED_LOGINS) {
        LOGGER.debug("failedLoginCount > MAX_FAILED_LOGINS " + failedLoginCount);
        final Duration duration = between(failedLoginDate , LocalDateTime.now());
        long minutes = duration.toMinutes();
        if (minutes > MINUTES_TO_WAIT) {
          LOGGER.debug("minutes > MINUTES_TO_WAIT (remove login) " + failedLoginCount);
          failedLogins.remove(login); // start from zero
        } else {
          LOGGER.debug("failedLoginCount <= MAX_FAILED_LOGINS " + failedLoginCount);
          failedLogins.compute(
              login ,
              (s , faildPair) -> new Pair<>(LocalDateTime.now() , failedLoginCount + 1));
          return false;
        }
      } else {
        LOGGER.debug("failedLoginCount => " + login + " - " + failedLoginCount);
      }
    }


    //TODO Demo InMemory
    if(login != null && password != null && checkUserPassword(login, password)){
      failedLogins.remove(login);
      return true;
    } else {
      LOGGER.debug("login failed " + login);
      failedLogins.putIfAbsent(login , new Pair<>(LocalDateTime.now() , 0));
      failedLogins.compute(
          login ,
          (s , oldPair) -> new Pair<>(LocalDateTime.now() , oldPair.getT2() + 1));
      return false;
    }
  }

  private boolean checkUserPassword(String login , String password) {
    if (login.equals("sven") && password.equals("sven")) return true;
    if (login.equals("admin") && password.equals("admin")) return true;
    return false;
  }
}
