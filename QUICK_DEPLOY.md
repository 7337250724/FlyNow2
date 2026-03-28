# Quick Deployment Guide for External Tomcat

## Changes Made

✅ **pom.xml**: 
- Changed packaging from `jar` to `war`
- Excluded embedded Tomcat
- Added Tomcat as `provided` scope dependency

✅ **FlyNowApplication.java**: 
- Extended `SpringBootServletInitializer` for WAR deployment

## Quick Steps to Deploy

### 1. Build the WAR File

Open terminal in `FlyNow2` directory and run:

```bash
mvn clean package -DskipTests
```

This creates: `target/FlyNow-0.0.1-SNAPSHOT.war`

### 2. Deploy to Tomcat

**Option A: Simple Copy**
1. Stop Tomcat
2. Copy `FlyNow-0.0.1-SNAPSHOT.war` to `[TOMCAT_HOME]/webapps/`
3. Rename to `FlyNow.war` (or `ROOT.war` for root context)
4. Start Tomcat
5. Access at: `http://localhost:8080/FlyNow`

**Option B: Tomcat Manager**
1. Access: `http://localhost:8080/manager/html`
2. Upload WAR file using "WAR file to deploy"
3. Application will auto-deploy

### 3. Important Notes

- **Database**: Update `application.properties` with your MySQL credentials
- **Port**: Application will use Tomcat's port (default: 8080), not 8081
- **Context Path**: Will be `/FlyNow` (or `/` if named `ROOT.war`)
- **Static Files**: Included in WAR automatically
- **Logs**: Check `[TOMCAT_HOME]/logs/catalina.out` for errors

### 4. Verify Deployment

1. Check Tomcat logs for startup messages
2. Access: `http://localhost:8080/FlyNow`
3. Test login and package browsing

## Troubleshooting

- **404 Error**: Check context path matches WAR filename
- **Database Error**: Verify MySQL is running and credentials are correct
- **ClassNotFoundException**: Rebuild WAR with `mvn clean package`
- **Port Conflict**: Change Tomcat port in `conf/server.xml`

For detailed instructions, see `DEPLOYMENT_GUIDE.md`

