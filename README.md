
AntDoclet is a tool to automatically generate documentation out of your
[Ant](http://ant.apache.org) Tasks' source code.

It is implemented as a [Javadoc](http://java.sun.com/j2se/javadoc/) doclet,
and generates reference documentation and other deliverables from the
source code of your custom Ant Tasks/Types. It's been only tested with
JDK 1.4.

It uses template-based generation, using
[Velocity](http://velocity.apache.org/). This makes it simple to
create new deliverables or modify the ones provided.  The example
templates generate HTML and LaTeX (PDF) and are located under the
`templates/` directory.


Quick Start
-----------

The `example.build.xml` file is an example of how to run the doclet from
Ant. You must change some properties to fit your needs, like the path to
your tasks source code and compiled classes. If you need AntDoclet, I assume
you are comfortable with Ant scripts ;-).

IMPORTANT: The doclet needs access to both the source code _and_
the compiled version of the tasks/types to document. This is so because
AntDoclet uses the same runtime-reflection mechanism that Ant itself uses
to find valid properties and nested elements from each task/type.


Ant-specific Javadoc tags
-------------------------

AntDoclet expects some ant-specific tags to build richer documentation:

* The javadoc comment of a class should have either the tag `@ant.task`
  (for Tasks) or tag `@ant.type` (for Types used from ant
  Tasks). Both accept three "attributes": `category, name, ignored`. Examples:

        @ant.task name="copy" category="filesystem"
        @ant.type name="fileset" category="filesystem"
        @ant.task ignore="true"
 
  When `ignore` is true, the class will not be included in the
  documentation. This is useful for ignoring abstract classes or
  tasks/types that are not to be exposed in the docs.

* The tasks/types properties documentation is extracted from the
  properties setter/getter methods' comments (the setter comment has
  precedence). Two additional tags are accepted here:

        @ant.required 
        @ant.not-required

  used to indicate that the property is (or not) required.

  Additional comment can be added to the tag. Example:

        /**
         * Overwrite any existing destination file(s).
         *
         * If true force overwriting of destination file(s)
         * even if the destination file(s) are younger than
         * the corresponding source file.
         *
         * @ant.not-required Default is false.
         */
        public void setOverwrite(boolean overwrite) {
            this.forceOverwrite = overwrite;
        }



The javadoc comments must be valid XHTML, otherwise, the templates'
output may be broken. Some suggestions:

* Make sure all HTML tags are "balanced" (properly open and close each
    tag).
* Separate paragraphs should be written between `<p> </p>`.
* Use `<code> </code>` for variable and file names.
* For displaying source code in a nice box (like code examples) use
  `<pre> </pre>`.
* Remember to escape all necessary characters to make it valid HTML (`&lt;` instead of `<`, etc.)



<hr />

Thanks,

Fernando Dobladez (<http://code54.com>)

