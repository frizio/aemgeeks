package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.models.Author;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/*
 * http://localhost:4502/content/aemgeeks/us/en/author/jcr:content/parsys-8/author.geeks.json
 */
@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = Author.class,
        resourceType = AuthorImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(
        name = "jackson",       // "exporter"   *
        extensions ="json",     // "type"       *
        selector = "geeks",     // "selector": infinity | model
        options = {
                @ExporterOption(name = "SerializationFeature.WRAP_ROOT_VALUE", value="true"),
                @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true")
        }
)
@JsonRootName("author-details")
public class AuthorImpl implements Author {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorImpl.class);

    final protected static String RESOURCE_TYPE="aemgeeks/components/content/author";

    @Inject
    Resource resource;

    @SlingObject
    ResourceResolver resourceResolver;

    @Self
    SlingHttpServletRequest slingHttpServletRequest;

    @RequestAttribute(name = "rAttribute1")
    private String reqAttribute1;

    @ResourcePath(path="/content/aemgeeks/us/en/home")
    @Via("resource")
    Resource resourcePage;

    @ScriptVariable
    Page currentPage;

    @Inject
    @Via("resource")
    @Named("jcr:lastModifiedBy")
    String modifiedBy;

    @Inject
    @Via("resource")
    // @ValueMapValue
    @Default(values = "AEM")
    private String fname;

    @ValueMapValue
    @Default(values = "GEEKS")
    private String lname;

    @Inject
    @Via("resource")
    private boolean professor;

    @ValueMapValue
    private List<String> books;

    @ValueMapValue
    private List<String> occupations;

    @Override
    public List<String> getBooks() {
        if (books != null) {
            return new ArrayList<String>(books);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> getOccupations() {
        if (occupations != null) {
            return new ArrayList<String>(occupations);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public String getFirstName() {
        return fname;
    }

    @Override
    public String getLastName() {
        return lname;
    }

    @Override
    public boolean getIsProfessor() {
        return professor;
    }

    @Override
    public String getPageTitle(){
        return currentPage.getTitle();
    }

    @Override
    @JsonIgnore
    public String getReqAttribute1() {
        return reqAttribute1;
    }

    @Override
    public String getHomePageName(){
        return resourcePage.getName();
    }

    @Override
    public String getLastModifiedBy(){
        return modifiedBy;
    }

    @JsonProperty(value = "author-name")
    public String authorName(){
        return "AEM GEEKS";
    }

    @Override
    @JsonProperty(value = "books-details")
    public List<Map<String, String>> getBookDetailsWithMap() {
        List<Map<String, String>> bookDetailsMap = new ArrayList<>();
        try {
            Resource bookDetail = resource.getChild("bookdetailswithmap");
            if(bookDetail!=null){
                for (Resource book : bookDetail.getChildren()) {
                    Map<String,String> bookMap = new HashMap<>();
                    bookMap.put("bookname",book.getValueMap().get("bookname",String.class));
                    bookMap.put("booksubject",book.getValueMap().get("booksubject",String.class));
                    bookMap.put("publishyear",book.getValueMap().get("publishyear",String.class));
                    bookDetailsMap.add(bookMap);
                }
            }
        } catch (Exception e){
            LOG.info("\n ERROR while getting Book Details {} ",e.getMessage());
        }
        return bookDetailsMap;
    }

}
