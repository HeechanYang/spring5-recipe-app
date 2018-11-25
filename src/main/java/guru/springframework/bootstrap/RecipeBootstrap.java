package guru.springframework.bootstrap;

import guru.springframework.domains.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        // Unit of measures
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");
        if (!eachUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found!");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (!tableSpoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found!");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (!teaSpoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found!");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");
        if (!dashUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found!");
        }

        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByDescription("Cup");
        if (!cupUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found!");
        }

        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();

        //Categories
        Optional<Category> americanCatOptional = categoryRepository.findByDescription("American");
        if (!americanCatOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found!");
        }

        Optional<Category> mexicanCatOptional = categoryRepository.findByDescription("Mexican");
        if (!mexicanCatOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found!");
        }

        Category americanCat = americanCatOptional.get();
        Category mexicanCat = mexicanCatOptional.get();

        //Recipes
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.");
        Notes guacNotes = new Notes();
        guacNotes.setRecipe(guacRecipe);
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries (see our Strawberry Guacamole).");
        guacRecipe.setNotes(guacNotes);
        guacRecipe.getIngredients().add(new Ingredient("ripe Avocados", new BigDecimal(2), guacRecipe, eachUom));
        guacRecipe.getIngredients().add(new Ingredient("Kosher salt", new BigDecimal(0.5), guacRecipe, teaSpoonUom));
        guacRecipe.getIngredients().add(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), guacRecipe, tableSpoonUom));
        guacRecipe.getIngredients().add(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), guacRecipe, tableSpoonUom));
        guacRecipe.getIngredients().add(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), guacRecipe, eachUom));
        guacRecipe.getIngredients().add(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), guacRecipe, tableSpoonUom));
        guacRecipe.getIngredients().add(new Ingredient("freshly grated black pepper", new BigDecimal(1), guacRecipe, dashUom));
        guacRecipe.getIngredients().add(new Ingredient("tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), guacRecipe, eachUom));
        guacRecipe.getCategories().add(americanCat);
        guacRecipe.getCategories().add(mexicanCat);

        recipes.add(guacRecipe);

        return recipes;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }
}
