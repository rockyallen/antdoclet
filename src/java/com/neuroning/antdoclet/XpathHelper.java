package com.neuroning.antdoclet;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Hide some of the ugliness that is JDOM.
 * 
 * @author rocky
 */
public class XpathHelper {

    private static final XPath xpath = XPathFactory.newInstance().newXPath();

    private XpathHelper() {
    }

    /**
     * Node is an element.
     * Get its child nodes that corresponds to the given xpath expression.
     * 
     * @param expr
     * @param node
     * @return
     * @throws XPathExpressionException
     */
    public static Node[] get(String expr, Node node) throws XPathExpressionException {
        NodeList nodes = (NodeList) xpath.evaluate(expr, node, XPathConstants.NODESET);
        Node[] ret = new Node[nodes.getLength()];
        for (int i = 0; i < nodes.getLength(); i++) {
            ret[i] = nodes.item(i);
        }
        return ret;
    }

    /**
     * Node is an element.
     * Get the string value of the first child node that corresponds to the given xpath expression.
     * 
     * @param expr
     * @param node
     * @return
     * @throws XPathExpressionException
     */
    public static String getValue(String expr, Node node) throws XPathExpressionException {
        NodeList nodes = (NodeList) xpath.evaluate(expr, node, XPathConstants.NODESET);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getNodeValue();
        } else {
            return null;
        }
    }

    /**
     * Node is an element. Get the value of its attribute named attname.
     *
     * @param attname
     * @param node
     * @return
     * @throws XPathExpressionException
     */
    public static String getAttributeValue(String attname, Node node) throws XPathExpressionException {
        return (String) xpath.evaluate("@" + attname, node, XPathConstants.STRING);
    }

    /**
     * Get all child nodes that correspond to the given xpath expression.
     * 
     * @param expr
     * @param node
     * @return
     * @throws XPathExpressionException 
     */
    public static Node[] get(String expr, InputSource node) throws XPathExpressionException {
        NodeList nodes = (NodeList) xpath.evaluate(expr, node, XPathConstants.NODESET);
        Node[] ret = new Node[nodes.getLength()];
        for (int i = 0; i < nodes.getLength(); i++) {
            ret[i] = nodes.item(i);
        }
        return ret;
    }

}
