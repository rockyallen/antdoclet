package demo;

/**
 * Something to sit on
 * 
 * @ant.type  category="miracle"
 * @author rocky
 */
public class Blanket {
    private String colour;
    private double size;

    /**
     * Main colour
     * 
     * @ant.not-required Default is black
     * @param colour the colour to set
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * Length in feet
     * 
     * @ant.required
     * @param size the size to set
     */
    public void setSize(double size) {
        this.size = size;
    }
    
}
