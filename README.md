
Json to Xmi converter
=====================

Java program to convert json data extracted from COBHUNI wiki into xmi.

* The section and page information is converted into offset annotations.
* The text is tagged using the Stanford Parser configured for Arabic.

## Usage

```sh
$ mvn clean dependency:copy-dependencies package
$ java -cp target/dependency/*:target/xmiconverter-0.0.1-SNAPSHOT.jar xmiconverter/XMIConverter
```