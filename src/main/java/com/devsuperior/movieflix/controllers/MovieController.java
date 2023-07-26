package com.devsuperior.movieflix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService service;

	@PreAuthorize("hasAnyRole('ROLE_VISITOR','ROLE_MEMBER')")
	@GetMapping("/{id}")
	public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id) {

		MovieDetailsDTO result = service.findById(id);

		return ResponseEntity.ok().body(result);
	}

	@PreAuthorize("hasAnyRole('ROLE_VISITOR','ROLE_MEMBER')")
	@GetMapping
	public ResponseEntity<Page<MovieCardDTO>> findByGenre(
			@RequestParam(value = "genreId", defaultValue = "0") Long genreId,
			@PageableDefault(sort = { "title" }, direction = Direction.ASC) Pageable pageable) {

		Page<MovieCardDTO> result = service.findByGenre(genreId, pageable);

		return ResponseEntity.ok().body(result);
	}
}
