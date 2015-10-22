package domainapp.dom.simple.businesses;

/**
 * Created by Administrator on 9/23/2015.
 */

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;

@DomainService(repositoryFor = Business.class)
@DomainServiceLayout(menuOrder = "10")
@DomainObjectLayout(
        cssClassFa = "fa-building"
)
public class Businesses {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Businesses");
    }
    //endregion

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Business> listAllBusinesses() {
        return container.allInstances(Business.class);
    }
    //endregion

    //region > findById (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public List<Business> findBusinessById(
            @ParameterLayout(named="Business Id")
            final String businessId
    ) {
        return container.allMatches(
                new QueryDefault<>(
                        Business.class,
                        "findBusinessById",
                        "businessId", businessId));
    }
    //endregion

    //region > findByName (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "3")
    public List<Business> findBusinessesByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return container.allMatches(
                new QueryDefault<>(
                        Business.class,
                        "findBusinessesByName",
                        "name", name));
    }
    //endregion

    //region > addBusiness (action)
    public static class CreateDomainEvent extends ActionDomainEvent<Businesses> {
        public CreateDomainEvent(final Businesses source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @MemberOrder(sequence = "4")
    public Business addBusiness(
            final @ParameterLayout(named="Business Id") String businessId,
            final @ParameterLayout(named="Name") String name,
            final @ParameterLayout(named="Tenancy")ApplicationTenancy applicationTenancy
            )
    {
        final Business obj = container.newTransientInstance(Business.class);
        obj.setBusinessId(businessId);
        obj.setName(name);
        obj.setApplicationTenancy(applicationTenancy);
        obj.setCreationTime(clockService.nowAsDateTime());
        obj.setStatus(BusinessStatus.ACTIVE);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion



    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    @Inject
    private ClockService clockService;


    //endregion
}

