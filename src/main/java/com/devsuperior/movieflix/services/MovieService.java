package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired private MovieRepository repository;
	@Autowired private GenreRepository genreRepository;

	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		
		final Movie entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		
		return new MovieDetailsDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findByGenre(Long genreId, Pageable pageable) {
		
		final Genre genre = (genreId <= 0) ? null : genreRepository.getReferenceById(genreId);
		
		final Page<Movie> page = repository.searchMovieByGenreId(genre, pageable);
		
		return page.map(entity -> new MovieCardDTO(entity));
	}
}
