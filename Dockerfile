FROM tomcat:10.1
COPY target/*.war /usr/local/tomcat/webapps/
COPY mysql-connector-j-9.1.0.jar /usr/local/tomcat/lib/
EXPOSE 8080
CMD ["catalina.sh", "run"]
