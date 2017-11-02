package demo.org.rapidpm.vaadin.demo.memoryleak.references;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 *
 */
public class WeakReferenceDemo extends AbstractReferenceDemo {

  public static void main(String[] args) {
    new WeakReferenceDemo().doWork();
  }

  @Override
  public void createReference(int i , List<Reference<MyObject>> references) {
    references.add(new WeakReference<>(new MyObject("weak " + i)));
  }


}

