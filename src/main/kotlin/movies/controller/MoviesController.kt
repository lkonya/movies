package movies.controller

import io.micronaut.http.MediaType.APPLICATION_JSON
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import movies.repository.MovieRepository
import movies.model.Movies

@Controller(produces = [APPLICATION_JSON])
class MoviesController(private val repository: MovieRepository) {

    @Get("/movies")
    fun searchMovie() = Movies(repository.getAllMovies())

    @Get("/movies/{title}")
    fun searchMovieByTitle(@PathVariable("title") title: String): Movies =
            Movies(repository.getMoviesByTitle(title))

    @Get("/movies/{releaseYear}")
    fun searchMovieByReleaseYear(@PathVariable("releaseYear") releaseYear: Int): Movies =
            Movies(repository.getMoviesByReleaseYear(releaseYear))
}
