package recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipesApplication.class, args);
	}

	/**
	 *
	 * See a specific recipe:
	 * curl -v http://localhost:8080/recipes/1 | json_pp
	 *
	 * See all recipes:
	 * curl -v http://localhost:8080/recipes | json_pp
	 *
	 * Insert recipe:
	 * curl -v -X POST localhost:8080/recipes -H 'Content-type:application/json' -d '{"name": "Test", "isVegetarian": false, "instructions": "Whatever", "ingredients": [ "Water" ], "amountOfServings": 1 }' | json_pp
	 *
	 * Update recipe:
	 * curl -v -X PUT localhost:8080/recipes/3 -H 'Content-Type:application/json' -d '{"name": "Test", "isVegetarian": false, "instructions": "Whatever", "ingredients": [ "Water" ], "amountOfServings": 1 }' | json_pp
	 *
	 * Delete recipe:
	 * curl -v -X DELETE http://localhost:8080/recipes/3 | json_pp
	 *
	 */

}
