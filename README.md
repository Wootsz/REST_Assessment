## Required:

- A running instance of Docker, to host the local MySQL database

## Filters
The following optional filters can be used when querying recipes:
* amountOfServings :: Integer
* doesNotContainIngredient :: String
* containsIngredient :: String
* isVegetarian :: Boolean
* containsInstruction :: String

## Examples of interacting with the database


* See a specific recipe:
  * curl -v http://localhost:8080/recipes/1 | json_pp

* See all recipes:
  * curl -v http://localhost:8080/recipes | json_pp

* Filter recipes
  * curl "http://localhost:8080/recipes/filter?amountOfServings=4&isVegetarian=true"

* Insert recipe:
  * curl -v -X POST localhost:8080/recipes -H 'Content-type:application/json' -d '{"name": "A refreshing drink", "isVegetarian": true, "instructions": "", "ingredients": [ "Water" ], "amountOfServings": 1 }' | json_pp

* Update recipe:
  * curl -v -X PUT localhost:8080/recipes/3 -H 'Content-Type:application/json' -d '{"name": "Boiled carrot", "isVegetarian": true, "instructions": "Boil carrot", "ingredients": [ "Carrot", "Water" ], "amountOfServings": 1 }' | json_pp

* Delete recipe:
  * curl -v -X DELETE http://localhost:8080/recipes/3 | json_pp


## Architectural choices

The application is built using the Spring Boot framework. 
All architectural choices were made in line with the Spring Boot standard practises.
See, for instance, https://spring.io/guides/tutorials/rest. 
