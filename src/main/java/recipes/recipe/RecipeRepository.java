package recipes.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("select r from Recipe r where r.isVegetarian = true")
    List<Recipe> findByIsVegetarian(Boolean isVegetarian);

    @Query("select r from Recipe r where r.amountOfServings = ?1")
    List<Recipe> findByAmountOfServings(int amountOfServings);

//    @Query("select r from Recipe r where ?1 = any( r.ingredients )")
//    List<Recipe> findByContainsIngredient(String ingredient);
//
//    @Query("select r from Recipe r where not ?1 = any( r.ingredients )")
//    List<Recipe> findByDoesNotContainIngredient(String ingredient);
}
