package movies.configuration

import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Primary
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import javax.inject.Singleton

@Factory
class AwsBean {

    @Primary
    @Singleton
    fun dynamoDbClient(): DynamoDbClient = DynamoDbClient.builder().region(Region.EU_CENTRAL_1).build()

}
