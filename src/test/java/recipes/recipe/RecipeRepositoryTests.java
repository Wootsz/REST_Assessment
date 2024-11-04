package recipes.recipe;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RecipeRepositoryTests {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void RecipeRepository_Save_Test() {
        Recipe recipe1 = new Recipe("Recipe1", 4, "potato\ncarrot", true, "Boil potatoes");

        Recipe savedRecipe =  recipeRepository.save(recipe1);

        Assertions.assertThat(savedRecipe).isNotNull();
        Assertions.assertThat(savedRecipe.getId()).isGreaterThan(0);
    }

    @Test
    public void RecipeRepository_FindAll_Test() {
        Recipe recipe1 = new Recipe("Recipe1", 4, "potato\ncarrot", true, "Boil potatoes");
        Recipe recipe2 = new Recipe("Recipe2", 2, "chicken\nspices", false, "Grill chicken");

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);

        List<Recipe> allRecipes = recipeRepository.findAll();

        Assertions.assertThat(allRecipes).isNotNull();
        Assertions.assertThat(allRecipes.size()).isEqualTo(2);
        Assertions.assertThat(allRecipes).containsExactly(recipe1, recipe2);
    }

    @Test
    public void RecipeRepository_FindById_Test() {
        Recipe recipe1 = new Recipe("Recipe1", 4, "potato\ncarrot", true, "Boil potatoes");

        recipeRepository.save(recipe1);

        Optional<Recipe> maybeRecipe = recipeRepository.findById(recipe1.getId());

        Assertions.assertThat(maybeRecipe).isNotNull();
        Assertions.assertThat(maybeRecipe).isNotEmpty();
        Assertions.assertThat(maybeRecipe).hasValue(recipe1);
    }

    @Test
    public void RecipeRepository_DeleteById_Test() {
        Recipe recipe1 = new Recipe("Recipe1", 4, "potato\ncarrot", true, "Boil potatoes");
        Recipe recipe2 = new Recipe("Recipe2", 2, "chicken\nspices", false, "Grill chicken");

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);

        recipeRepository.deleteById(recipe1.getId());

        List<Recipe> allRecipes = recipeRepository.findAll();
        Optional<Recipe> maybeRecipe = recipeRepository.findById(recipe1.getId());

        Assertions.assertThat(allRecipes).doesNotContain(recipe1);
        Assertions.assertThat(maybeRecipe).isEmpty();
    }

    @Test
    public void RecipeRepository_FindByCriteria_Test() {
        Recipe recipe1 = new Recipe("Recipe1", 4, "potato\ncarrot", true, "Boil potatoes");
        Recipe recipe2 = new Recipe("Recipe2", 2, "chicken\nspices", false, "Grill chicken");

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);

        // All combinations of the following variables should return recipe1 when called by recipeRepository.findByCriteria (except if everything is null)
        for(Boolean isVegetarian : new Boolean[]{true, null}) {
            for(Integer amountOfServings : new Integer[]{4,null}) {
                for(String instructions : new String[]{"Boil potatoes", "Boil", "potatoes", "oil potatoe", null }) {
                    for(String containsIngredient : new String[]{ "potato\ncarrot", "potato", "carrot", "car", "pot", "ato\ncar", null }) {
                        for(String doesNotContainIngredient : new String[]{ "chicken", null }) {
                            if(!(isVegetarian == null && amountOfServings == null && instructions == null
                                    && containsIngredient == null && doesNotContainIngredient == null)) {
                                List<Recipe> filteredRecipes = recipeRepository.findByCriteria(isVegetarian, amountOfServings, instructions, containsIngredient, doesNotContainIngredient);

                                Assertions.assertThat(filteredRecipes).isNotNull();
                                Assertions.assertThat(filteredRecipes).containsExactly(recipe1);
                            }
                        }
                    }
                }
            }
        }

    }
}
