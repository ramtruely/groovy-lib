class FileDeployer {

    static void deploy(File artifact, File targetDir) {
        if (!artifact.exists()) {
            throw new RuntimeException("Artifact not found: ${artifact.name}")
        }

        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }

        def target = new File(targetDir, artifact.name)
        target.bytes = artifact.bytes

        println "Deployed ${artifact.name} to ${targetDir.canonicalPath}"
    }
}
