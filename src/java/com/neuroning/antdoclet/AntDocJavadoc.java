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

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

import org.apache.tools.ant.IntrospectionHelper;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;
import org.apache.tools.ant.types.EnumeratedAttribute;

import com.sun.javadoc.Doc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;
import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * An object of this class represents a Java class that is: an Ant Task, or an
 * Ant Type.
 *
 * It provides information about the Task/Type's attributes, nested elements and
 * more.
 *
 * It's intended to be used for documenting Ant Tasks/Types.
 *
 * Use the getAttribute methods to return either attributes or nested types.
 * This works because it enforces attributes and nested types to have distinct
 * names. (The name of a nested type is its parameter name in the addConfigured
 * method).
 *
 * @author Fernando Dobladez dobladez@gmail.com
 */
public class AntDocJavadoc extends AntDoc {

    // types and attributes have the same data
    private final Map<String, AttributeData> nestedTypesAndAttributes = new HashMap<String, AttributeData>();

    // attributes only
    private final Set<String> attributes = new HashSet<String>();

    // types only
    private final Set<String> nestedTypes = new HashSet<String>();
    /**
     * An IntrospectionHelper (from Ant) to interpret ant-specific conventions
     * from Tasks and Types
     */
    private final IntrospectionHelper introHelper;

    /**
     * Javadoc description for this type.
     */
    private final ClassDoc doc;

    /**
     * Javadoc "root node"
     */
    private final RootDoc rootdoc;

    /**
     * The java Class for this type
     */
    private final Class<? extends Object> clazz;

    private AntDocJavadoc(IntrospectionHelper ih, RootDoc rootdoc, ClassDoc doc,
            Class<? extends Object> clazz) {
        this.doc = Util.notNull(doc, "doc");
        this.rootdoc = Util.notNull(rootdoc, "rootdoc");
        this.introHelper = Util.notNull(ih, "ih");
        this.clazz = Util.notNull(clazz, "clazz");
    }

    /**
     * Convert clazz to an AntDoc
     *
     * @param clazz
     * @param rootdoc
     * @return an AntDoc, if it is a documentable type, else null
     * @throws ClassNotFoundException
     */
    public static AntDocJavadoc getInstance(String clazz, RootDoc rootdoc) throws Exception, InstantiationException {
        Util.notNull(clazz, "clazz");
        Util.notNull(rootdoc, "rootdoc");

        Class<? extends Object> c = null;

        try {
            c = Class.forName(clazz);
        } catch (ClassNotFoundException ee) {
            // try inner class (replacing last . for $)
            int lastdot = clazz.lastIndexOf(".");

            if (lastdot >= 0) {
                clazz = clazz.substring(0, lastdot) + "$"
                        + clazz.substring(lastdot + 1);
            }
            c = Class.forName(clazz);
        }
        return getInstance(c, rootdoc);
    }

    /**
     * Get an AntDoc instance from the given class.
     *
     * @param clazz
     * @param rootdoc
     * @return Antdoc if is a class to document, else null.
     */
    private static AntDocJavadoc getInstance(Class<? extends Object> clazz, RootDoc rootdoc) throws Exception, InstantiationException {
        Util.notNull(clazz, "clazz");
        Util.notNull(rootdoc, "rootdoc");

        AntDocJavadoc d = null;

        IntrospectionHelper ih = IntrospectionHelper.getHelper(clazz);

        ClassDoc doc = rootdoc.classNamed(clazz.getName());
        Util.notNull(doc, "doc");
        // Filter out those types/tasks that are marked as "ignored"
        if (!isIgnored(doc)) {
            d = new AntDocJavadoc(ih, rootdoc, doc, clazz);

            // parse attributes
            for (String attributeName : getAttributes2(ih)) {
                MethodDoc mm = getMethodFor(doc, attributeName);
                if (Util.tagValue(mm, "ant.required") != null || Util.tagValue(mm, "ant.not-required") != null) {
                    AttributeData att = new AttributeData();
                    Tag[] firstTags = mm.firstSentenceTags();
                    att.setShortDescription((firstTags.length > 0 && firstTags[0] != null) ? firstTags[0].text() : "???");
                    att.setComment(mm.commentText());
                    att.setName(attributeName);
                    att.setType(typeToString(ih.getAttributeType(attributeName)));
                    if (Util.tagValue(mm, "ant.required") != null) {
                        att.setDefaultValue("NA");
                        att.setRequired(true);
                        att.setRequiredComment(null); // velocity treats this as false
                    } else {
                        att.setDefaultValue("");
                        att.setRequired(false);
                        att.setRequiredComment(Util.tagValue(mm, "ant.not-required"));
                    }
                    d.nestedTypesAndAttributes.put(attributeName, att);
                    d.attributes.add(attributeName);
                }
            }
        }

        // parse nested types
        for (Method m : ih.getExtensionPoints()) {
            String nestedClassname = m.getParameters()[0].getType().getName();
            MethodDoc mm = getMethodForNestedType(doc, nestedClassname);
            if (Util.tagValue(mm, "ant.required") != null || Util.tagValue(mm, "ant.not-required") != null) {
                String name = mm.parameters()[0].name();

                if (d.nestedTypesAndAttributes.get(name) != null) {
                    throw new IllegalArgumentException("Nested type has duplicate name '" + name + "' in method " + mm);
                }
                AttributeData att = new AttributeData();
                att.setShortDescription(mm.commentText());
                att.setComment(mm.commentText());
                att.setName(name);
                att.setType(nestedClassname);
                if (Util.tagValue(mm, "ant.required") != null) {
                    att.setDefaultValue(null);
                    att.setRequired(true);
                    att.setRequiredComment("???");
                } else {
                    att.setDefaultValue("");
                    att.setRequired(false);
                    att.setRequiredComment(Util.tagValue(mm, "ant.not-required"));
                }
                d.nestedTypesAndAttributes.put(name, att);
                d.nestedTypes.add(name);
            }
        }
        return d;
    }

    private static Collection<String> getAttributes2(IntrospectionHelper introHelper) throws IntrospectionException {
        ArrayList<String> attrs = Collections.list(introHelper.getAttributes());

        // filter out all attributes inherited from Task, since they are
        // common to all Ant Tasks and tend to confuse
        BeanInfo beanInfo = Introspector.getBeanInfo(Task.class);
        PropertyDescriptor[] commonProps = beanInfo
                .getPropertyDescriptors();

        for (PropertyDescriptor commonProp : commonProps) {
            String propName = commonProp.getName().toLowerCase();
            if (attrs.contains(propName)) {
                System.out.println("WARNING: Ignoring task property:" + propName);
                attrs.remove(propName);
            }
        }

        return attrs;
    }

    /**
     * @return Whether this represents an Ant Task (otherwise, it is assumed as
     * a Type)
     */
    @Override
    public boolean isTask() {
        return Task.class.isAssignableFrom(clazz);
    }

    /**
     * Get "task" if this is a task, or "type" if it is a type
     *
     * @return
     */
    @Override
    public String getTaskOrType() {
        return isTask() ? "task" : "type";
    }

    /**
     * @return Is this an Ant Task Container?
     */
    @Override
    public boolean isTaskContainer() {
        return TaskContainer.class.isAssignableFrom(clazz);
    }

    /**
     * @return Should this task/type be excluded?
     */
    @Override
    public boolean isIgnored() {
        return isIgnored(doc);
    }

    private static boolean isIgnored(Doc doc) {
        return (Util.tagValue(doc, "ant.task") == null
                && Util.tagValue(doc, "ant.type") == null)
                || "true".equalsIgnoreCase(Util.tagAttributeValue(doc, "ant.task", "ignore"))
                || "true".equalsIgnoreCase(Util.tagAttributeValue(doc, "ant.type", "ignore"));
    }

    /**
     * @return Is the source code for this type included in this javadoc run?
     */
    @Override
    public boolean sourceIncluded() {
        return doc.isIncluded();
    }

    /**
     *
     * @return The source comment (description) for this class (task/type)
     */
    @Override
    public String getComment() {
        return doc.commentText();
    }

    /**
     * @return Short comment for this class (basically, the first sentence)
     */
    @Override
    public String getShortComment() {
        Tag[] firstTags = doc.firstSentenceTags();

        if (firstTags.length > 0 && firstTags[0] != null) {
            return firstTags[0].text();
        }
        return null;

    }

    /**
     * Get the attributes in this class from Ant's point of view.
     *
     * @return Collection of Ant attributes, excluding those inherited from
     * org.apache.tools.ant.Task
     */
    @Override
    public Collection<String> getAttributes() {

        return attributes;
    }

    /**
     * Originally, just returned the nested *elements*; I want it to return the
     * nested *types* as well (abstract classes and interfaces)
     *
     * @return a collection of the "Nested Elements" that this Ant tasks accepts
     */
    @Override
    public Enumeration<String> getNestedElements() {

        return introHelper.getNestedElements();
    }

    /**
     * List of class names for extensions for this class: abstract classes or
     * interfaces.
     *
     * @return
     */
    @Override
    public Iterator<String> getNestedTypes() {
        return nestedTypes.iterator();
    }

    @Override
    public String getFullClassName() {
        return clazz.getName();
    }

    /**
     *
     * @return true if this refers to an inner-class
     */
    @Override
    public boolean isInnerClass() {
        return doc.containingClass() != null;
    }

    /**
     * Get the comment about the requirement of this attribute. The comment if
     * extracted from the
     *
     * @ant.required tag
     * @param attribute
     * @return A comment. A null String if this attribute is not declared as
     * required
     */
    @Override
    public String getAttributeRequired(String attribute) {
        return nestedTypesAndAttributes.get(attribute).getRequiredComment();
    }

    /**
     * Get the comment about the "non-requirement" of this attribute. The
     * comment if extracted from the
     *
     * @ant.not-required tag
     * @param attribute
     * @return A comment. A null String if this attribute is not declared as
     * non-required
     */
    @Override
    public String getAttributeNotRequired(String attribute) {
        return nestedTypesAndAttributes.get(attribute).getRequiredComment();
    }

    /**
     * Return the "category" of this Ant "task" or "type"
     *
     * @returns The value of the "category" attribute of the ant.task or
     * ant.type if it exists.
     */
    @Override
    public String getAntCategory() {

        String antCategory = Util.tagAttributeValue(this.doc, "ant.task",
                "category");

        if (antCategory == null) {
            antCategory = Util.tagAttributeValue(this.doc, "ant.type",
                    "category");
        }

        if (antCategory == null && getContainerDoc() != null) {
            antCategory = getContainerDoc().getAntCategory();
        }

        return antCategory;

    }

    /**
     * @returns true if the class has a
     * @ant.type or
     * @ant.task tag in it
     */
    @Override
    public boolean isTagged() {
        return Util.tagAttributeValue(doc, "ant.task", "name") != null
                || Util.tagAttributeValue(doc, "ant.type", "name") != null;
    }

    /**
     * Return the name of this type from Ant's perspective
     *
     * @returns The value of the ant.task attribute or ant.type if it exists.
     * Otherwise, the Java class name.
     *
     */
    @Override
    public String getAntName() {
        String antName = Util.tagAttributeValue(this.doc, "ant.task", "name");

        if (antName == null) {
            antName = Util.tagAttributeValue(this.doc, "ant.type", "name");
        }

        // Handle inner class case
        if (antName == null && getContainerDoc() != null) {

            antName = getContainerDoc().getAntName()
                    + "."
                    + this.clazz
                    .getName()
                    .substring(
                            this.clazz.getName().lastIndexOf('$') + 1);
        }

        if (antName == null) {
            antName = typeToString(clazz);
        }
        assert antName != null : "antName";
        return antName;
    }

    /**
     * Return a new AntDoc for the "container" of this type. Only makes sense
     * for inner classes.
     *
     */
    private AntDoc getContainerDoc() {
        if (!isInnerClass()) {
            return null;
        }
        try {
            return getInstance(this.doc.containingClass().qualifiedName(), this.rootdoc);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Return the name of the type for the specified attribute
     */
    @Override
    public String getAttributeType(String attributeName) {
        return nestedTypesAndAttributes.get(attributeName).getType();
    }

    /**
     * Retrieves the method comment for the given attribute. The comment of the
     * setter is used preferably to the getter comment.
     *
     * @param attribute
     * @return The comment for the specified attribute
     */
    @Override
    public String getAttributeComment(String attributeName) {
        return nestedTypesAndAttributes.get(attributeName).getComment();
    }

    /**
     * Searches the given class for the appropriate setter or getter for the
     * given attribute. This method only returns the getter if no setter is
     * available. If the given class provides no method declaration, the
     * superclasses are searched recursively.
     *
     * @param attribute
     * @param methods
     * @return The MethodDoc for the given attribute or null if not found
     */
    private static MethodDoc getMethodFor(ClassDoc classDoc, String attribute) {
        Util.notNull(classDoc, "classDoc");
        Util.notNull(attribute, "attribute");
        MethodDoc result = null;
        MethodDoc[] methods = classDoc.methods();
        for (int i = 0; i < methods.length; i++) {

            // we give priority to the documentation on the "setter" method of
            // the attribute
            // but if the documentation is only on the "getter", use it
            // we give priority to the documentation on the "setter" method of
            // the attribute
            // but if the documentation is only on the "getter", use it
            if (methods[i].name().equalsIgnoreCase("set" + attribute)) {
                return methods[i];
            } else if (methods[i].name().equalsIgnoreCase("get" + attribute)) {
                result = methods[i];
            }
        }
        if (result == null && !(classDoc.superclass() == null)) {
            return getMethodFor(classDoc.superclass(), attribute);
        }
        return result;
    }

    /**
     * Get the addConfigured(nestedClassname) method. Tries superclasses as
     * well.
     *
     * @param classDoc
     * @param nestedClassname
     * @return The method - it should never be null.
     */
    private static MethodDoc getMethodForNestedType(ClassDoc classDoc, String nestedClassname) {
        Util.notNull(classDoc, "classDoc");
        Util.notNull(nestedClassname, "nestedClassname");
        MethodDoc result = null;
        for (MethodDoc m : classDoc.methods()) {
            if (m.name().equalsIgnoreCase("addConfigured")) {
                String paramClassName = m.parameters()[0].type().qualifiedTypeName();
                if (nestedClassname.equals(paramClassName)) {
                    result = m;
                    break;
                }
            }
        }

        if (result == null && classDoc.superclass() != null) {
            return getMethodForNestedType(classDoc.superclass(), nestedClassname);
        }
        return result;
    }

    /**
     * characters&lt;/echo&gt;
     *
     * @return true if this Ant Task/Type expects characters in the element
     * body.
     */
    @Override
    public boolean supportsCharacters() {
        return introHelper.supportsCharacters();
    }

    // Private helper methods:
    /**
     * Create a "displayable" name for the given type
     *
     * @param clazz
     * @return a string with the name for the given type
     */
    private static String typeToString(Class<? extends Object> clazz) {
        Util.notNull(clazz, "clazz");
        String fullName = clazz.getName();

        String name = fullName.lastIndexOf(".") >= 0 ? fullName
                .substring(fullName.lastIndexOf(".") + 1) : fullName;

        // inners use dollar signs
        String result = name.replace('$', '.');

        if (EnumeratedAttribute.class.isAssignableFrom(clazz)) {
            try {
                EnumeratedAttribute att = (EnumeratedAttribute) clazz
                        .newInstance();
                result = "String [";

                String[] values = att.getValues();
                result += "\"" + values[0] + "\"";
                for (int i = 1; i < values.length; i++) {
                    result += ", \"" + values[i] + "\"";
                }

                result += "]";
            } catch (java.lang.IllegalAccessException iae) {
                // ignore, may a protected/private Enumeration
                iae.printStackTrace();
            } catch (InstantiationException ex) {
                throw new RuntimeException(ex);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "AntDocJavadoc={" + clazz.getName() + "," + getAntCategory() + "," + getAntName() + ",atts=" + attributes.size() + ",elements=" + "?" + "nestedTypes=" + nestedTypes.size();
    }
}
