#!/bin/sh
#
# Start the Java LDAP Server
#

# The program you use to run Java applications. I use the IBM version
# of JDK 1.1.6, as it is lightning fast. Other 1.1.x versions may work
# as well.
JAVA=java
#JAVA=jre

# Change to reflect the path to the classes directory from Snacc4Java
# If you didn't download it yet, go to http://www.alphaworks.ibm.com/.
#SNACC_CLASSES=./Snacc4java/classes
SNACC_CLASSES=./ibmasn1.jar

# Where to find the Xerces-J classes. Available from xml.apache.org
# in binary or source form.
XERCES_CLASSES=./xerces.jar

# Might want to change to reflect directory containing javaldap class
# files. It should be the current directory.
JAVALDAP_CLASSES=.

# Shouldn't need to change this unless your Java environment requires
# you to add classes.zip or such.
CLASSPATH=$SNACC_CLASSES:$JAVALDAP_CLASSES:$XERCES_CLASSES
export CLASSPATH

$JAVA org.javaldap.server.LDAPServer
