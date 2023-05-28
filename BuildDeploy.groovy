//Declarative pipeline
pipeline{
    agent any
    parameters {
        string(name:'BRANCH_NAME', defaultValue: 'source', description: 'Enter source branch name')
        string(name:'BUILD_PIPE', defaultValue: 'pipeline', description: 'Enter pipeline branch name')
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
                println "here im uploading artifacts from jenkins server to tomcat"
                sh "aws s3 ls"
                sh "aws s3 ls s3://mamuu"
                sh "aws s3 cp target/hello-${BUILD_NUM}.war s3://mamuu/chintu/${BRANCH_NAME}/${BUILD_NUM}" 
            }
        }

        }

    }
