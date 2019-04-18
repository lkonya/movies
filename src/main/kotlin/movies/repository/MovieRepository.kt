package movies.repository

import io.reactivex.Flowable
import movies.model.Movie
import mu.KLogging
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.ScanRequest
import javax.inject.Singleton

@Singleton
class MovieRepository(private val dynamoDbClient: DynamoDbAsyncClient) {
    companion object : KLogging() {
        private const val TableName = "movie"
    }

    fun getAllMovies(): Flowable<List<Movie>> {
        val scanRequest = createScanRequest { it }
        return retrieveMovies(scanRequest)
    }

    fun getMoviesByTitle(title: String): Flowable<List<Movie>> {
        val placeHolder = ":title"
        val filterExpression = "contains(title, $placeHolder)"
        val scanRequest = createFilteredScanRequest(filterExpression, placeHolder, title.toAttributeValue())
        return retrieveMovies(scanRequest)
    }

    fun getMoviesByReleaseYear(releaseYear: Int): Flowable<List<Movie>> {
        val placeHolder = ":releaseYear"
        val filterExpression = "releaseYear = $placeHolder"
        val scanRequest = createFilteredScanRequest(filterExpression, placeHolder, releaseYear.toAttributeValue())
        return retrieveMovies(scanRequest)
    }

    private fun retrieveMovies(scanRequest: ScanRequest): Flowable<List<Movie>> {
        return Flowable.fromFuture(dynamoDbClient.scan(scanRequest)).map { it.items().map(this::createMovie) }
    }

    private fun createFilteredScanRequest(filterExpression: String, placeholder: String, placeholderValue: AttributeValue): ScanRequest =
            createScanRequest {
                it.tableName(TableName)
                        .filterExpression(filterExpression)
                        .expressionAttributeValues(mapOf(placeholder to placeholderValue))
            }

    private fun createScanRequest(builderModifier: (ScanRequest.Builder) -> ScanRequest.Builder): ScanRequest {
        val baseBuilder = ScanRequest.builder().tableName(TableName)
        return builderModifier(baseBuilder).build()
    }

    private fun createMovie(fieldValueMap: MutableMap<String, AttributeValue>): Movie =
            Movie(
                    fieldValueMap["title"]!!.s(),
                    fieldValueMap["tmdbId"]!!.n().toInt(),
                    fieldValueMap["overview"]!!.s(),
                    fieldValueMap["posterPath"]!!.s(),
                    fieldValueMap["releaseYear"]!!.n().toInt(),
                    fieldValueMap["originalTitle"]!!.s(),
                    fieldValueMap["voteAverage"]!!.n().toDouble()
            )

    private fun String.toAttributeValue(): AttributeValue = AttributeValue.builder().s(this).build()
    private fun Int.toAttributeValue(): AttributeValue = AttributeValue.builder().n(this.toString()).build()

}
