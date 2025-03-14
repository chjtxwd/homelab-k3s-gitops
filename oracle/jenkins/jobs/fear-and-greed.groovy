multibranchPipelineJob('fear-and-greed') {
    displayName('fear-and-greed')
    description('fear-and-greed')
    branchSources {
        git {
            id('fear-and-greed')
            remote('https://github.com/chjtxwd/homelab-k3s-gitops')
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(0)
        }
    }
    configure { node ->
        node / sources / data / 'jenkins.branch.BranchSource' / source / traits {
            'jenkins.plugins.git.traits.BranchDiscoveryTrait'()
        }
    }
}
