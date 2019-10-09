; 
; JavaLDAP Simple Build BAT file
;
set PATH=d:\jdk1.3\bin;%PATH%
set CLASSPATH=ibmasn1.jar;xerces.jar;.
javac org\javaldap\btree\*.java
javac org\javaldap\ldapv3\*.java
javac org\javaldap\server\*.java
javac org\javaldap\server\acl\*.java
javac org\javaldap\server\backend\*.java
javac org\javaldap\server\operation\*.java
javac org\javaldap\server\schema\*.java
javac org\javaldap\server\syntax\*.java
javac org\javaldap\server\util\*.java
