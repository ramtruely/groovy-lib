def createArtifact(String name) {
    def workspace = new File(".").canonicalPath
    def buildDir = new File("build")

    if (!buildDir.exists()) {
        buildDir.mkdirs()
    }

    def artifactFile = new File(buildDir, "${name}.txt")

    artifactFile.text = """
Artifact Name : ${name}
Created At    : ${new Date()}
Workspace     : ${workspace}
""".stripIndent().trim()

    println "Artifact created at: ${artifactFile.absolutePath}"
}

return this
