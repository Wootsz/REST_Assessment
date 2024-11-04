package recipes.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    /**
     * Query the RecipeRepository for recipes with certain values. We expect that any of the parameters can be null.
     * The parameters are used in the query as follows:
     * @param isVegetarian check equality of recipe.isVegetarian to this parameter
     * @param amountOfServings check equality of recipe.amountOfServings to this parameter
     * @param instructions check inclusion of recipe.instructions of this parameter
     * @param containsIngredient check inclusion of recipe.ingredients of this parameter
     * @param doesNotContainIngredient check NON-inclusion of recipe.ingredients of this parameter
     * @return A list of Recipes matching the criteria
     */
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
