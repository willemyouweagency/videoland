package com.demo.videoland.categories.types;

import java.util.List;

public record CategoryResponse(List<AvailableCategory> availableCategories, List<SubscribedCategoriesExtended> subscribedCategories) {}