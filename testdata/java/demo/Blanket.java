package demo;

/**
 * Something to sit on.
 * Sample nested element, implemented as a concrete class.
 * 
 * @ant.type  category="dayout"
 */
public class Blanket {

  /**
     * Main colour
     * 
     * @ant.not-required Default is black
     * @param colour the colour to set
     */
    public void setColour(String colour) {
    }

    /**
     * Length in feet
     * 
     * @ant.required
     * @param size the size to set
     */
    public void setSize(double size) {
    }
    
}
