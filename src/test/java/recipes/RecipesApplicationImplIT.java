package recipes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import recipes.recipe.Recipe;
import recipes.recipe.RecipeController;
import recipes.recipe.RecipeModelAssembler;
import recipes.recipe.RecipeRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RecipeController.class)
@ContextConfiguration(classes = RecipesApplication.class)
public class RecipesApplicationImplIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeRepository recipeRepository;

    @MockBean
    private RecipeModelAssembler assembler;


    @Test
    public void RecipeController_AllRecipes_Test() throws Exception {
        Recipe recipe1 = new Recipe("Recipe1", 4, "potato\ncarrot", true, "Boil potatoes");

        when(recipeRepository.findAll())
                .thenReturn(List.of(recipe1));
        when(assembler.toModel(recipe1)).thenReturn(EntityModel.of(recipe1));

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("._embedded.recipes").isNotEmpty())
                .andExpect(jsonPath("._embedded.recipes[0].name").value(recipe1.getName()))
                .andExpect(jsonPath("._embedded.recipes[0].amountOfServings").value(recipe1.getAmountOfServings()))
                .andExpect(jsonPath("._embedded.recipes[0].ingredients").value(recipe1.getIngredients()))
                .andExpect(jsonPath("._embedded.recipes[0].isVegetarian").value(recipe1.getIsVegetarian()))
                .andExpect(jsonPath("._embedded.recipes[0].instructions").value(recipe1.getInstructions()));
    }

    @Test
    public void RecipeController_GetRecipe_Test() throws Exception {
        Recipe recipe1 = new Recipe("Recipe1", 4, "potato\ncarrot", true, "Boil potatoes");
        Long recipe1Id = (long) 1;

        when(recipeRepository.findById(recipe1Id))
                .thenReturn(Optional.of(recipe1));
        when(assembler.toModel(recipe1)).thenReturn(EntityModel.of(recipe1));

        mockMvc.perform(get("/recipes/{id}", recipe1Id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(recipe1.getName()))
                .andExpect(jsonPath("amountOfServings").value(recipe1.getAmountOfServings()))
                .andExpect(jsonPath("ingredients").value(recipe1.getIngredients()))
                .andExpect(jsonPath("isVegetarian").value(recipe1.getIsVegetarian()))
                .andExpect(jsonPath("instructions").value(recipe1.getInstructions()));
    }

    @Test
    void RecipeController_RecipesFilter_Test() throws Exception {
        Recipe recipe1 = new Recipe("Recipe1", 4, "potato\ncarrot", true, "Boil potatoes");

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
                .andExpect(jsonPath("._embedded.recipes[0].name").value(recipe1.getName()))
                .andExpect(jsonPath("._embedded.recipes[0].amountOfServings").value(recipe1.getAmountOfServings()))
                .andExpect(jsonPath("._embedded.recipes[0].ingredients").value(recipe1.getIngredients()))
                .andExpect(jsonPath("._embedded.recipes[0].isVegetarian").value(recipe1.getIsVegetarian()))
                .andExpect(jsonPath("._embedded.recipes[0].instructions").value(recipe1.getInstructions()));

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
