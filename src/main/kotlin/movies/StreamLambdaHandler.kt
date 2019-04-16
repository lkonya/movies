package movies

import com.amazonaws.serverless.exceptions.ContainerInitializationException
import com.amazonaws.serverless.proxy.model.*
import com.amazonaws.services.lambda.runtime.*
import io.micronaut.function.aws.proxy.MicronautLambdaContainerHandler
import java.io.*

class StreamLambdaHandler : RequestStreamHandler {

    override fun handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context) {
        handler.proxyStream(inputStream, outputStream, context)
    }

    companion object {
        private var handler: MicronautLambdaContainerHandler

        init {
            try {
                handler = MicronautLambdaContainerHandler.getAwsProxyHandler()
            } catch (e: ContainerInitializationException) {
                e.printStackTrace()
                throw RuntimeException("Could not initialize Micronaut", e)
            }

        }
    }
}
