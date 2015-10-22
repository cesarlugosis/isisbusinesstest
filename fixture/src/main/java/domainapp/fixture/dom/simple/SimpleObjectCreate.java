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

package domainapp.fixture.dom.simple;

import java.math.BigDecimal;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.simple.businesses.Business;
import domainapp.dom.simple.businesses.BusinessCategory;
import domainapp.dom.simple.businesses.BusinessLocation;
import domainapp.dom.simple.businesses.BusinessLocations;

public class SimpleObjectCreate extends FixtureScript {

    //region > name (input)
    private String name;
    /**
     * Name of the object (required)
     */
    public String getName() {
        return name;
    }

    public SimpleObjectCreate setName(final String name) {
        this.name = name;
        return this;
    }
    //endregion


    //region > businessLocation (output)
    private BusinessLocation businessLocation;

    /**
     * The created simple object (output).
     * @return
     */
    public BusinessLocation getBusinessLocation() {
        return businessLocation;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);
        String businessLocationId = checkParam("businessLocationId", ec, String.class);
        BigDecimal maxDeliveryDistance = checkParam("maxDeliveryDistance", ec, BigDecimal.class);
        final Business business = new Business();
        final BusinessCategory businessCategory = new BusinessCategory();
        // Cesar Lugo We need to add the logic here to define which business to assign to this businessLocation
        this.businessLocation = wrap(businessLocations).addBusinessLocation(business, businessLocationId, name,maxDeliveryDistance, businessCategory);

        // also make available to UI
        ec.addResult(this, businessLocation);
    }

    @javax.inject.Inject
    private BusinessLocations businessLocations;

}
