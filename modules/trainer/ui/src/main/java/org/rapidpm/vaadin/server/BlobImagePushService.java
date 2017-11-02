package org.rapidpm.vaadin.server;

import static org.rapidpm.vaadin.trainer.modules.mainview.dashboard.DashboardComponent.nextImageName;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.vaadin.shared.Registration;

/**
 *
 */
public class BlobImagePushService {

  //TODO JVM static
  private static final Set<ImagePushListener> registry = ConcurrentHashMap.newKeySet();

  private static final Timer timer = new Timer(true);

  public interface ImagePushListener {
    void updateImage(final String imageID);
  }


  public static Registration register(ImagePushListener imagePushListener) {

    registry.add(imagePushListener);

    return () -> {
      registry.remove(imagePushListener);
      Logger.getAnonymousLogger().info("removed registration");
    };
  }

  //TODO run every 5 sec -> Timer
  public static void updateImages() {
    // not nice coupled
    registry.forEach(e -> e.updateImage(nextImageName().get()));
  }

  // TODO not nice
  static {
    timer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
            BlobImagePushService.updateImages();
          }
        } ,
        5_000 ,
        5_000);
  }
}
