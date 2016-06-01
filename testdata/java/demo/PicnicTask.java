package demo;

import org.apache.tools.ant.Task;

/**
 * Plan for a day out.
 *
 * A sample task definition demonstrating nested types.
 *
 * @ant.task name="picnic" ignore="false" category="dayout"
 */
public class PicnicTask extends Task {

  public PicnicTask() {
    super();
  }

  @Override
  public void execute() {
  }

  /**
   * Where is the party held?
   *
   * @ant.required
   * @param name
   */
  public void setSite(String name) {

  }

  /**
   * Number of guests.
   *
   * Minimum is 5, maximum is 50
   *
   * @ant.not-required Default is 10
   * @param guests
   */
  public void setGuests(int guests) {

  }

  /**
   * Number of day.
   *
   * Minimum is 1, maximum is 5
   *
   * @ant.not-required Default is 1
   * @param days
   */
  public void setDays(int days) {

  }

  /**
   * This comment is never used.
   * @design Simple nested class.
   * Ant calls these nested elements.
   *
   * @ant.required
   */
  public void addConfiguredBlanket(Blanket blanket) {

  }

  /**
   * Something to eat with.
   * (See also the "cutlery" category).
   * Choose any number from:
   * 
   * 
   * @design Example of a nested abstract class.
   *
   * @ant.not-required
   * @param cutlery
   */
  public void addConfigured(EatingImplement cutlery) {

  }

  /**
   * Something to eat.
   * Choose any number from:
   * 
   * @design Example of a nested type as an interface. Ant calls these types.
   *
   * @ant.not-required
   * @param food
   */
  public void addConfigured(FoodItem food) {

  }
}
