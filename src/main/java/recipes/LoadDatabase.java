package recipes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import recipes.recipe.Recipe;
import recipes.recipe.RecipeRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RecipeRepository recipeRepository) {

        return args -> {
            recipeRepository.save(new Recipe("Curried Sausage", 4,
                    "1 tbsp olive oil\n" +
                    "800g thin beef sausages\n" + "1 large brown onion, thinly sliced\n" +
                    "2 carrots, peeled, thinly sliced\n" + "2 garlic cloves, crushed\n" +
                    "1 tbsp KEEN'S Traditional Curry Powder\n" + "2 tbsp fruit chutney\n" +
                    "2 cups Massel chicken style liquid stock\n" + "1 cup frozen peas\n" +
                    "Coles White Medium Grain Rice, Steamed, to serve\n" + "Parsley leaves, to serve",
                    false, "Step 1\n" +
                        "Heat oil in a large, heavy-based saucepan over medium-high heat. Cook sausages, in batches, turning, for 5 to 6 minutes or until browned all over. Transfer to a large plate.\n" +
                        "Step 2\n" +
                        "Add onion, carrot and garlic. Cook, stirring, for 5 minutes or until onion has softened. Add curry powder. Cook, stirring, for 30 seconds or until fragrant. Add fruit chutney and stock. Return sausages to pan. Cover, bring to the boil. Reduce heat to medium-low. Simmer, uncovered, for 25 minutes or until sausages are cooked through and sauce thickens.\n" +
                        "Step 3\n" +
                        "Stir in peas. Season. Cook for 1 to 2 minutes or until heated through. Serve with steamed rice and sprinkled with parsley leaves."));

            recipeRepository.save(new Recipe("Zucchini slices", 15,
                    "5 Coles Australian Free Range Eggs\n" +
                        "150g (1 cup) Coles White Self Raising Flour, sifted\n" +
                        "375g zucchini, grated\n" +
                        "1 large onion, finely chopped\n" +
                        "200g rindless bacon, chopped\n" +
                        "1 cup grated cheddar cheese\n" +
                        "60ml (1/4 cup) vegetable oil\n",
                    false, "Step 1\n" +
                    "Preheat oven to 170C.\n" +
                    "Step 2\n" +
                    "Beat the eggs in a large bowl until combined. Add the flour and beat until smooth, then add zucchini, onion, bacon, cheese and oil and stir to combine.\n" +
                    "zucchini slice raw\n" +
                    "Step 3\n" +
                    "Grease and line a 30 x 20cm lamington pan. Pour into the prepared pan and bake in oven for 30 minutes or until cooked through."));

            recipeRepository.save(new Recipe("Coconut & squash dhansak", 4,
                        "1tbsp vegetable oil \n" +
                        "500g butternut squash (about 1 small squash), peeled and chopped into bite-sized chunks (or buy a pack of ready-prepared to save time), see tip, below left \n" +
                        "100g onions \n" +
                        "mild curry paste (we used korma) \n" +
                        "400g chopped tomatoes \n" +
                        "400g light coconut milk \n" +
                        "mini naan bread, to serve \n" +
                        "400g lentils, drained \n" +
                        "200g baby spinach\n" +
                        "150ml coconut yogurt (we used Rachel’s Organic), plus extra to serve",
                    true, "STEP 1\n" +
                    "Heat the oil in a large pan. Put the squash in a bowl with a splash of water. Cover with cling film and microwave on High for 10 mins or until tender. Meanwhile, add the onions to the hot oil and cook for a few mins until soft. Add the curry paste, tomatoes and coconut milk, and simmer for 10 mins until thickened to a rich sauce.\n" +
                    "STEP 2\n" +
                    "Warm the naan breads in a low oven or in the toaster. Drain any liquid from the squash, then add to the sauce with the lentils, spinach and some seasoning. Simmer for a further 2-3 mins to wilt the spinach, then stir in the coconut yogurt. Serve with the warm naan and a dollop of extra yogurt."));

            recipeRepository.findAll().forEach(recipe -> log.info("Preloaded " + recipe));

        };
    }
}