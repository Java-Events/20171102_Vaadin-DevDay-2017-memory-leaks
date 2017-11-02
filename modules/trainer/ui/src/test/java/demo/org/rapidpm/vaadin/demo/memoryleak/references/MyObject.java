package demo.org.rapidpm.vaadin.demo.memoryleak.references;

/**
 *
 */
public class MyObject {
  private int[] ints = new int[1_000];
  private final String name;

  public MyObject(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  protected void finalize() throws Throwable {
    System.out.printf("Finalizing: %s%n" , name);
  }
}
