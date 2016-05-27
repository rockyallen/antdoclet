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

/**
 * Something to sit on
 * 
 * @ant.type  category="dayout"
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