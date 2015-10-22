package domainapp.dom.simple;

import java.math.BigDecimal;
import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;

import domainapp.dom.simple.businesses.Business;
import domainapp.dom.simple.businesses.BusinessCategory;
import domainapp.dom.simple.businesses.BusinessLocation;

/**
 * Created by Administrator on 10/12/2015.
 */
public class BusinessLocationsForTests {


    public List<BusinessLocation> listAllBusinessLocations() {
        return container.allInstances(BusinessLocation.class);
    }

    public BusinessLocation addBusinessLocation(
            final Business business,
            final String businessLocationId,
            final String name,
            final BigDecimal maxDeliveryDistance,
            final BusinessCategory businessCategory)
    {
        final BusinessLocation obj = container.newTransientInstance(BusinessLocation.class);
        obj.setBusiness(business);
        obj.setBusinessLocationId(businessLocationId);
        obj.setName(name);
        obj.setMaxDeliveryDistance(maxDeliveryDistance);
        obj.setBusinessCategory(businessCategory);
        container.persistIfNotAlready(obj);
        return obj;
    }


    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    //endregion


}
