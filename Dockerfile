FROM openjdk:8
EXPOSE 8080
ADD target/emp-org-chart.jar emp-org-chart.jar
ENTRYPOINT [ "java","-jar","/emp-org-chart.jar" ]



