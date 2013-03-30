WAR Bootstrap
=============

Environment
-----------

- Java SE JDK 1.6
	http://www.oracle.com/technetwork/java/javase/downloads/
- Maven 3.0.x
	http://maven.apache.org/download.html

Usage
-----

	# generate a new WAR bootstrap
	mvn archetype:generate \
		-DarchetypeGroupId=org.duelengine \
		-DarchetypeArtifactId=war-bootstrap-archetype \
		-DarchetypeVersion=0.5.0

	# build your boostrap
	# NOTE: replace "bootstrap" with your chosen project name
	cd bootstrap; mvn package; cd ..

	# run the resulting WAR on the test bootstrap
	# NOTE: again replace with your chosen artifact names
	java -jar bootstrap/target/bootstrap.jar -p 8080 --tomcat \
		-war /=path/to/root.war \
		-war /myapp=path/to/myapp.war

JSP in Tomcat
-------------

If you want to support JSP within Tomcat, **uncomment** the `org.apache.tomcat.embed:tomcat-embed-jasper` dependency (it is commented out by default). Otherwise, it will load a lighter set of resources.

Glassfish 3.1
-------------

There is a conflict between the classes in embedded Glassfish and embedded Tomcat. As a result, the `pom.xml` needs to be modified to include/exclude the dependencies for one or the other. Currently, this defaults to Tomcat.

NOTE: there seems to be an issue with the Glassfish configuration as it isn't correctly loading anymore.