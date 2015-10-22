/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
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
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;

@DomainService(repositoryFor = BusinessLocation.class)
@DomainServiceLayout(named = "Businesses",menuOrder = "20")
public class BusinessLocations {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Locations");
    }
    //endregion

    //region > listAllBusinessLocations (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<BusinessLocation> listAllBusinessLocations() {
        return container.allInstances(BusinessLocation.class);
    }
    //endregion

    //region > findBusinessLocationById (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
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

    //region > findBusinessLocationsByName (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            contributed = Contributed.AS_ASSOCIATION
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

    //region > findBusinessLocationsByBusiness (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            contributed = Contributed.AS_ASSOCIATION
    )
    @MemberOrder(sequence = "4")
    public List<BusinessLocation> findBusinessLocationsByBusiness(
            @ParameterLayout(named="Business Name")
            final String name
    ) {
        return container.allMatches(
                new QueryDefault<>(
                        BusinessLocation.class,
                        "findBusinessLocationsByBusiness",
                        "name", name));
    }
    //endregion


    //region > addBusinessLocation (action)
    public static class CreateDomainEvent extends ActionDomainEvent<BusinessLocations> {
        public CreateDomainEvent(final BusinessLocations source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(contributed = Contributed.AS_NEITHER)
    @MemberOrder(sequence = "4")
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
