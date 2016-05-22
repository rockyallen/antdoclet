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
package com.neuroning.antdoclet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author rocky
 */
public class AntRootTest {

    AntRoot instance = new AntRoot();
    Collection<AntDoc> docs = null;

    public AntRootTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        instance.loadMacrodefs(new File("testdata/dummy-build.xml"));
        docs = instance.getAllDocs();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldContainMacrodefsFromFileAsTasks() throws Exception {
        assertEquals("", 3, docs.size());        
        assertNotNull("contains full",getByName(docs, "full"));
        assertNotNull("contains call-cc",getByName(docs, "call-cc"));
        assertNotNull("contains testing",getByName(docs, "testing"));
    }
    
    private AntDoc getByName(Collection<AntDoc> docs, String name) throws Exception {
        for (AntDoc ad : docs) {
            if (name.equals(ad.getAntName())) {
                return ad;
            }
        }
        return null;
    }
}
