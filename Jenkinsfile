/*
  A simple Jenkins pipeline build instruction that does the following:
	- perform code checkout from Git repo
  - run junit test and publish test results
	- build the sample Java SpringBoot application using maven
	- deploy the web application (war) to a Tomcat server running in an AWS EC2 container
	- send an email alert if the deployment fails
*/
pipeline {

  agent any

  environment {
	GIT_REPO_URL      = 'https://github.com/OneStopDevOps/ci-cd-jenkins-pipeline.git'
	JENKINS_WORKSPACE = 'Project3-CI-CD-Jenkins-Pipeline/inventory-service/target'
	EC2_IP_ADDRESS    = 'ec2-54-183-206-136.us-west-1.compute.amazonaws.com'
	EC2_USER          = 'ec2-user'
  }

  stages {

    stage('Stage 1 - Clone docker-jenkins-pipeline') {

      steps {

        sendStartNotification()

        echo "Checking out from github repo..."
        git branch: 'master', url: "${GIT_REPO_URL}"
      }
    }

    stage('Stage 2 - Executing unit testing suite') {

      // only execute this stage if the previous stages are successful.
      when {
        expression {
          currentBuild.result == null || currentBuild.result == 'SUCCESS'
        }
      }

      steps {
         echo "Executing unit testing for inventory-service ..."

         dir('inventory-service') {
          sh 'pwd'
          sh 'mvn test'
         }
      }
    }
 
    stage('Stage 3 - Building the inventory-service') {

      // only execute this stage if the previous stages are successful.
      when {
        expression {
          currentBuild.result == null || currentBuild.result == 'SUCCESS'
        }
      }

      steps {

        echo "Building inventory-service war file ..."
        dir('inventory-service') {
          sh 'pwd'
          sh 'mvn clean package'
        }
      }
    }

    stage('Stage 4 - Deploy to tomcat container on AWS EC2...') {
      
      // only execute this stage if the previous stages are successful.
      when {
        expression {
          currentBuild.result == null || currentBuild.result == 'SUCCESS'
        }
      }

      steps {
	    echo "Deploying war to tomcat container."
	    
	    dir('inventory-service/target') {
	        sshagent(['jenkins-ssh-ec2-user']) {
				sh "scp inventory-service.war ${EC2_USER}@${EC2_IP_ADDRESS}:/home/${EC2_USER}/apache-tomcat-9.0.46/webapps"
			}
	    }
      } 
    }

  }

  post {
  
    success {
      echo "Build completed."
      sendSuccessNotification()
    }

    failure {
      echo "Build failed."
      sendFailNotification()
    }
    
  }

}

/*
   Send out start build event notification thru email
*/
def sendStartNotification() {

  emailext( 
    subject: "STARTED Job: '${env.JOB_NAME} [${env.JOB_NUMBER}]'",
    body: """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
      <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
    to: '$DEFAULT_RECIPIENTS',
    recipientProviders: [[$class: 'DevelopersRecipientProvider']] 
  )
}

/*
  Send out fail build event notification thru email
*/
def sendFailNotification() {

  emailext (
    subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
    body: """<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
      <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
    to: '$DEFAULT_RECIPIENTS',
    recipientProviders: [[$class: 'DevelopersRecipientProvider']]
  )
}

/*
  Send out success build event notification thru email
*/
def sendSuccessNotification() {

  emailext (
    subject: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
    body: """<p>SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
      <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
    to: '$DEFAULT_RECIPIENTS',
    recipientProviders: [[$class: 'DevelopersRecipientProvider']]
  )
} 
