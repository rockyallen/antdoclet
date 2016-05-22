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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sun.javadoc.ClassDoc;
import java.util.Collections;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Node;

/**
 * AntDoc based on an Ant macro.
 *
 * @author rocky
 */
public class AntDocMacrodef extends AntDoc {

    private String antName;
    private String description;
    private Map<String, AttributeData> attributes = new HashMap<String, AttributeData>();

    private AntDocMacrodef() {

    }

    public static AntDoc valueOf(Node macrodef) throws XPathExpressionException {
        AntDocMacrodef ret = new AntDocMacrodef();
        ret.antName = XpathHelper.getAttributeValue("name", macrodef);
        ret.description = XpathHelper.getAttributeValue("description", macrodef);
        for (Node attnode : XpathHelper.get("attribute", macrodef)) {
            String name = XpathHelper.getAttributeValue("name", attnode);
            Util.notNull(name, "All attributes must have a name");
            AttributeData att = new AttributeData();
            // schema does not provide this information
            att.setType("-");
            if (XpathHelper.get("@description", attnode).length > 0) {
                String s = XpathHelper.getAttributeValue("description", attnode);
                att.setShortDescription(s);
                att.setComment(s);
            } else {
                att.setShortDescription(null);
                att.setComment(null);
            }
            if (XpathHelper.get("@default", attnode).length > 0) {
                att.setDefaultValue("Default is " + XpathHelper.getAttributeValue("default", attnode));
                att.setRequired(false);
            } else {
                att.setDefaultValue(null);
                att.setRequired(true);
            }
            att.setRequiredComment("");
            ret.attributes.put(name, att);
        }
        return ret;
    }

    @Override
    public String getAntCategory() {
        return "macrodef";
    }

    @Override
    public String getAntName() {
        return antName;
    }

    @Override
    public String getAttributeComment(String attributeName) {
        String ret = attributes.get(attributeName).getShortDescription();
        return ret == null ? "" : ret;
    }

    @Override
    public String getAttributeNotRequired(String attributeName) {
        return attributes.get(attributeName).getDefaultValue();
    }

    @Override
    public String getAttributeRequired(String attributeName) {
        return attributes.get(attributeName).getDefaultValue() == null ? "" : null;
    }

    @Override
    public String getAttributeType(String attributeName) {
        return attributes.get(attributeName).getType();
    }

    @Override
    public Collection<String> getAttributes() {
        return attributes.keySet();
    }

    @Override
    public String getComment() {
        return description;
    }

    @Override
    public String getFullClassName() {
        return antName;
    }

    @Override
    public Enumeration<String> getNestedElements() {
        return Collections.emptyEnumeration();
    }

    @Override
    public Iterator<String> getNestedTypes() {
        return Collections.emptyIterator();
    }

    @Override
    public String getShortComment() {
        return description;
    }

    @Override
    public String getTaskOrType() {
        return "task";
    }

    @Override
    public boolean isIgnored() {
        return false;
    }

    @Override
    public boolean isInnerClass() {
        return false;
    }

    @Override
    public boolean isTagged() {
        return true;
    }

    @Override
    public boolean isTask() {
        return true;
    }

    @Override
    public boolean isTaskContainer() {
        return false;
    }

    @Override
    public boolean sourceIncluded() {
        return false;
    }

    @Override
    public boolean supportsCharacters() {
        return false;
    }
}
