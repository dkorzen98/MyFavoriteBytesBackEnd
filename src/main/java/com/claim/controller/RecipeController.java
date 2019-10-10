package com.claim.controller;

import java.awt.Image;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.claim.entity.Recipe;
import com.claim.entity.Student;
import com.claim.repository.RecipeRepository;
import com.claim.repository.StudentRepository;

@RestController
@RequestMapping("recipe")
@CrossOrigin
public class RecipeController {

	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private StudentRepository studentRepository;


	@GetMapping(value = "/userRecipes")
	@ResponseBody
	private ResponseEntity <List <Recipe>> seeRecipe(@RequestParam String email) {
		Student student = this.studentRepository.findById(email).get();
		if (student != null) {
			List<Recipe> recipes = this.recipeRepository.findByRecipeStudent(student);
			return new ResponseEntity<>(recipes, HttpStatus.OK);

		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
//	
	@RequestMapping(value = "/allRecipes", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private ResponseEntity <List <Recipe>> seeAllRecipe() {
		
			List<Recipe> recipes = this.recipeRepository.findAll();
			return new ResponseEntity<>(recipes, HttpStatus.OK);
	}
	@RequestMapping(value = "/submitImage", 
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void submitImage(@RequestParam("file") MultipartFile file, @RequestParam("pictureName") String pictureName,
            @RequestParam("recipeName") String recipeName, @RequestParam("ingredients") String ingredients,
            @RequestParam("instructions") String instructions,  @RequestParam("email") String email) throws IOException
    {   
		
        Recipe recipe = new Recipe();
        	recipe.setPictureName(file.getContentType());
        	recipe.setIngredients(ingredients);
        	recipe.setInstructions(instructions);
        	recipe.setRecipeName(recipeName);
        	
        	
        	try {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        recipe.setPictureName(base64Image);
        
        		}
        		 catch(IOException e){
        		
        		e.printStackTrace();
        	}
        	Student student = this.studentRepository.findById(email).get();
    		if (student != null) {
    			recipe.setRecipeStudent(student);
    			recipe = this.recipeRepository.save(recipe);
    		}
        	recipeRepository.save(recipe);
        
      //  this.recipeRepository.save(recipe);
    }
	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	private ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe, @RequestParam String email) {
		Student student = this.studentRepository.findById(email).get();
		if (student != null) {
			recipe.setRecipeStudent(student);
			recipe = this.recipeRepository.save(recipe);
			

			return new ResponseEntity<>(recipe, HttpStatus.OK);

		}
		return new ResponseEntity<>(recipe, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/search", produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.GET)
	@ResponseBody
	private ResponseEntity< List <Recipe>> findRecipe(@RequestParam String recipes){
	
		List <Recipe> recipe = recipeRepository.findByRecipeName(recipes);
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}
	

}
