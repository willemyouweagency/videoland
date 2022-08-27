package com.demo.videoland.categories;

import com.demo.videoland.categories.types.AvailableCategory;
import com.demo.videoland.categories.types.CategoryType;

import java.util.Map;

import static java.util.Map.entry;

// This class is just holding static data. Since it is (relatively) static we keep it out of the DB to keep things simpler
public class AvailableCategoriesData {
    public static Map<CategoryType, AvailableCategory> availableCategories = Map.ofEntries(
            entry(CategoryType.DUTCH_FILMS, new AvailableCategory("Dutch Films", 10, 4.0)),
            entry(CategoryType.DUTCH_SERIES, new AvailableCategory("Dutch Series", 20, 5.0)),
            entry(CategoryType.INTERNATIONAL_FILMS, new AvailableCategory("International Films", 15, 6.0))
    );

    public static String getName(CategoryType categoryType) {
        return availableCategories.get(categoryType).name();
    }

    public static double getPrice(CategoryType categoryType) {
        return availableCategories.get(categoryType).price();
    }
}
