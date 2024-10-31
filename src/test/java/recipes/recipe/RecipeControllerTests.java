package recipes.recipe;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import recipes.RecipesApplication;

import java.util.List;

@WebMvcTest(controllers = RecipeController.class)
@ContextConfiguration(classes = RecipesApplication.class)
public class RecipeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeRepository recipeRepository;

    @MockBean
    private RecipeModelAssembler assembler;

    // Test data
    Recipe recipe1 = new Recipe("Recipe1", 4, "potato\ncarrot", true, "Boil potatoes");
    Recipe recipe2 = new Recipe("Recipe2", 2, "chicken\nspices", false, "Grill chicken");

    @Test
    void filteredRecipesTest() throws Exception {

        when(recipeRepository.findByCriteria(true, 4, "Boil", "potato", null))
                .thenReturn(List.of(recipe1));
        when(assembler.toModel(recipe1)).thenReturn(EntityModel.of(recipe1));

        mockMvc.perform(get("/recipes/filter")
                        .param("isVegetarian", "true")
                        .param("amountOfServings", "4")
                        .param("containsInstruction", "Boil")
                        .param("containsIngredient", "potato"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("._embedded.recipes").isNotEmpty())
                .andExpect(jsonPath("._embedded.recipes[0].name").value("Recipe1"));
    }

    @Test
    void filteredRecipesTest2() throws Exception {

        when(recipeRepository.findByCriteria(false, 4, "Boil", "potato", null))
                .thenReturn(List.of());

        mockMvc.perform(get("/recipes/filter")
                        .param("isVegetarian", "false")
                        .param("amountOfServings", "4")
                        .param("containsInstruction", "Boil")
                        .param("containsIngredient", "potato"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("._embedded.recipes").isEmpty());
    }
}
