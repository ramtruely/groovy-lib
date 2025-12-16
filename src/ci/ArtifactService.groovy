package ci

class ArtifactService {

    static void create(String name) {
        println "[ArtifactService] Creating artifact: ${name}"

        def file = new File("build/${name}.txt")
        file.parentFile.mkdirs()
        file.text = """
Artifact Name : ${name}
Created At    : ${new Date()}
Workspace     : ${new File('.').canonicalPath}
"""

        println "[ArtifactService] Artifact created at: ${file.path}"
    }
}
