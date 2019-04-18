package movies.controller

import io.micronaut.http.MediaType.APPLICATION_JSON
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.reactivex.Flowable
import movies.model.Movies
import movies.repository.MovieRepository

@Controller(produces = [APPLICATION_JSON])
class MoviesController(private val repository: MovieRepository) {

    @Get("/movies")
    fun searchMovie(): Flowable<Movies> =
            repository.getAllMovies().map { Movies(it) }

    @Get("/movies/{title}")
    fun searchMovieByTitle(@PathVariable("title") title: String): Flowable<Movies> =
            repository.getMoviesByTitle(title).map { Movies(it) }

    @Get("/movies/{releaseYear}")
    fun searchMovieByReleaseYear(@PathVariable("releaseYear") releaseYear: Int): Flowable<Movies> =
            repository.getMoviesByReleaseYear(releaseYear).map { Movies(it) }
}
