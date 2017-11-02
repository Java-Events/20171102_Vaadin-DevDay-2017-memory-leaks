package org.rapidpm.vaadin.trainer.backend.binaryblob.image;

import static java.nio.file.Files.readAllBytes;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.rapidpm.frp.functions.CheckedFunction;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.trainer.backend.binaryblob.BlobService;

/**
 *
 */
public class BlobImageServiceCached /*implements BlobService*/ {


  public static final String STORAGE_PREFIX = "_data/_nasa_pics/_0512px/";

  private static final Map<String, WeakReference<Result<byte[]>>> cache = new ConcurrentHashMap<>(); //TODO fill up the memory

  private CheckedFunction<String, byte[]> load
      = (blobID) -> readAllBytes(new File(STORAGE_PREFIX + blobID).toPath());


  //@Override
  public Result<byte[]> loadBlob(String blobID) {
    //hard coded right now
    final boolean containsKey = cache.containsKey(blobID);
    System.out.println("containsKey = " + containsKey);
    if (containsKey) {
      final WeakReference<Result<byte[]>> weakReference = cache.get(blobID);
      final Result<byte[]> result = weakReference.get();

      System.out.println((result == null )
                         ? "blobId " + blobID + " was eaten by GC "
                         : "blobId " + blobID + " was cached so far"
      );
      if (result == null) {
        cache.remove(blobID);
        return cache.computeIfAbsent(blobID , s -> new WeakReference<>(load.apply(blobID)))
            .get();
      } else {
        return result;
      }
    } else {
      return cache
          .computeIfAbsent(blobID , s -> new WeakReference<>(load.apply(blobID)))
          .get();
    }
  }


}
