package recipes.recipe;


import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RecipeTests {

    // Note: These tests might be a bit derivative, but I am not sure what the standard practise is for writing tests,
    // so I included this file just in case.

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void Recipe_GettersSetters_Test() {
        Recipe recipe = new Recipe("Recipe", 4, "potato\ncarrot", true, "Boil potatoes");

        Long newId = (long) 2;
        recipe.setId(newId);

        String newName = "Test name";
        recipe.setName(newName);

        Integer newAmountOfServings = 1;
        recipe.setAmountOfServings(newAmountOfServings);

        String newIngredients = "water\noil";
        recipe.setIngredients(newIngredients);

        Boolean newIsVegetarian = false;
        recipe.setIsVegetarian(newIsVegetarian);

        String newInstructions = "Boil water";
        recipe.setInstructions(newInstructions);

        Assertions.assertThat(recipe.getId()).isEqualTo(newId);
        Assertions.assertThat(recipe.getName()).isEqualTo(newName);
        Assertions.assertThat(recipe.getIngredients()).isEqualTo(newIngredients);
        Assertions.assertThat(recipe.getIsVegetarian()).isEqualTo(newIsVegetarian);
        Assertions.assertThat(recipe.getInstructions()).isEqualTo(newInstructions);
        Assertions.assertThat(recipe.getAmountOfServings()).isEqualTo(newAmountOfServings);
    }

    @Test
    public void Recipe_Equals_Test() {
        Recipe recipe1 = new Recipe("Recipe", 4, "potato\ncarrot", true, "Boil potatoes");
        Recipe recipe2 = new Recipe("Recipe", 4, "potato\ncarrot", true, "Boil potatoes");
        Recipe recipe3 = new Recipe("Recipe3", 5, "potato", false, "Eat potatoes");

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);
        recipeRepository.save(recipe3);

        Boolean recipe1EqualsRecipe1 = recipe1.equals(recipe1);
        Boolean recipe1EqualsRecipe2 = recipe1.equals(recipe2);
        Boolean recipe1EqualsRecipe3 = recipe1.equals(recipe3);
        Boolean recipe2EqualsRecipe3 = recipe2.equals(recipe3);

        Assertions.assertThat(recipe1EqualsRecipe1).isTrue();
        Assertions.assertThat(recipe1EqualsRecipe2).isFalse();
        Assertions.assertThat(recipe1EqualsRecipe3).isFalse();
        Assertions.assertThat(recipe2EqualsRecipe3).isFalse();
    }

}
