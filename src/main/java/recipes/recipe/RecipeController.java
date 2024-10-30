package recipes.recipe;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeModelAssembler assembler;

    RecipeController(RecipeRepository recipeRepository, RecipeModelAssembler assembler) {
        this.recipeRepository = recipeRepository;
        this.assembler = assembler;
    }

    /**
     * Retrieve all recipies from the database
     * @return A Collectionmodel of all Recipes as EntityModels
     */
    @GetMapping("/recipes")
    CollectionModel<EntityModel<Recipe>> all() {

        List<EntityModel<Recipe>> recipes = recipeRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(recipes,
                linkTo(methodOn(RecipeController.class).all()).withSelfRel());
    }

    /**
     * Retrieve all recipes adhering to the specified filters
     * @return A Collectionmodel of Recipes as EntityModels
     */
//    @GetMapping("/recipes")
//    CollectionModel<EntityModel<Recipe>> allFiltered() {
//
//        List<EntityModel<Recipe>> recipes = recipeRepository.findBy().stream()
//                .map(assembler::toModel)
//                .collect(Collectors.toList());
//
//        return CollectionModel.of(recipes,
//                linkTo(methodOn(RecipeController.class).all()).withSelfRel());
//    }

    /**
     * Retrieve a specific recipe from the database
     * @param id The id of the recipe
     * @return An Entitymodel for the Recipe
     */
    @GetMapping("/recipes/{id}")
    EntityModel<Recipe> one(@PathVariable Long id) {

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        return assembler.toModel(recipe);
    }

    /**
     * Insert a new recipe into the database
     * @param newRecipe The recipe to be inserted
     * @return A ResponseEntity
     */
    @PostMapping("/recipes")
    ResponseEntity<?> newRecipe(@RequestBody Recipe newRecipe) {

        EntityModel<Recipe> entityModel = assembler.toModel(recipeRepository.save(newRecipe));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    /**
     * Replace (/update) a recipe in the database by a new one
     * @param newRecipe The new recipe
     * @param id The id of the old recipe to be updated
     * @return A ReponseEntity
     */
    @PutMapping("/recipes/{id}")
    ResponseEntity<?> replaceRecipe(@RequestBody Recipe newRecipe, @PathVariable Long id) {

        Recipe updatedRecipe = recipeRepository.findById(id)
            .map(recipe -> {
                recipe.setName(newRecipe.getName());
                recipe.setAmountOfServings((newRecipe.getAmountOfServings()));
                recipe.setIngredients(newRecipe.getIngredients());
                recipe.setIsVegetarian(newRecipe.getIsVegetarian());
                recipe.setInstructions(newRecipe.getInstructions());
                return recipeRepository.save(recipe);
            })
            .orElseGet(() -> recipeRepository.save(newRecipe));

        EntityModel<Recipe> entityModel = assembler.toModel(updatedRecipe);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    /**
     * Delete a recipe from the database
     * @param id The id of the recipe to be deleted
     * @return A ResponseEntity
     */
    @DeleteMapping("/recipes/{id}")
    ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        recipeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
