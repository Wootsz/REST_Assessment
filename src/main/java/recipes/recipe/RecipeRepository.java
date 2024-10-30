package recipes.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r " +
            "WHERE (:isVegetarian IS NULL OR r.isVegetarian = :isVegetarian) " +
            "AND (:amountOfServings IS NULL OR r.amountOfServings = :amountOfServings) " +
            "AND (:instructions IS NULL OR r.instructions LIKE CONCAT('%', :instructions, '%')) " +
            "AND (:containsIngredient IS NULL OR r.ingredients LIKE CONCAT('%', :containsIngredient, '%')) " +
            "AND (:doesNotContainIngredient IS NULL OR r.ingredients NOT LIKE CONCAT('%', :doesNotContainIngredient, '%')) ")
    List<Recipe> findByCriteria(
            @Param("isVegetarian") Boolean isVegetarian,
            @Param("amountOfServings") Integer amountOfServings,
            @Param("instructions") String instructions,
            @Param("containsIngredient") String containsIngredient,
            @Param("doesNotContainIngredient") String doesNotContainIngredient);

}
