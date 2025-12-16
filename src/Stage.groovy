class Stage {

    static void run(String name, Closure body) {
        println "\n============================="
        println " STAGE : ${name}"
        println "============================="

        try {
            body.call()
            println "✔ STAGE SUCCESS : ${name}"
        } catch (Exception e) {
            println "✖ STAGE FAILED  : ${name}"
            throw e
        }
    }
}
