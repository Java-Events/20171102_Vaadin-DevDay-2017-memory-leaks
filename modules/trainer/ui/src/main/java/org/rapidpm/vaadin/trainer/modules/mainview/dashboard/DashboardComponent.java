package org.rapidpm.vaadin.trainer.modules.mainview.dashboard;

import static org.rapidpm.ddi.DI.activateDI;
import static org.rapidpm.vaadin.server.BlobImagePushService.register;
import static org.rapidpm.vaadin.trainer.backend.binaryblob.image.ImageFunctions.randomImageID;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.rapidpm.vaadin.generic.resource.ImageSource;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Image;

/**
 *
 */
public class DashboardComponent extends Composite {

  public static Supplier<String> nextImageName() {
    return () -> "nasa_pic_" + randomImageID().apply(100) + ".jpg";
  }




  private Function<String, Image> createImage() {
    return (imageID) -> {
      final Image image = new Image(null , createImageResource().apply(imageID));
      image.markAsDirty(); // for refresh
      return image;
    };
  }

  private Function<String, StreamResource> createImageResource() {
    return (imageID) -> {
      final StreamResource streamResource = new StreamResource(activateDI(new ImageSource(imageID)) , imageID);
      streamResource.setCacheTime(0);
      streamResource.setFilename(imageID + "." + System.nanoTime());
      return streamResource;
    };
  }

  private Image image;
  private Registration registration;

  @PostConstruct
  private void postConstruct() {
    image = createImage().apply(nextImageName().get());

    registration = register(imgID -> image.getUI()
                                          .access(() -> {
                                            System.out.println("DashboardComponent - imgID = " + imgID);
                                            final StreamResource apply = createImageResource().apply(imgID);
                                            image.setSource(apply);
                                          }));

    setCompositionRoot(image);
  }


  //TODO to avoid Memory Leaks
  @Override
  public void detach() {
    registration.remove();
    super.detach();
  }
}