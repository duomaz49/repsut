export interface IRecipeMealDB {
  dateModified: string | null; // Nullable date
  idMeal: string; // Meal identifier (it's a string, not a number)
  strArea: string; // The cuisine or region of the dish (e.g., "French")
  strCategory: string; // The category of the meal (e.g., "Chicken")
  strCreativeCommonsConfirmed: string | null; // Usually null
  strDrinkAlternate: string | null; // Alternate drink for the meal
  strImageSource: string | null; // Link to the image source (optional)
  strInstructions: string; // Full cooking instructions
  strMeal: string; // Name of the meal (e.g., "Chicken Parmentier")
  strMealThumb: string; // Thumbnail image URL for the meal
  strSource: string; // Link to the recipe source
  strTags: string; // Comma-separated list of tags (e.g., "Meat,Dairy")
  strYoutube: string; // YouTube URL for the meal video

  // **Dynamic Ingredients & Measures**
  strIngredient1: string;
  strIngredient2: string;
  strIngredient3: string;
  strIngredient4: string;
  strIngredient5: string;
  strIngredient6: string;
  strIngredient7: string;
  strIngredient8: string;
  strIngredient9: string;
  strIngredient10: string;
  strIngredient11: string;
  strIngredient12: string;
  strIngredient13: string;
  strIngredient14: string;
  strIngredient15: string;
  strIngredient16: string;
  strIngredient17: string;
  strIngredient18: string;
  strIngredient19: string;
  strIngredient20: string;

  // **Dynamic Measures**
  strMeasure1: string;
  strMeasure2: string;
  strMeasure3: string;
  strMeasure4: string;
  strMeasure5: string;
  strMeasure6: string;
  strMeasure7: string;
  strMeasure8: string;
  strMeasure9: string;
  strMeasure10: string;
  strMeasure11: string;
  strMeasure12: string;
  strMeasure13: string;
  strMeasure14: string;
  strMeasure15: string;
  strMeasure16: string;
  strMeasure17: string;
  strMeasure18: string;
  strMeasure19: string;
  strMeasure20: string;
}
