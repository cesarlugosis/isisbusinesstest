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
package domainapp.app.services.homepage;

import java.util.List;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.simple.businesses.BusinessLocation;
import domainapp.dom.simple.businesses.BusinessLocations;

 @ViewModel
public class HomePageViewModel {

    //region > title
    public String title() {
        return " Business Locations";
    }
    //endregion

    //region > BusinessLocations (collection)

    @org.apache.isis.applib.annotation.HomePage

    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BusinessLocation> getBusinessLocations() {
        return businessLocations.listAllBusinessLocations();
    }

    //endregion

    // region Injected Services
    @javax.inject.Inject
    BusinessLocations businessLocations;
    //endregion

}
