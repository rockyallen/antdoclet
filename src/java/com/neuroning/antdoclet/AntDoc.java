package com.neuroning.antdoclet;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import com.sun.javadoc.ClassDoc;

/**
 *
 * @author rocky
 */
public interface AntDoc extends Comparable<AntDoc> {

    int compareTo(AntDoc otherDoc);

    /**
     * Return the "category" of this Ant "task" or "type"
     *
     * @returns The value of the "category" attribute of the ant.task or
     * ant.type if it exists.
     */
    String getAntCategory() throws ClassNotFoundException, InstantiationException;

    /**
     * Return the name of this type from Ant's perspective
     *
     * @returns The value of the
     * @ant.task or
     * @ant.type if it exists. Otherwise, the Java class name.
     *
     */
    String getAntName() throws ClassNotFoundException, InstantiationException ;

    /**
     * Retrieves the method comment for the given attribute. The comment of the
     * setter is used preferably to the getter comment.
     *
     * @param attribute
     * @return The comment for the specified attribute
     */
    String getAttributeComment(String attribute);

    /**
     * Get the comment about the "non-requirement" of this attribute. The
     * comment if extracted from the ant.not-required tag
     * @param attribute
     * @return A comment. A null String if this attribute is not declared as
     * non-required
     */
    String getAttributeNotRequired(String attribute);

    /**
     * Get the comment about the requirement of this attribute. The comment if
     * extracted from the ant.required tag
     * @param attribute
     * @return A comment. A null String if this attribute is not declared as
     * required
     */
    String getAttributeRequired(String attribute);

    /**
     * Return the name of the type for the specified attribute
     */
    String getAttributeType(String attributeName) throws InstantiationException;

    /**
     * Get the attributes in this class from Ant's point of view.
     *
     * @return Collection of Ant attributes, excluding those inherited from
     * org.apache.tools.ant.Task
     */
    Collection<String> getAttributes() throws IntrospectionException;

    /**
     *
     * @return The source comment (description) for this class (task/type)
     */
    String getComment();

    /**
     * Return a new AntDoc for the "container" of this type. Only makes sense
     * for inner classes.
     *
     */
    AntDoc getContainerDoc() throws ClassNotFoundException, InstantiationException;

    ClassDoc getDocForNestedType(String typename);

    /**
     * Return a new AntDoc for the given "element"
     */
    AntDoc getElementDoc(String elementName) throws ClassNotFoundException, InstantiationException;

    /**
     *
     * @see #getNestedElements()
     * @param elementName
     * @return The java type for the specified element accepted by this task
     */
    Class<Object> getElementType(String elementName);

    String getFullClassName();

    /**
     * Originally, just returned the nested *elements*; I want it to return the
     * nested *types* as well (abstract classes and interfaces)
     *
     * @return a collection of the "Nested Elements" that this Ant tasks accepts
     */
    Enumeration<String> getNestedElements();

    /**
     * List of class names for extensions for this class: abstract classes or
     * interfaces.
     *
     * @return
     */
    Iterator<String> getNestedTypes();

    /**
     * @return Short comment for this class (basically, the first sentence)
     */
    String getShortComment();

    /**
     * Get "task" if this is a task, or "type" if it is a type
     *
     * @return
     */
    String getTaskOrType();

    /**
     * @return Should this task/type be excluded?
     */
    boolean isIgnored();

    /**
     *
     * @return true if this refers to an inner-class
     */
    boolean isInnerClass();

    /**
     * @returns true if the class has a
     * @ant.type or
     * @ant.task tag in it
     */
    boolean isTagged();

    /**
     * @return Whether this represents an Ant Task (otherwise, it is assumed as
     * a Type)
     */
    boolean isTask();

    /**
     * @return Is this an Ant Task Container?
     */
    boolean isTaskContainer();

    /**
     * @return Is the source code for this type included in this javadoc run?
     */
    boolean sourceIncluded();

    /**
     * characters&lt;/echo&gt;
     *
     * @return true if this Ant Task/Type expects characters in the element
     * body.
     */
    boolean supportsCharacters();
    
}
