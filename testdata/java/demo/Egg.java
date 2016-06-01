package demo;

/**
 * Large brown hens egg
 * 
 * @ant.type name="egg" category="food"
 */
public class Egg implements FoodItem {

  /**
   * Number wanted
   * @ant.required 
   */
  public void setQuantity(int qty) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   * Calories, each egg
   * @ant.not-required
   */
  public double getCalories() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
