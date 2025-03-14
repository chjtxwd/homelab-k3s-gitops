pipelineJob('fear-and-greed') {
    displayName('fear-and-greed')
    description('fear-and-greed')
    definition {
        cps {
            script(
                '''
                pipeline {
                    agent any
                    stages {
                        stage('Build') {
                            steps {
                                echo 'Building..'
                            }
                        }
                        stage('Test') {
                            steps {
                                echo 'Testing..'
                            }
                        }
                        stage('Deploy') {
                            steps {
                                echo 'Deploying..'
                            }
                        }
                    }
                }
                '''
            )
        }
    }
}
