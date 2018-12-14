pipeline {
    agent {
        docker {
            image 'gradle:4.10-jdk8-alpine'
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    tools {
        gradle "gradle"
    }

    stages {
        stage("Checkout") {
            steps {
                checkout scm
            }
        }
        stage("Build") {
            steps {
                sh "gradle build --stacktrace --info -PBUILD_NO=$BUILD_NUMBER"
            }
        }
        stage("Test") {
            steps {
                sh "gradle check --stacktrace --info -PBUILD_NO=$BUILD_NUMBER"
            }
        }
        stage("Publish to Curseforge") {
            steps {
                withCredentials([string(credentialsId: 'curse_api_key', variable: 'curse_api_key')]) {
                    script {
                        def changes = ""
                        for (changeLog in currentBuild.changeSets) {
                            for(entry in changeLog.items) {
                                changes += "${entry.msg}\r\n"
                            }
                        }
                        sh "gradle curseforge --stacktrace --info -PBUILD_NO=$BUILD_NUMBER -Pcurse_api_key=$curse_api_key -Pchangelog='$changes'"
                    }
                }
            }
        }
        stage("Publish to Github") {
            steps {
                withCredentials([string(credentialsId: 'github_release_token', variable: 'github_release_token')]) {
                    script {
                        def changes = ""
                        for (changeLog in currentBuild.changeSets) {
                            for(entry in changeLog.items) {
                                changes += "${entry.msg}\r\n".replace('\'', '\\\'')
                            }
                        }
                        sh "gradle githubRelease --stacktrace --info -PBUILD_NO=$BUILD_NUMBER -Pgithub_release_token=$github_release_token -Pchangelog='$changes'"
                    }
                }
            }
        }
    }
    
    post {
        success {
            archiveArtifacts 'build/libs/**/*.jar'
        }
    }
}
