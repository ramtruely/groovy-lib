import com.company.utils.GitUtils
import com.company.utils.Logger

return { String repoUrl ->
    Logger.info("checkoutRepo() called")
    GitUtils.cloneRepo(repoUrl)
}
