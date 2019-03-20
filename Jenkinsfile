pipeline {
    agent {
        docker {
            image 'gradle:5.2-jdk8-alpine'
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
                // git branch: 'develop',
                //     credentialsId: 'GITHUB_CREDENTIALS',
                //     url: 'https://github.com/Hea3veN/DulceDeLeche'
            }
        }
        stage("Clean") {
            steps {
                sh "gradle clean --stacktrace --info -PBUILD_NO=$BUILD_NUMBER -Dorg.gradle.jvmargs=-Xmx1g"
            }
        }
        stage("Build") {
            steps {
                sh "gradle build check --stacktrace --info -PBUILD_NO=$BUILD_NUMBER -Dorg.gradle.jvmargs=-Xmx1g"
            }
        }
        stage("Release to curseforge") {
            steps {
                withCredentials([string(credentialsId: 'curse_api_key', variable: 'curse_api_key')]) {
					sh "gradle curseforge -Pcurse_api_key=$curse_api_key --stacktrace --info -PBUILD_NO=$BUILD_NUMBER -Dorg.gradle.jvmargs=-Xmx1g"
				}
            }
        }
        stage("Release to github") {
            steps {
                withCredentials([string(credentialsId: 'github_release_token', variable: 'github_release_token')]) {
					sh "gradle githubRelease -Pgithub_release_token=$github_release_token --stacktrace --info -PBUILD_NO=$BUILD_NUMBER -Dorg.gradle.jvmargs=-Xmx1g"
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
