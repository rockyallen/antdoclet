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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;
import org.xml.sax.InputSource;

/**
 *
 * @author rocky
 */
public class AntDocMacrodefImpTest {

    private static final String FULL
            = "<project name='test'>\n"
            + "    <macrodef name='full' description='Include all features'>\n"
            + "        <attribute name='target' description='Round thing' default='small' />\n"
            + "        <attribute name='link' description='Where it is going' />\n"
            + "        <attribute name='target.dir' default='${build.bin.dir}'/>\n"
            + "        <sequential/>\n"
            + "    </macrodef>\n"
            + "</project>\n";

    AntDoc ad;

    public AntDocMacrodefImpTest() throws Exception {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of valueOf method, of class AntDocMacrodefImp.
     */
    @Test
    public void testValueOf() throws Exception {
        System.out.println("valueOf");
        parse(FULL);
        assertNotNull("", ad);
    }

    /**
     * Test of getAntCategory method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetAntCategory() throws Exception {
        System.out.println("getAntCategory");
        parse(FULL);
        assertEquals("", "macrodef", ad.getAntCategory());
    }

    /**
     * Test of getAntName method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetAntName() throws Exception {
        System.out.println("getAntName");
        parse(FULL);
        assertEquals("", "full", ad.getAntName());
    }

    /**
     * Test of getAttributeComment method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetAttributeComment() throws Exception {
        System.out.println("getAttributeComment");
        parse(FULL);
        assertEquals("Normal case", "Round thing", ad.getAttributeComment("target"));
        assertEquals("Normal case", "Where it is going", ad.getAttributeComment("link"));
        assertEquals("Not provided", "", ad.getAttributeComment("target.dir"));
    }

    /**
     * Test of getAttributeNotRequired method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetAttributeNotRequired() throws Exception {
        parse(FULL);
        assertEquals("target", "Default is small", ad.getAttributeNotRequired("target"));
        assertEquals("link", null, ad.getAttributeNotRequired("link"));
    }

    /**
     * Test of getAttributeRequired method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetAttributeRequired() throws Exception {
        System.out.println("getAttributeRequired");
        parse(FULL);
        assertEquals("target", null, ad.getAttributeRequired("target"));
        assertEquals("link", "", ad.getAttributeRequired("link"));
    }

    /**
     * Test of getAttributeType method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetAttributeType() throws Exception {
        System.out.println("getAttributeType");
        parse(FULL);
        assertEquals("", "?", ad.getAttributeType("target"));
    }

    /**
     * Test of getAttributes method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetAttributes() throws Exception {
        System.out.println("getAttributes");
        parse(FULL);
        assertEquals("",
                new HashSet<String>(Arrays.asList(new String[]{"target", "link", "target.dir"})),
                new HashSet<String>(ad.getAttributes()));
    }

    /**
     * Test of getComment method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetComment() throws Exception {
        System.out.println("getComment");
        parse(FULL);
        assertEquals("", "Include all features", ad.getComment());
    }

    /**
     * Test of getFullClassName method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetFullClassName() throws Exception {
        System.out.println("getFullClassName");
        parse(FULL);
        assertEquals("", "full", ad.getFullClassName());
    }

    /**
     * Test of getNestedElements method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetNestedElements() throws Exception {
        System.out.println("getNestedElements");
        parse(FULL);
        assertEquals("", false, ad.getNestedElements().hasMoreElements());
    }

    /**
     * Test of getNestedTypes method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetNestedTypes() throws Exception {
        System.out.println("getNestedTypes");
        parse(FULL);
        assertEquals("", false, ad.getNestedTypes().hasNext());
    }

    /**
     * Test of getShortComment method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetShortComment() throws Exception {
        System.out.println("getShortComment");
        parse(FULL);
        assertEquals("", "Include all features", ad.getShortComment());
    }

    /**
     * Test of getTaskOrType method, of class AntDocMacrodefImp.
     */
    @Test
    public void testGetTaskOrType() throws Exception {
        System.out.println("getTaskOrType");
        parse(FULL);
        assertEquals("", "task", ad.getTaskOrType());
    }

    /**
     * Test of isIgnored method, of class AntDocMacrodefImp.
     */
    @Test
    public void testIsIgnored() throws Exception {
        System.out.println("isIgnored");
        parse(FULL);
        assertEquals("", false, ad.isIgnored());
    }

    /**
     * Test of isInnerClass method, of class AntDocMacrodefImp.
     */
    @Test
    public void testIsInnerClass() throws Exception {
        System.out.println("isInnerClass");
        parse(FULL);
        assertEquals("", false, ad.isInnerClass());
    }

    /**
     * Test of isTagged method, of class AntDocMacrodefImp.
     */
    @Test
    public void testIsTagged() throws Exception {
        System.out.println("isTagged");
        parse(FULL);
        assertEquals("", true, ad.isTagged());
    }

    /**
     * Test of isTask method, of class AntDocMacrodefImp.
     */
    @Test
    public void testIsTask() throws Exception {
        System.out.println("isTask");
        parse(FULL);
        assertEquals("", true, ad.isTask());
    }

    /**
     * Test of isTaskContainer method, of class AntDocMacrodefImp.
     */
    @Test
    public void testIsTaskContainer() throws Exception {
        System.out.println("isTaskContainer");
        parse(FULL);
        assertEquals("", false, ad.isTaskContainer());
    }

    /**
     * Test of sourceIncluded method, of class AntDocMacrodefImp.
     */
    @Test
    public void testSourceIncluded() throws Exception {
        System.out.println("sourceIncluded");
        parse(FULL);
        assertEquals("", false, ad.sourceIncluded());
    }

    /**
     * Test of supportsCharacters method, of class AntDocMacrodefImp.
     */
    @Test
    public void testSupportsCharacters() throws Exception {
        System.out.println("supportsCharacters");
        parse(FULL);
        assertEquals("", false, ad.supportsCharacters());
    }

    /**
     * Test of compareTo method, of class AntDocMacrodefImp.
     */
    @Test
    public void testCompareTo() throws Exception {
        System.out.println("compareTo");
    }

    /**
     * Test of toString method, of class AntDocMacrodefImp.
     */
    @Test
    public void testToString() throws Exception {
        System.out.println("toString");
    }

    private void parse(String s) throws Exception {
        ad = AntDocMacrodef.valueOf(XpathHelper.get("//macrodef",
                new InputSource(new StringReader(s)))[0]);
    }
}
