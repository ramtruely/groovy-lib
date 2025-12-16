package com.company.utils

class GitUtils {
    static void cloneRepo(String url) {
        Logger.info("Cloning repo: ${url}")
        "git clone ${url}".execute().waitFor()
    }
}
