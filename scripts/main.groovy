import groovy.lang.GroovyClassLoader

println "[INFO] Starting pipeline orchestrator"

def workspace = new File(".").canonicalPath
println "[INFO] Workspace: ${workspace}"

def srcDir  = new File("src")
def varsDir = new File("vars")

if (!srcDir.exists() || !varsDir.exists()) {
    throw new RuntimeException("src/ or vars/ directory missing")
}

/**
 * 1. Create GroovyClassLoader
 */
def gcl = new GroovyClassLoader(this.class.classLoader)

/**
 * 2. Load all src classes FIRST
 */
srcDir.eachFileRecurse { file ->
    if (file.name.endsWith(".groovy")) {
        gcl.parseClass(file)
    }
}

/**
 * 3. Load all vars scripts
 */
def vars = [:]
varsDir.eachFile { file ->
    if (file.name.endsWith(".groovy")) {
        def scriptClass = gcl.parseClass(file)
        vars[file.name - ".groovy"] = scriptClass.newInstance()
    }
}

println "[INFO] Loaded vars: ${vars.keySet()}"

/**
 * 4. Pipeline execution (Jenkinsfile logic)
 */
import Stage

println "-----------------------------"
println "Pipeline execution started"
println "-----------------------------"

Stage.run("Build") {
    vars.createArtifact.createArtifact("demo-build")
}

Stage.run("Deploy") {
    vars.deployComponent.deploy("demo-build", "dev")
}

println "-----------------------------"
println "Pipeline execution finished"
println "-----------------------------"
