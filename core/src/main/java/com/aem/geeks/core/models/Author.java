package com.aem.geeks.core.models;

import java.util.List;
import java.util.Map;

public interface Author {
    String getFirstName();
    String getLastName();
    boolean getIsProfessor();
    String getPageTitle();
    String getReqAttribute1();
    String getHomePageName();
    String getLastModifiedBy();
    List<String> getBooks();
    List<String> getOccupations();
    List<Map<String, String>> getBookDetailsWithMap();

}
