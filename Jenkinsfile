pipeline {
    agent {
        docker {
            image 'maven:3-jdk-12-alpine'
            args '-v /root/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    stages {
        stage('Prerequisites') {
            steps {
                sh './jenkins/scripts/init.sh'
                withCredentials([usernamePassword(credentialsId: 'docker_hub', passwordVariable: 'password', usernameVariable: 'username')]) {
                    sh 'docker login -u $username -p $password'
                }
            }
        }
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
