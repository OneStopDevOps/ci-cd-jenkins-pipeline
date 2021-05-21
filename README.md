# CalTech DevOps Project 3 - CI/CD Jenkins Pipeline with 

## Project Description:

This is a simple project that demonstrates how to maintain a continuous integration and delivery processes by using Jenkins pipeline and docker.
The Jenkins pipeline will do the following tasks:

 - perform code checkout from Git repo
 - build the sample Java SpringBoot application using maven
 - run junit test and publish test results
 - deploy the web application (war) to AWS EC2 instance on a Tomcat server
 - send an email alert if the deployment fails
