package org.rapidpm.vaadin.trainer.backend.binaryblob.image;

import static java.lang.String.format;
import static java.util.concurrent.ThreadLocalRandom.current;

import java.util.function.Function;

/**
 *
 */
public interface ImageFunctions {


  public static Function<Integer, String> randomImageID() {
    return (boundary) -> format("%05d" , current().nextInt(boundary) + 1);
  }

}
