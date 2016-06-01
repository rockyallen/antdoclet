package demo;

/**
 * Spoons are used for eating soup.
 * 
 * @design Example of a nested type that extends an extension base class.
 * @ant.type name="spoon" category="cutlery"
 */
public class Spoon extends EatingImplement {

    @Override
    public String usedFor() {
        return "Soup";
    }
    
}
