package domainapp.dom.simple.businesses;

import java.math.BigDecimal;
import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

/**
 * Created by Administrator on 10/1/2015.
 */

@DomainService(repositoryFor = BusinessLocation.class,
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
@DomainServiceLayout(menuOrder = "50",named = "Locations")
public class BusinessLocationContributions {

    //region > listAllBusinessLocations (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            contributed = Contributed.AS_BOTH,
            position = ActionLayout.Position.PANEL
    )
    @MemberOrder(name="businessLocations",sequence = "1")
    public List<BusinessLocation> listAllBusinessLocations() {
        return container.allInstances(BusinessLocation.class);
    }
    //endregion

    //region > findBusinessLocationById (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            contributed = Contributed.AS_BOTH,
            position = ActionLayout.Position.PANEL
    )
    @MemberOrder(name="businessLocations",sequence = "2")
    public List<BusinessLocation> findBusinessLocationById(
            @ParameterLayout(named="Business Location Id")
            final String businessLocationId
    ) {
        return container.allMatches(
                new QueryDefault<>(
                        BusinessLocation.class,
                        "findBusinessLocationById",
                        "businessLocationId", businessLocationId));
    }
    //endregion

    //region > findByName (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            contributed = Contributed.AS_BOTH,
            position = ActionLayout.Position.PANEL
    )
    @MemberOrder(sequence = "3")
    public List<BusinessLocation> findBusinessLocationsByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return container.allMatches(
                new QueryDefault<>(
                        BusinessLocation.class,
                        "findBusinessLocationsByName",
                        "name", name));
    }
    //endregion

    //region > create (action)
    public static class CreateDomainEvent extends ActionDomainEvent<BusinessLocationContributions> {
        public CreateDomainEvent(final BusinessLocationContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @MemberOrder(name="businessLocations",sequence = "4")
    public BusinessLocation addBusinessLocation(
            final @ParameterLayout(named="Business") Business business,
            final @ParameterLayout(named="Business Location Id") String businessLocationId,
            final @ParameterLayout(named="Name") String name,
            final @ParameterLayout(named="Max Delivery Distance") BigDecimal maxDeliveryDistance,
            final @ParameterLayout(named="Business Category") BusinessCategory businessCategory)
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

    //endregion

    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    //endregion
}

