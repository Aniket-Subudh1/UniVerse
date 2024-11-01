FROM tomcat:10.1
COPY target/*.war /usr/local/tomcat/webapps/
ADD mysql-connector-java-8.0.26.jar /usr/local/tomcat/lib/
EXPOSE 8080
CMD ["catalina.sh", "run"]

