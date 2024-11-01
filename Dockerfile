FROM tomcat:10.1
COPY target/*.war /usr/local/tomcat/webapps/
COPY .env /usr/local/tomcat/
EXPOSE 8080
CMD ["catalina.sh", "run"]
