/**
 *  Copyright (c) 2003-2016 Fernando Dobladez
 *
 *  This file is part of AntDoclet.
 *
 *  AntDoclet is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  AntDoclet is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with AntDoclet; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.neuroning.antdoclet;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

/**
 *
 * @author rocky
 */
public abstract class AntDoc implements Comparable<AntDoc> {

    /**
     * Return the "category" of this Ant "task" or "type"
     *
     * @returns The value of the "category" attribute of the ant.task or
     * ant.type if it exists.
     */
    public abstract String getAntCategory();

    /**
     * Return the name of this type from Ant's perspective
     *
     * @returns The value of the
     * @ant.task or
     * @ant.type if it exists. Otherwise, the Java class name.
     *
     */
    public abstract String getAntName();

    /**
     * Retrieves the method comment for the given attribute. The comment of the
     * setter is used preferably to the getter comment.
     *
     * @param attribute
     * @return The comment for the specified attribute. Never null; an empty
     * string if not provided.
     */
    public abstract String getAttributeComment(String attribute);

    /**
     * Get the comment about the "non-requirement" of this attribute. The
     * comment is extracted from the ant.not-required tag
     *
     * @param attribute
     * @return A comment. A null String if this attribute is not declared as
     * non-required
     */
    public abstract String getAttributeNotRequired(String attribute);

    /**
     * Get the comment about the requirement of this attribute. The comment if
     * extracted from the ant.required tag
     *
     * @param attribute
     * @return A comment. A null String if this attribute is not declared as
     * required
     */
    public abstract String getAttributeRequired(String attribute);

    /**
     * Return the name of the type for the specified attribute, eg int, double
     */
    public abstract String getAttributeType(String attributeName);

    /**
     * Get the attributes in this class from Ant's point of view.
     *
     * @return Collection of Ant attributes, excluding those inherited from
     * org.apache.tools.ant.Task
     */
    public abstract Collection<String> getAttributes();

    /**
     *
     * @return The source comment (description) for this class (task/type)
     */
    public abstract String getComment();

    /**
     *
     * @see #getNestedElements()
     * @param elementName
     * @return The java type for the specified element accepted by this task
     */
    public abstract String getFullClassName();

    /**
     * Originally, just returned the nested *elements*; I want it to return the
     * nested *types* as well (public abstract classes and interfaces)
     *
     * @return a collection of the "Nested Elements" that this Ant tasks accepts
     */
    public abstract Enumeration<String> getNestedElements();

    /**
     * List of class names for extensions for this class: public abstract
     * classes or interfaces.
     *
     * @return
     */
    public abstract Iterator<String> getNestedTypes();

    /**
     * @return Short comment for this class (basically, the first sentence)
     */
    public abstract String getShortComment();

    /**
     * Get "task" if this is a task, or "type" if it is a type
     *
     * @return
     */
    public abstract String getTaskOrType();

    /**
     * @return Should this task/type be excluded?
     */
    public abstract boolean isIgnored();

    /**
     *
     * @return true if this refers to an inner-class
     */
    public abstract boolean isInnerClass();

    /**
     * @returns true if the class has a
     * @ant.type or
     * @ant.task tag in it
     */
    public abstract boolean isTagged();

    /**
     * @return Whether this represents an Ant Task (otherwise, it is assumed as
     * a Type)
     */
    public abstract boolean isTask();

    /**
     * @return Is this an Ant Task Container?
     */
    public abstract boolean isTaskContainer();

    /**
     * @return Is the source code for this type included in this javadoc run?
     */
    public abstract boolean sourceIncluded();

    /**
     * characters&lt;/echo&gt;
     *
     * @return true if this Ant Task/Type expects characters in the element
     * body.
     */
    public abstract boolean supportsCharacters();

    @Override
    public int compareTo(AntDoc otherDoc) {

        int ret;
        try {
            String fullName1 = getAntCategory() + ":" + getAntName();
            String fullName2 = otherDoc.getAntCategory() + ":" + otherDoc.getAntName();
            ret = fullName1.compareTo(fullName2);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return ret;
    }

    @Override
    public String toString() {
        return "AntDoc{" + getFullClassName() + "," + getAntCategory() + "," + getAntName() + "}";
    }
}
