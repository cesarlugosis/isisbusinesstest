package domainapp.dom.simple.businesses;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

/**
 * Created by Administrator on 10/2/2015.
 */

@DomainService(repositoryFor = BusinessCategory.class)
@DomainServiceLayout(menuOrder = "30",named = "Businesses")

public class BusinessCategories {

    //region > findBusinessCategoryByName (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<BusinessCategory> findBusinessCategoryByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return container.allMatches(
                new QueryDefault<>(
                        BusinessCategory.class,
                        "findBusinessCategoryByName",
                        "name", name));
    }
    //endregion


    //region > addBusinessCategory (action)
    public static class CreateDomainEvent extends ActionDomainEvent<BusinessCategories> {
        public CreateDomainEvent(final BusinessCategories source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @MemberOrder(sequence = "2")
    public BusinessCategory addBusinessCategory(
            final @ParameterLayout(named="Business Category Id") String businessCategoryId,
            final @ParameterLayout(named="Name") String name){
        final BusinessCategory obj = container.newTransientInstance(BusinessCategory.class);
        obj.setBusinessCategoryId(businessCategoryId);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }


    @javax.inject.Inject
    DomainObjectContainer container;

}
