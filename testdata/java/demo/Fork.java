package demo;

/**
 * Example of a nested type that extends the extension type.
 * 
 * @ant.type name="fork" category="EatingImplement"
 * @author rocky
 */
public class Fork extends EatingImplement {

    @Override
    public String usedFor() {
        return "Vegetables";
    }
    
}
