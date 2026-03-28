# FlyNow Deployment Guide for External Tomcat

This guide will help you deploy the FlyNow Spring Boot application to an external Tomcat server.

## Prerequisites

1. **Java 17 or higher** - Make sure Java 17+ is installed and `JAVA_HOME` is set
2. **Apache Tomcat 10.x** - Download from https://tomcat.apache.org/download-10.cgi
3. **MySQL Database** - Ensure MySQL is running and accessible
4. **Maven** - For building the WAR file

## Step 1: Build the WAR File

1. Open a terminal/command prompt in the `FlyNow2` directory
2. Run the following Maven command to build the WAR file:

```bash
mvn clean package
```

This will create a WAR file at: `FlyNow2/target/FlyNow-0.0.1-SNAPSHOT.war`

## Step 2: Configure Tomcat

1. **Set JAVA_HOME** (if not already set):
   - Windows: Set environment variable `JAVA_HOME` to your JDK installation path
   - Example: `C:\Program Files\Java\jdk-17`

2. **Configure Tomcat Memory** (Optional but recommended):
   - Edit `bin/setenv.bat` (Windows) or `bin/setenv.sh` (Linux/Mac)
   - Add:
   ```bash
   set JAVA_OPTS=-Xms512m -Xmx1024m
   ```

## Step 3: Deploy the WAR File

### Option A: Copy WAR to webapps folder

1. Stop Tomcat if it's running
2. Copy `FlyNow-0.0.1-SNAPSHOT.war` to Tomcat's `webapps` directory
3. Rename it to `FlyNow.war` (or `ROOT.war` if you want it at root context)
4. Start Tomcat
5. The application will be available at:
   - `http://localhost:8080/FlyNow` (if named FlyNow.war)
   - `http://localhost:8080` (if named ROOT.war)

### Option B: Use Tomcat Manager (Recommended)

1. Access Tomcat Manager at: `http://localhost:8080/manager/html`
2. You may need to configure users in `conf/tomcat-users.xml`:
   ```xml
   <role rolename="manager-gui"/>
   <role rolename="manager-script"/>
   <user username="admin" password="yourpassword" roles="manager-gui,manager-script"/>
   ```
3. Use the "Deploy" section to upload the WAR file
4. Or use the "WAR file to deploy" option to browse and deploy

## Step 4: Configure Database Connection

1. Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/flynow_db?createDatabaseIfNotExist=true
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

2. **OR** use Tomcat's context configuration:
   - Create `META-INF/context.xml` in your WAR or
   - Create `conf/Catalina/localhost/FlyNow.xml` in Tomcat with:
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <Context>
       <Resource name="jdbc/flynow_db"
                 auth="Container"
                 type="javax.sql.DataSource"
                 driverClassName="com.mysql.cj.jdbc.Driver"
                 url="jdbc:mysql://localhost:3306/flynow_db"
                 username="your_username"
                 password="your_password"
                 maxTotal="20"
                 maxIdle="10"
                 maxWaitMillis="10000"/>
   </Context>
   ```

## Step 5: Verify Deployment

1. Check Tomcat logs in `logs/catalina.out` for any errors
2. Access the application:
   - `http://localhost:8080/FlyNow` (or your configured context path)
3. Test the application functionality

## Troubleshooting

### Common Issues:

1. **ClassNotFoundException or NoClassDefFoundError**
   - Ensure all dependencies are included in the WAR
   - Check that `packaging` is set to `war` in pom.xml

2. **Port Already in Use**
   - Change Tomcat port in `conf/server.xml`:
   ```xml
   <Connector port="8080" protocol="HTTP/1.1" ... />
   ```

3. **Database Connection Errors**
   - Verify MySQL is running
   - Check database credentials
   - Ensure MySQL connector JAR is in Tomcat's `lib` folder (if using JNDI)

4. **Static Resources Not Loading**
   - Ensure static files are in `src/main/resources/static`
   - Check that Thymeleaf templates are in `src/main/resources/templates`

5. **Context Path Issues**
   - If you want root context, rename WAR to `ROOT.war`
   - Or configure context path in `application.properties`:
   ```properties
   server.servlet.context-path=/FlyNow
   ```

## Production Recommendations

1. **Remove DevTools** from production build:
   - Already set to `optional=true` in pom.xml, but ensure it's not included

2. **Enable Template Caching**:
   ```properties
   spring.thymeleaf.cache=true
   ```

3. **Configure Logging**:
   - Add `logback-spring.xml` for production logging

4. **Security**:
   - Change default passwords
   - Configure HTTPS
   - Set up firewall rules

5. **Performance**:
   - Configure connection pooling
   - Enable JPA second-level cache
   - Optimize database queries

## Building for Production

To build without tests and with production settings:

```bash
mvn clean package -DskipTests -Pproduction
```

## Notes

- The application will run on the port configured in Tomcat (default: 8080)
- Make sure your MySQL database is accessible from the Tomcat server
- Static files and templates are included in the WAR file automatically
- The application context path will be the WAR filename (without .war extension)

