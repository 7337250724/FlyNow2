#!/bin/bash
echo "Building WAR file for Tomcat deployment..."
mvn clean package -DskipTests
echo ""
echo "WAR file created at: target/FlyNow-0.0.1-SNAPSHOT.war"
echo ""
echo "To deploy:"
echo "1. Copy the WAR file to your Tomcat webapps folder"
echo "2. Rename it to FlyNow.war (or ROOT.war for root context)"
echo "3. Start Tomcat"
echo ""

