package demo;

import org.apache.tools.ant.Task;

/**
 * Empty task definition. Does nothing at all.
 *
 * @ant.task name="feed" ignore="false" category="miracle"
 */
public class TestTask extends Task {

    public TestTask() {
        super();
    }

    @Override
    public void execute() {

    }

    /**
     * Number of fishes wanted
     *
     * @ant.not-required Default is 50
     * @param fishes
     */
    public void setFishes(int fishes) {

    }

    /**
     * Number of loaves wanted.
     *
     * Minimum is 10, maximum is 100
     *
     * @ant.not-required Default is 50
     * @param loaves
     */
    public void setLoaves(int loaves) {

    }

    /**
     * Customer name
     *
     * @ant.required
     * @param name
     */
    public void setName(String name) {

    }

    /**
     * Sample nested class.
     * 
     * Ant calls these nested elements. 
     * Their classes are known at compile time.
     *
     * @ant.required
     * @param t
     */
    public void addConfiguredBlanket(Blanket t) {

    }

    /**
     * Sample nested interface.
     *
     * Ant calls these types - you can typedef them at runtime.
     *
     * @ant.required
     * @param t
     */
    public void addConfigured(Basket t) {

    }

    /**
     * Sample nested abstract class.
     *
     * Ant calls these types - you can typedef them at runtime.
     *
     *
     * @ant.required
     * @param t
     */
    public void addConfigured(EatingImplement t) {

    }
}
