package recipes.recipe;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "RECIPE")
public class Recipe {

    private @Id @GeneratedValue Long id;
    private String name;
    private int amountOfServings;
    private String[] ingredients;
    private Boolean isVegetarian;
    @Column(length = 1000) private String instructions;

    public Recipe() { }

    /**
     * A Recipe
     * @param name The name of the recipe
     * @param amountOfServings For how many people is this recipe intended?
     * @param ingredients An array of ingredients, including measurements
     * @param isVegetarian Whether this dish is vegetarian or not
     * @param instructions The instructions to make the recipe
     */
    public Recipe(String name, int amountOfServings, String[] ingredients, Boolean isVegetarian, String instructions) {
        this.name = name;
        this.amountOfServings = amountOfServings;
        this.ingredients = ingredients;
        this.isVegetarian = isVegetarian;
        this.instructions = instructions;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getAmountOfServings() {
        return this.amountOfServings;
    }

    public String[] getIngredients() {
        return this.ingredients;
    }

    public Boolean getIsVegetarian() {
        return this.isVegetarian;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmountOfServings(int amountOfServings) {
        this.amountOfServings = amountOfServings;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setIsVegetarian(Boolean isVegetarian) {
        this.isVegetarian = isVegetarian;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof Recipe))
            return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(this.id, recipe.id)
            && Objects.equals(this.name, recipe.name)
            && Objects.equals(this.amountOfServings, recipe.amountOfServings)
            && Objects.equals(this.ingredients, recipe.ingredients)
            && Objects.equals(this.isVegetarian, recipe.isVegetarian)
            && Objects.equals(this.instructions, recipe.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.amountOfServings, Arrays.hashCode(this.ingredients), this.isVegetarian, this.instructions);
    }

    @Override
    public String toString() {
        return "Recipe{" + "id=" + this.id + ", name='" + this.name + '\''
                + ", amountOfServing='" + this.amountOfServings + '\'' + ", ingredients='" + Arrays.toString(this.ingredients) + '\''
                + ", isVegetarian='" + this.isVegetarian + '\'' + ", instructions='" + this.instructions + '\'' + '}';
    }
}
