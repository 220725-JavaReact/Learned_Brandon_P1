# From instructions is used to download whatever dependencies our application needs
# in this case, we need tomcat and JRE to run our java servlet applciation
FROM tomcat:8.5-jre8

# Copy instructions is used to copy whatever is on your computer and paste it into the image
copy /target/Learned_Brandon_P1.war /usr/local/tomcat/webapps

# expose instruction will determine what port this container will open to
expose 8080

CMD ["catalina.sh", "run"]