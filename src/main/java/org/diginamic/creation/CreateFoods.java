package org.diginamic.creation;

import jakarta.persistence.EntityManager;

import org.apache.commons.csv.CSVRecord;
import org.diginamic.model.Brand;
import org.diginamic.model.Ingredient;
import org.diginamic.model.Product;
import org.diginamic.model.ProductCategory;
import org.diginamic.orm.DBAccessor;
import org.diginamic.repository.BrandRepository;
import org.diginamic.repository.IngredientRepository;
import org.diginamic.repository.ProductCategoryRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateFoods {
    
    /** 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // Create lines
        EntityManager em = new DBAccessor().getManager();

        ProductCategoryRepository productCategoryRepository = new ProductCategoryRepository(em);
        BrandRepository brandRepository = new BrandRepository(em);
        IngredientRepository ingredientRepository = new IngredientRepository(em);

        System.out.println("Creating products...");
        List<CSVRecord> templates = CsvParser.parseCSV("foods.csv");

        // get a new line
        // template size
        // parsing the line
        for (int lineCount = 1; lineCount < templates.size(); lineCount++) {

            String[] line = Arrays.toString(templates.get(lineCount).values()).replace("[", "").replace("]", "")
                    .split("\\|");

            System.out.println(Arrays.toString(line));
            em.getTransaction().begin();

            Product product = new Product();
            ProductCategory category = new ProductCategory();
            Brand brand = new Brand();

            // check brand (society) is exist
            if (brandRepository.getByName(line[1]).size() == 0) {
                brand.setName(line[1]);
                em.persist(brand);
                product.setBrand(brand);
            } else {
                product.setBrand((Brand) brandRepository.getByName(line[1]).get(0));
            }

            // check category is exist
            if (productCategoryRepository.getByLabel(line[0]).size() == 0) {
                category.setLabel(line[0]);
                em.persist(category);
                product.setProductCategory(category);
            } else {
                product.setProductCategory((ProductCategory) productCategoryRepository.getByLabel(line[0]).get(0));
            }

            // set ingredient
            // boucle pour chaque ingrédient dans la ligne 
            System.out.println(line[4].split(","));
            String[] productLineIngredients = line[4].split(",");
            ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

            for (String ingredientName : productLineIngredients) {

                // verify if ingredient already exists
                if (ingredientRepository.getByName(ingredientName).size() == 0) {
                    Ingredient ingredient = new Ingredient();

                    ingredient.setName(ingredientName);
                    em.persist(ingredient);

                    if (product.getIngredients() == null) {

                        ingredients.add(ingredient);
                        product.setIngredients(ingredients);

                    } else {
                        product.getIngredients().add(ingredient);
                    }

                } 
            }

            product.setIngredients(ingredients);

            product.setLabel(line[2]);

            product.setGrade(line[3]);
            em.persist(product);

            em.getTransaction().commit();
        }

        em.close();

    }

}
