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
                        stage("Checkout the Base Image repo") {
                            steps {
                                script {
                                    gitInfo = checkout([$class: 'GitSCM',
                                        branches: [[name: "*/main"]],
                                        doGenerateSubmoduleConfigurations: false,
                                        extensions: [
                                            [$class: 'CleanCheckout'],
                                            [$class: 'CloneOption',
                                                depth: 1,
                                                noTags: true,
                                                reference: '',
                                                shallow: true
                                            ]
                                        ],
                                        submoduleCfg: [],
                                        userRemoteConfigs: [[url: 'https://github.com/chjtxwd/homelab-k3s-gitops.git']]
                                    ])
                                }
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
