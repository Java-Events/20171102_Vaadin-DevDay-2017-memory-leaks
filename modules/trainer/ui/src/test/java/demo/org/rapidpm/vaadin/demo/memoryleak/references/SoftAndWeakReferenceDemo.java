package demo.org.rapidpm.vaadin.demo.memoryleak.references;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 *
 */
public class SoftAndWeakReferenceDemo extends AbstractReferenceDemo {

  public static void main(String[] args) {
    new SoftAndWeakReferenceDemo().doWork();
  }

  @Override
  public void createReference(int i , List<Reference<MyObject>> references) {
    references.add(new SoftReference<>(new MyObject("soft " + i)));
    references.add(new WeakReference<>(new MyObject("weak " + i)));
  }

}
