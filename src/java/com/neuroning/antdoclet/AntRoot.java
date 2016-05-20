/**
 *  Copyright (c) 2003-2005 Fernando Dobladez
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
 *
 */
package com.neuroning.antdoclet;

import java.util.SortedSet;
import java.util.TreeSet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    //private final RootDoc rootDoc;
    private final SortedSet<AntDoc> all;
    private final SortedSet<AntDoc> allTypes;
    private final SortedSet<AntDoc> allTasks;
    private final SortedSet<String> categories;

    public AntRoot(RootDoc rootDoc) throws ClassNotFoundException, InstantiationException {
        //this.rootDoc = Util.notNull(rootDoc, "rootDoc");
        all = new TreeSet<AntDoc>();
        allTypes = new TreeSet<AntDoc>();
        allTasks = new TreeSet<AntDoc>();
        categories = new TreeSet<String>();

        // ALL classes and interfaces
        ClassDoc[] classes = rootDoc.classes();
        for (ClassDoc classe : classes) {
            AntDoc d = AntDoc.getInstance(classe.qualifiedName(), rootDoc);
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

    public Iterator<AntDoc> getTypes() {
        return allTypes.iterator();
    }

    public Iterator<AntDoc> getTasks() {
        return allTasks.iterator();
    }

    public Iterator<AntDoc> getAllByCategory(String category) throws ClassNotFoundException, InstantiationException {
        // give category "all" a special meaning:
        if ("all".equals(category)) {
            return getAll();
        }

        return getByCategory(category, all);
    }

    public Iterator<AntDoc> getTypesByCategory(String category) throws ClassNotFoundException, InstantiationException {
        // give category "all" a special meaning:
        if ("all".equals(category)) {
            return getTypes();
        }

        return getByCategory(category, allTypes);
    }

    public Iterator<AntDoc> getTasksByCategory(String category) throws ClassNotFoundException, InstantiationException {
        // give category "all" a special meaning:
        if ("all".equals(category)) {
            return getTasks();
        }

        return getByCategory(category, allTasks);
    }

    private Iterator<AntDoc> getByCategory(String category, Set<AntDoc> antdocs) throws ClassNotFoundException, InstantiationException {
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
}