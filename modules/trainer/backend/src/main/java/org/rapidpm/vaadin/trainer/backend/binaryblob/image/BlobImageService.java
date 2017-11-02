package org.rapidpm.vaadin.trainer.backend.binaryblob.image;

import static java.nio.file.Files.readAllBytes;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.rapidpm.frp.functions.CheckedFunction;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.trainer.backend.binaryblob.BlobService;

/**
 *
 */
public class BlobImageService implements BlobService {

  public static final String STORAGE_PREFIX = "_data/_nasa_pics/_0512px/";

  private static final Map<String, Result<byte[]>> cache = new ConcurrentHashMap<>(); //TODO fill up the memory

  private CheckedFunction<String, byte[]> load
      = (blobID) -> readAllBytes(new File(STORAGE_PREFIX + blobID).toPath());


  //@Override
  public Result<byte[]> loadBlob(String blobID) {
    //hard coded right now
    final Result<byte[]> result = cache.containsKey(blobID)
                                  ? cache.get(blobID)
                                  : cache.computeIfAbsent(blobID , load);
    return result;
  }
}
