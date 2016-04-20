package com.rburgosnavas.droidfs.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Realm object for search categories.
 *
 * TODO: add image byte array
 */
public class SearchCategoryObject extends RealmObject {
    @Required
    @PrimaryKey
    private String name;

    @Required
    private String searchTerms;

    public SearchCategoryObject() {}

    public SearchCategoryObject(String name, String searchTerms) {
        this.name = name;
        this.searchTerms = searchTerms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms;
    }
}
