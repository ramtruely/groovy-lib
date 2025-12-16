import groovy.lang.GroovyClassLoader

// Go up from scripts/main.groovy → repo root
def projectRoot = new File(".").canonicalFile

println "[INFO] Running from: ${projectRoot}"

// Adjust if script is executed from /workspace
if (new File(projectRoot, "scripts").exists()) {
    projectRoot = projectRoot
} else {
    projectRoot = projectRoot.parentFile
}

def srcDir  = new File(projectRoot, "src")
def varsDir = new File(projectRoot, "vars")

println "[INFO] srcDir : ${srcDir}"
println "[INFO] varsDir: ${varsDir}"

// Basic validation
if (!srcDir.exists() || !varsDir.exists()) {
    throw new RuntimeException("src or vars directory missing")
}

// Create classloader
def gcl = new GroovyClassLoader(this.class.classLoader)
// Load src classes
srcDir.eachFileRecurse { f ->
    if (f.name.endsWith(".groovy")) {
        gcl.parseClass(f)
    }
}

// Load vars
def vars = [:]
varsDir.eachFile { f ->
    if (f.name.endsWith(".groovy")) {
        vars[f.name - ".groovy"] = gcl.parseClass(f).newInstance()
    }
}

// =================
// USE LIKE JENKINS
// =================
vars.each { k, v -> println "[INFO] Loaded var: ${k}" }

// Example call (comment out if not needed)
// vars.checkoutRepo("https://github.com/example/repo.git")
// vars.buildApp()

println "[SUCCESS] main.groovy completed"

println "-----------------------------"
println "Pipeline execution started"
println "-----------------------------"

vars.createArtifact.createArtifact("demo-build")

println "-----------------------------"
println "Pipeline execution finished"
println "-----------------------------"

