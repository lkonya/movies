package movies.model

import io.micronaut.core.annotation.Introspected

@Introspected
data class Movies(val movies: List<Movie>)

@Introspected
data class Movie(
        val title: String,
        val tmdbId: Int,
        val posterPath: String?,
        val overview: String,
        val releaseYear: Int,
        val originalTitle: String,
        val voteAverage: Double)
