/**
 *  Copyright (c) 2003-2016 Fernando Dobladez
 *
 *  This file is part of AntDoclet.
 *
 *  IAntDoclet is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  IAntDoclet is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with IAntDoclet; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */
package demo;

import org.apache.tools.ant.Task;

/**
 * plan for a day out.
 * 
 * A sample task definition demonstrating all elements.
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
     * Sample nested class.
     * 
     * Ant calls these nested elements. 
     * Their classes are known at compile time.
     *
     * @ant.required
     * @param t
     */
    public void addConfiguredBlanket(Blanket blanket) {

    }

    /**
     * Box to put things in.
     * 
     *Example of a nested interface.
     *Ant calls these types - you can typedef them at runtime.
     *
     * @ant.not-required
     * @param basket
     */
    public void addConfigured(Basket basket) {

    }

    /**
     * Eating utensil.
     * 
     *Example of a nested abstract class.
     *Ant calls these types - you can typedef them at runtime.
     *
     * @ant.not-required
     * @param cutlery
     */
    public void addConfigured(EatingImplement cutlery) {

    }
}
