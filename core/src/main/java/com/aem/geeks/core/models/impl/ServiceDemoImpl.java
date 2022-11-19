package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.models.ServiceDemo;
import com.aem.geeks.core.services.DemoService;
import com.aem.geeks.core.services.DemoServiceB;
import com.aem.geeks.core.services.MultiService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;

@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = ServiceDemo.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ServiceDemoImpl  implements ServiceDemo {

    private static final Logger LOG= LoggerFactory.getLogger(ServiceDemoImpl.class);

    /*--------Start Tutorial #27--------*/

    @PostConstruct
    protected void init() {
        LOG.info("\n********* PRINTING LOGS *********");
        LOG.trace(" ********* PRINTING trace *********");
        LOG.debug(" ********* PRINTING debug *********");
        LOG.info(" ********* PRINTING info *********");
        LOG.warn(" ********* PRINTING warn *********");
        LOG.error(" ********* PRINTING error *********");
    }

    /*--------End Tutorial #27--------*/

    /*--------Start Tutorial #29--------*/
    @OSGiService
    private DemoService demoService;

    @Inject
    //@OSGiService
    DemoServiceB demoServiceB;

    @Override
    public Iterator<Page> getPagesList(){
        return demoService.getPages();
    }

    @Override
    public List<String> getPageTitleList() {
        return demoServiceB.getPages();
    }

    /*--------End Tutorial #29--------*/


    /*--------Start Tutorial #30--------*/

    @OSGiService(filter = "(component.name=serviceA)")
    MultiService multiService;

    @OSGiService(filter = "(component.name=com.aem.geeks.core.services.impl.MultiServiceBImpl)")
    MultiService multiServiceB;

    @Override
    public String getNameFromService() {
        return multiService.getName();
    }

    @Override
    public String getNameFromServiceB() {
        return multiServiceB.getName();
    }
    @Override
    public String getNameWithReference() {
        return demoServiceB.getNameWithReference();
    }
    /*--------End Tutorial #30--------*/
}

