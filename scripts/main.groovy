import groovy.lang.GroovyClassLoader

println "[INFO] Starting pipeline orchestrator"

def srcDir  = new File("src")
def varsDir = new File("vars")

if (!srcDir.exists() || !varsDir.exists()) {
    throw new RuntimeException("src/ or vars/ directory missing")
}

/* 1. Create classloader */
def gcl = new GroovyClassLoader(this.class.classLoader)

/* 2. Load src classes */
srcDir.eachFileRecurse { file ->
    if (file.name.endsWith(".groovy")) {
        gcl.parseClass(file)
    }
}

/* 3. Load Stage dynamically */
def Stage = gcl.loadClass("Stage")

/* 4. Load vars scripts */
def vars = [:]
varsDir.eachFile { file ->
    if (file.name.endsWith(".groovy")) {
        def scriptClass = gcl.parseClass(file)
        vars[file.name - ".groovy"] = scriptClass.newInstance()
    }
}

println "[INFO] Loaded vars: ${vars.keySet()}"

println "-----------------------------"
println "Pipeline execution started"
println "-----------------------------"

Stage.run("Build") {
    vars.createArtifact.createArtifact("demo-build")
}

Stage.run("Deploy to DEV") {
    vars.deployComponent.deploy("demo-build", "dev")
}

println "-----------------------------"
println "Pipeline execution finished"
println "-----------------------------"
