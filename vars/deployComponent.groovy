import FileDeployer

def deploy(String artifactName, String env) {
    def artifact = new File("build/${artifactName}.txt")
    def targetDir = new File("env/${env}")

    FileDeployer.deploy(artifact, targetDir)
}

return this
