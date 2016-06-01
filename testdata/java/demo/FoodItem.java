package demo;
/**
 * 
 * Example of a extension type that is an interface.
 * This class is an Ant type, but don't tag it as such - only tag its implementations.
 */

public interface FoodItem {
  
//   /**
//   * How much do you want?
//   * 
//   * @ant.required
//   */
//  void setQuantity(int qty);
  
  /**
   * How fat will it make me?
   * 
   * @ant.required
   */
  double getCalories();
  

  
}
