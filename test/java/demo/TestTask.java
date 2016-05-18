package demo;

import org.apache.tools.ant.Task;

/**
 * Empty task definition.
 * 
 * Does nothing at all.
 * 
  @ant.task name="feed" ignore="false" category="miracle"
  */

public class TestTask extends Task{
   
    public TestTask()
    {
        super();
    }
    
    @Override
    public void execute()
    {
        
    }
    
    /**
     * Number of fishes wanted
     * @ant.not-required Default is 50
     * @param fishes 
     */
    public void setFishes(int fishes)
    {
        
    }
    
    /**
     * Number of loaves wanted.
     * 
     * Minimum is 10, maximum i 100.
     * @ant.not-required Default is 50.
     * @param loaves 
     */
    public void setLoaves(int loaves)
    {
        
    }

    /**
     * Number of loaves wanted
     * @ant.required
     * @param name
     */
    public void setName(String name)
    {
        
    }
}
