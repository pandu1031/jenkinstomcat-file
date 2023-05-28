//Declarative pipeline
pipeline{
    agent any
    parameters {
        string(name:'BRANCH_NAME', defaultValue: 'source', description: 'Enter source branch name')
        string(name:'BUILD_PIPE', defaultValue: 'pipeline', description: 'Enter pipeline branch name')
        string(name:'SERVER_IP', defaultValue: '', description: 'Enter server ip')
    }

    stages{
        stage("clone code"){
            steps{
                println "here im cloning the code from github"
                git branch: '$BRANCH_NAME', 
                     url: "https://github.com/pandu1031/boxfuse-sample-java-war-hello.git"
            }
        }
        stage("Build"){
            steps{
                println "here im building the code "
                sh "mvn clean package"
                sh " ls -l target"
                
            }
        }
        stage("Upload artifacts"){
            steps{
                println "here im uploading artifacts to s3 bucket"
                sh "aws s3 ls"
                sh "aws s3 ls s3://mamuu"
                sh "aws s3 cp target/hello-${BUILD_NUMBER}.war s3://mamuu/chintu/${BRANCH_NAME}/${BUILD_NUMBER}" 
            }
        }
        stage("Deployment"){
            steps{
                println "deploying the artifacts to tomcat server"
                sh "scp -i /tmp/mamu1031.pem/tmp/tomcatinstall.sh ec2-user@${SERVER_IP}:/tmp/"
                sh "ssh -i /tmp/mamu1031.pem ec2-user@${SERVER_IP} \"bash /tmp/tomcatinstall.sh  && systemctl status tomcat\""
            }
        }

        }

    }
