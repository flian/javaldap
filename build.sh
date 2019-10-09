#!/bin/sh
#
#
# Trivial Build Script

# Edit Classpath to include IBM ASN.1 stuff, Xerces-J, and
# any necessary JDBC classes

CLASSPATH=.:./ibmasn1.jar:./xerces.jar
export CLASSPATH

# Compile all .java files under org
for i in `find org -name *.java -print`
do
  echo "Compiling $i."
  javac $i
done
