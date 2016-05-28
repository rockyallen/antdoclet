package demo;

/**
 * Example of a nested type that extends an extension base class.
 * Spoons are used for eating soup.
 * 
 * @ant.type name="Spoon" category="EatingImplement"
 * @author rocky
 */
public class Spoon extends EatingImplement {

    @Override
    public String usedFor() {
        return "Soup";
    }
    
}
