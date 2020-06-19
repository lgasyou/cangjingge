pipeline {
    agent {
        docker {
            image 'maven:3-jdk-12-alpine'
            args '-v /root/.m2:/root/.m2 -v /usr/bin/docker:/usr/bin/docker -v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    stages {
        stage('Build') { 
            steps {
                sh './jenkins/scripts/build.sh' 
            }
        }
        stage('Deliver') {
            steps {
                sh './jenkins/scripts/deliver.sh' 
            }
        }
    }
}
