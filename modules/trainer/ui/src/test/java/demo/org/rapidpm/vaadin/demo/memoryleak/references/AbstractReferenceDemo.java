package demo.org.rapidpm.vaadin.demo.memoryleak.references;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public abstract class AbstractReferenceDemo {


  public abstract void createReference(int i , List<Reference<MyObject>> references);

  public void doWork() {
    List<Reference<MyObject>> references = new ArrayList<>();
    for (int i = 0; i < 30; i++) {
      createReference(i, references);
      new MyObject("normal " + i);

    }
    printReferences(references);
  }

  public void printReferences(List<Reference<MyObject>> references) {
    ExecutorService ex = Executors.newSingleThreadExecutor();
    ex.execute(() -> {
      try {
        Thread.sleep(1_000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("-- printing references --");
      references
          .forEach(r -> System.out.printf("Reference: %s [%s]%n" ,
                                          r.get() ,
                                          r.getClass().getSimpleName()));
    });
    ex.shutdown();
  }


}
