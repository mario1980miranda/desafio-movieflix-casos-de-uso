package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;
	@Autowired
	private MovieRepository repoMovieRepository;
	@Autowired
	private AuthService authService;

	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {

		try {
			final Movie movie = repoMovieRepository.getReferenceById(dto.getMovieId());
			final User user = authService.authenticated();

			Review entity = new Review();
			entity.setMovie(movie);
			entity.setText(dto.getText());
			entity.setUser(user);

			entity = repository.save(entity);

			return new ReviewDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(String.format("movieId %s não encontrado", dto.getMovieId()));
		}
	}

}
