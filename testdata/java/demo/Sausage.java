package demo;

/**
 * Rosemary and thyme flavoured organic pork sausage.
 * 
 * @ant.type name="sausage" category="food"
 */
public class Sausage implements FoodItem {

  /**
   * Number required
   * @ant.not-required Default is 1 kg.
   */
  public void setQuantity(double qty) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   * Calories per sausage
   * @ant.not-required
   */
  public double getCalories() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
