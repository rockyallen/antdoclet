package com.neuroning.antdoclet;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sun.javadoc.ClassDoc;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Node;

/**
 *
 * @author rocky
 */
public class AntDocMacrodefImp implements AntDoc {

    private String antName;
    private String description;
    private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

    private static final Iterator<String> EMPTY_ITERATOR = null;
    private static final Enumeration<String> EMPTY_ENUMERATOR = null;

    private AntDocMacrodefImp() {

    }

    public static AntDoc valueOf(Node macrodef) throws XPathExpressionException {
        AntDocMacrodefImp ret = new AntDocMacrodefImp();
        ret.antName = XpathHelper.getAttributeValue("name", macrodef);
        ret.description = XpathHelper.getAttributeValue("description", macrodef);
        for (Node attnode : XpathHelper.get("//attribute", macrodef)) {
            Attribute att = new Attribute();
            att.type="-";
            String name=XpathHelper.getAttributeValue("name", attnode);
            att.description=XpathHelper.getAttributeValue("description", attnode);
            att.defaultValue = XpathHelper.getAttributeValue("default", attnode);
            ret.attributes.put(name, att);
        }
        return ret;
    }

public String getAntCategory() {
        return "macrodef";
    }

    public String getAntName() {
        return antName;
    }

    public String getAttributeComment(String attribute) {
        return attributes.get(attribute).description;
    }

    public String getAttributeNotRequired(String attribute) {
        return attributes.get(attribute).defaultValue == null ? null : "-";
    }

    public String getAttributeRequired(String attribute) {
        return attributes.get(attribute).defaultValue == null ? "-" : null;
    }

    public String getAttributeType(String attributeName) {
        return "-";
    }

    public Collection<String> getAttributes() {
        return attributes.keySet();
    }

    public String getComment() {
        return description;
    }

    public AntDoc getContainerDoc() {
        return null;
    }

    public ClassDoc getDocForNestedType(String typename) {
        return null;
    }

    public AntDoc getElementDoc(String elementName) {
        return null;
    }

    public Class<Object> getElementType(String elementName) {
        return null;
    }

    public String getFullClassName() {
        return antName;
    }

    public Enumeration<String> getNestedElements() {
        return EMPTY_ENUMERATOR;
    }

    public Iterator<String> getNestedTypes() {
        return EMPTY_ITERATOR;
    }

    public String getShortComment() {
        return description;
    }

    public String getTaskOrType() {
        return "task";
    }

    public boolean isIgnored() {
        return false;
    }

    public boolean isInnerClass() {
        return false;
    }

    public boolean isTagged() {
        return true;
    }

    public boolean isTask() {
        return true;
    }

    public boolean isTaskContainer() {
        return false;
    }

    public boolean sourceIncluded() {
        return false;
    }

    public boolean supportsCharacters() {
        return false;
    }

    @Override
        public int compareTo(AntDoc otherDoc) {
//        try {
        return toString().compareTo(otherDoc.toString());
//        } catch (ClassNotFoundException ex) {
//            throw new RuntimeException(ex);
//        } catch (InstantiationException ex) {
//            throw new RuntimeException(ex);
//        }
    }

    @Override
        public String toString() {
        return getAntCategory() + ":" + getAntName();
    

}

    private static class Attribute {

    String description;
    String defaultValue;
    String type;
}
}
