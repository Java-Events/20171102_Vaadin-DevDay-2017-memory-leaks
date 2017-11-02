package org.rapidpm.vaadin.trainer.backend.binaryblob;

import org.rapidpm.frp.model.Result;

/**
 *
 */
public interface BlobService {

  public Result<byte[]> loadBlob(String blobID);
}
