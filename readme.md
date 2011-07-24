WAR Bootstrap
=============

Environment
-----------

- Java SE JDK 1.6u26
	http://www.oracle.com/technetwork/java/javase/downloads/
- Maven 3.0.3
	http://maven.apache.org/download.html

Usage
-----

	# generate a new WAR bootstrap
	mvn archetype:generate \
		-DarchetypeGroupId=org.duelengine \
		-DarchetypeArtifactId=war-bootstrap-archetype \
		-DarchetypeVersion=0.2.1

	# build your boostrap
	# NOTE: replace "bootstrap" with your chosen project name
	cd bootstrap; mvn package; cd ..

	# run the resulting WAR on the test bootstrap
	# NOTE: again replace with your chosen artifact names
	java -jar bootstrap/target/bootstrap.jar -war path/to/myapp.war -p 8080 -c /myapp --jetty
