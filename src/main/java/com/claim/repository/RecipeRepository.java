package com.claim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claim.entity.Recipe;
import com.claim.entity.Student;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{
	
//	@Query(value="SELECT r FROM Recipe r where r.pictureName like %?1% or r.email like %?1%")

	List<Recipe> findByRecipeStudent(Student student);
	
	@Query("SELECT r FROM Recipe r where r.recipeName like %?1%")
	List<Recipe> findByRecipeName(String recipeName);
}
