package demo;
/**
 * Example of a extension type that is an interface.
 * 
 * @ant.type name="food" category="fooditem"
 * @author rocky
 */

public interface FoodItem {
  
  /**
   * What is it called?
   * Only here to make it appear in the antdoclet output - it should be private to your implementing class.
   * @ant.required
   */
  void setName(String name);
  
  /**
   * What is it called?
   * 
   * @ant.required
   */
  String getName();
  
  /**
   * How big is it?
   * 
   * @ant.required
   */
  double getSize();

  /**
   * How fat will it make me?
   * 
   * @ant.required
   */
  double getCalories();
  
}
