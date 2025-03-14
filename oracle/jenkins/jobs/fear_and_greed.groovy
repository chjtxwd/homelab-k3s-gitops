pipelineJob('fear-and-greed') {
    displayName('fear-and-greed')
    description('fear-and-greed')
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/chjtxwd/homelab-k3s-gitops')
                    }
                    branches('*/main')
                    scriptPath('Jenkinsfile')
                }
            }
        }
    }
    }
    steps {
        shell('echo "Building the project..."')
        shell('pwd')
        shell('ls -la')
    }
}
