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
        stage("Release") {
            steps {
                withCredentials([
                        string(credentialsId: 'curse_api_key', variable: 'curse_api_key'),
                        string(credentialsId: 'github_release_token', variable: 'github_release_token')]) {
                    script {
                        def changes = ""
                        for (changeLog in currentBuild.changeSets) {
                            for(entry in changeLog.items) {
                                changes += "${entry.msg}\r\n".replace('\'', '\\\'')
                            }
                        }
                        sh "gradle build check curseforge githubRelease --stacktrace --info -PBUILD_NO=$BUILD_NUMBER -Pcurse_api_key=$curse_api_key -Pgithub_release_token=$github_release_token -Pchangelog='$changes'"
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
