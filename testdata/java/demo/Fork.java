package demo;

/**
 * Forks are used for stabbing vegetables.
 * 
 * @design Example of a nested type that extends the extension type.
 * 
 * @ant.type name="fork" category="cutlery"
 */
public class Fork extends EatingImplement {

    @Override
    public String usedFor() {
        return "Vegetables";
    }
    
}
