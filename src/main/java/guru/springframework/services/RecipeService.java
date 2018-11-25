package guru.springframework.services;

import guru.springframework.domains.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
