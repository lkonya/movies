package movies

import com.amazonaws.serverless.exceptions.ContainerInitializationException
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import io.micronaut.function.aws.proxy.MicronautLambdaContainerHandler
import mu.KLogging
import java.io.InputStream
import java.io.OutputStream

class StreamLambdaHandler : RequestStreamHandler {

    override fun handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context) {
        handler.proxyStream(inputStream, outputStream, context)
    }

    companion object : KLogging() {
        private val handler: MicronautLambdaContainerHandler

        init {
            try {
                handler = MicronautLambdaContainerHandler.getAwsProxyHandler()
            } catch (e: ContainerInitializationException) {
                logger.error("Could not initialize Micronaut", e)
                throw RuntimeException("Could not initialize Micronaut", e)
            }
        }
    }
}
