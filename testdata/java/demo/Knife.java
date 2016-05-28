package demo;

/**
 * Example of a nested type that extends the extension type.
 * Knives are used for cutting meat.
 * @ant.type name="Knife" category="EatingImplement"
 * @author rocky
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
