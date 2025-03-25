pipelineJob('get-housing-price') {
    displayName('get-housing-price')
    description('get-housing-price')
    triggers {
        cron('H 10,22 * * *')
    }
    definition {
        cps {
            script(
                '''
                pipeline {
                    agent any
                    stages {
                        stage('Clone repository') {
                            steps {
                                sh """
                                    apt update
                                    apt install -y git
                                    git clone --depth 1 https://github.com/chjtxwd/homelab-k3s-gitops.git
                                """
                            }
                        }
                        stage('install dependencies') {
                            steps {
                                sh """
                                    apt update
                                    apt install -y python3
                                """
                            }
                        }
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
