import groovy.lang.GroovyClassLoader

// Root directory
def baseDir = new File(".").canonicalFile

def srcDir  = new File(baseDir, "src")
def varsDir = new File(baseDir, "vars")

// Create classloader
GroovyClassLoader gcl = new GroovyClassLoader(this.class.classLoader)

// Load all src classes
srcDir.eachFileRecurse { file ->
    if (file.name.endsWith(".groovy")) {
        gcl.parseClass(file)
    }
}

// Load vars as closures
def vars = [:]

varsDir.eachFile { file ->
    if (file.name.endsWith(".groovy")) {
        def script = gcl.parseClass(file).newInstance()
        vars[file.name - ".groovy"] = script
    }
}

// ======================
// Use like Jenkins vars
// ======================

vars.checkoutRepo("https://github.com/example/repo.git")
vars.buildApp()
