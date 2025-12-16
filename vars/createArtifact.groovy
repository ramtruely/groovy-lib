import ci.ArtifactService

return { String artifactName ->
    println "[createArtifact] Step started"
    ArtifactService.create(artifactName)
    println "[createArtifact] Step completed"
}
