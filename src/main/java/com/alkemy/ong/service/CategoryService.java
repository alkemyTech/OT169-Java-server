package com.alkemy.ong.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.repository.CategoryRepository;

@Service
/**
 * @author Franco Lamberti
 */
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	private CategoryDTO convertEntityToDto(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setDescription(category.getDescription());
		categoryDTO.setImage(category.getImage());
		categoryDTO.setName(category.getName());
		return categoryDTO;
	}
	
	//Get all
	public List<CategoryDTO> getAllCategories(){
		return categoryRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	//Gey names all
	public List<CategoryDTO> getNamesFromAllCategories(){
		return categoryRepository.getNamesFromAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}
	//Get by id
	public Category getById(String id){
		return categoryRepository.getById(id);
	}
	//Create and update
	public Category save(Category category) {
		return categoryRepository.save(category);
	}	
	//Delete
	public ResponseEntity<Category> delete(Category category) {
		categoryRepository.delete(category);
		return new ResponseEntity<Category>(HttpStatus.OK);
	}
	
}
