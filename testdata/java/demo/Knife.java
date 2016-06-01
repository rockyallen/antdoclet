package demo;

/**
 * Knives are used for cutting meat.
 * 
 * @design Example of a nested type that extends the extension type.
 * @ant.type name="knife" category="cutlery"
 */
public class Knife extends EatingImplement {

  /**
   * Meat cutter.
   * 
   * @return 
   */
    @Override
    public String usedFor() {
        return "Meat";
    }
    
}
