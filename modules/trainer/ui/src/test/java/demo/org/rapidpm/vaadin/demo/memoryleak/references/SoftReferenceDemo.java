package demo.org.rapidpm.vaadin.demo.memoryleak.references;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.List;

/**
 * run this with -Xmx5m -Xms5m
 */
public class SoftReferenceDemo extends AbstractReferenceDemo {

  public static void main(String[] args) {
    new SoftReferenceDemo().doWork();
  }

  @Override
  public void createReference(int i , List<Reference<MyObject>> references) {
    references.add(new SoftReference<>(new MyObject("soft " + i)));
  }


}
