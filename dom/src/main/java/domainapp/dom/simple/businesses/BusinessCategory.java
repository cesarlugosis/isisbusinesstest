package domainapp.dom.simple.businesses;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.util.ObjectContracts;

/**
 * Created by Administrator on 10/2/2015.
 */
@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema = "simple",
        table = "BusinessCategory"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findBusinessCategoryByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.simple.BusinessCategory "
                        + "WHERE name.indexOf(:name) >= 0 "),
})

@javax.jdo.annotations.Unique(name="BusinessCategory_Id_UNQ", members = {"businessCategoryId"})
@DomainObject(autoCompleteAction = "findBusinessCategoryByName", autoCompleteRepository = BusinessCategories.class,auditing= Auditing.ENABLED )

@DomainObjectLayout(
        bookmarking = BookmarkPolicy.NEVER,
        cssClassFa = "fa-list-alt"
)


public class BusinessCategory implements Comparable<BusinessCategory> {

    //region > ID (property)
    private String businessCategoryId;

    @javax.jdo.annotations.Column(allowsNull="false", length = 10)
    @MemberOrder(sequence = "1")

    public String getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(final String businessCategoryId) {
        this.businessCategoryId = businessCategoryId;
    }

    // endregion

    //region > name (property)

    private String name;

    @javax.jdo.annotations.Column(allowsNull="false", length = 40)
    @Title(sequence="1")
    @MemberOrder(sequence = "2")

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    // endregion

    //region > businessLocations (collection)
    @Persistent(mappedBy = "businessCategory", dependentElement = "false")
    private SortedSet<BusinessLocation> businessLocations = new TreeSet<BusinessLocation>();

    @CollectionLayout(
            hidden= Where.EVERYWHERE)
    @MemberOrder(sequence = "3")
    public SortedSet<BusinessLocation> getBusinessLocations() {
        return businessLocations;
    }

    public void setBusinessLocations(final SortedSet<BusinessLocation> businessLocations) {
        this.businessLocations = businessLocations;
    }
    //endregion


    @Override
    public int compareTo(final BusinessCategory other) {
        return ObjectContracts.compare(this, other, "businessCategoryId");
    }


}
