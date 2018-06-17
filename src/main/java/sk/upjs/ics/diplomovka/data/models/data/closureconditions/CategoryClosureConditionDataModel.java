package sk.upjs.ics.diplomovka.data.models.data.closureconditions;

import java.util.List;

public class CategoryClosureConditionDataModel extends ClosureConditionDataModel {

    private List<Integer> categories;

    public CategoryClosureConditionDataModel(String type, List<Integer> categories) {
        this.type = type;
        this.categories = categories;
    }

    public List<Integer> getCategories() {
        return categories;
    }
}
