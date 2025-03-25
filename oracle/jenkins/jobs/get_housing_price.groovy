pipelineJob('get-housing-price') {
    displayName('get-housing-price')
    description('get-housing-price')
    triggers {
        cron('0 10,22 * * *')
    }
    definition {
        cps {
            script(
                '''
                pipeline {
                    agent any
                    stages {
                        stage('version') {
                        steps {
                                sh 'python3 --version'
                            }
                        }
                        stage('Get price') {
                            steps {
                                sh 'python3 get_housing_price/get_price.py'
                            }
                        }
                    }
                }
                '''
            )
        }
    }
}
