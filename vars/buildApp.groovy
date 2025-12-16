import com.company.utils.Logger

return {
    Logger.info("Building application...")
    "mvn clean package".execute().waitFor()
}
