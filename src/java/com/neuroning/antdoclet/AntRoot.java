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

import java.util.SortedSet;
import java.util.TreeSet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * An object of this class represents a set of Java classes that are an Ant Task
 * and an Ant Types.
 *
 * It's mainly a wrapper around a RootDoc instance, adding methods for
 * traversing the RootDoc objects sorted by Ant-specific features (task name,
 * category, etc)
 *
 * @author Fernando Dobladez dobladez@gmail.com
 */
public class AntRoot {

    private final SortedSet<AntDoc> all;
    private final SortedSet<AntDoc> allTypes;
    private final SortedSet<AntDoc> allTasks;
    private final SortedSet<String> categories;

    public AntRoot() {
        all = new TreeSet<AntDoc>();
        allTypes = new TreeSet<AntDoc>();
        allTasks = new TreeSet<AntDoc>();
        categories = new TreeSet<String>();
    }

    public void load(RootDoc rootDoc) throws Exception {

        // ALL classes and interfaces
        ClassDoc[] classes = rootDoc.classes();
        for (ClassDoc classe : classes) {
            AntDoc d = AntDocJavadoc.getInstance(classe.qualifiedName(), rootDoc);
            if (d != null) {
                all.add(d);
                if (d.getAntCategory() != null) {
                    categories.add(d.getAntCategory());
                }
                if (d.isTask()) {
                    allTasks.add(d);
                } else {
                    allTypes.add(d);
                }
            }
        }
    }

    public Iterator<String> getCategories() {
        return categories.iterator();
    }

    public Iterator<AntDoc> getAll() {
        return all.iterator();
    }

    public Collection<AntDoc> getAllDocs() {
        return all;
    }

    public Iterator<AntDoc> getTypes() {
        return allTypes.iterator();
    }

    public Iterator<AntDoc> getTasks() {
        return allTasks.iterator();
    }

    public Iterator<AntDoc> getAllByCategory(String category) throws Exception {
        // give category "all" a special meaning:
        if ("all".equals(category)) {
            return getAll();
        }

        return getByCategory(category, all);
    }

    public Iterator<AntDoc> getTypesByCategory(String category) throws Exception {
        // give category "all" a special meaning:
        if ("all".equals(category)) {
            return getTypes();
        }

        return getByCategory(category, allTypes);
    }

    public Iterator<AntDoc> getTasksByCategory(String category) throws Exception {
        // give category "all" a special meaning:
        if ("all".equals(category)) {
            return getTasks();
        }

        return getByCategory(category, allTasks);
    }

    private Iterator<AntDoc> getByCategory(String category, Set<AntDoc> antdocs) throws Exception {
        List<AntDoc> filtered = new ArrayList<AntDoc>();

        Iterator<AntDoc> it = antdocs.iterator();
        while (it.hasNext()) {
            AntDoc d = it.next();
            if (category.equals(d.getAntCategory())) {
                filtered.add(d);
            }
        }

        return filtered.iterator();
    }

    /**
     * Read macrodefs from f, convert them to AntDocs and add them to the
     * appropriate AntDoc lists.
     *
     * @param f
     * @throws FileNotFoundException
     */
    public void loadMacrodefs(File f) throws Exception {
        if (!f.exists()) {
            throw new FileNotFoundException(f.getAbsolutePath());
        } else {
            for (Node macrodef : XpathHelper.get("//macrodef", new InputSource(new FileReader(f)))) {
                AntDoc ad = AntDocMacrodef.valueOf(macrodef);
                all.add(ad);
                allTasks.add(ad);
                categories.add(ad.getAntCategory());
            }
        }
    }
}
