/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package domainapp.dom.simple;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

import domainapp.dom.simple.businesses.Business;
import domainapp.dom.simple.businesses.BusinessCategory;
import domainapp.dom.simple.businesses.BusinessLocation;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleObjectsTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    @Mock
    DomainObjectContainer mockContainer;

    BusinessLocationsForTests businessLocations;

    @Before
    public void setUp() throws Exception {
        businessLocations = new BusinessLocationsForTests();
        businessLocations.container = mockContainer;
    }

    public static class Create extends SimpleObjectsTest {

        @Test
        public void happyCase() throws Exception {

            // given
            final BusinessLocation businessLocation = new BusinessLocation();

            final Sequence seq = context.sequence("create");
            context.checking(new Expectations() {
                {
                    oneOf(mockContainer).newTransientInstance(BusinessLocation.class);
                    inSequence(seq);
                    will(returnValue(businessLocation));

                    oneOf(mockContainer).persistIfNotAlready(businessLocation);
                    inSequence(seq);
                }
            });

            // when
            final Business business = new Business();
            final BusinessCategory businessCategory = new BusinessCategory();
            final BusinessLocation obj = businessLocations.addBusinessLocation(business,"11","Restaurant 11", BigDecimal.ONE,businessCategory);

            // then
            assertThat(obj).isEqualTo(businessLocation);
            assertThat(obj.getBusinessLocationId()).isEqualTo("11");
            assertThat(obj.getName()).isEqualTo("Restaurant 11");
        }

    }

    public static class ListAll extends SimpleObjectsTest {

        @Test
        public void happyCase() throws Exception {

            // given
            final List<BusinessLocation> all = Lists.newArrayList();

            context.checking(new Expectations() {
                {
                    oneOf(mockContainer).allInstances(BusinessLocation.class);
                    will(returnValue(all));
                }
            });

            // when
            final List<BusinessLocation> list = businessLocations.listAllBusinessLocations();

            // then
            assertThat(list).isEqualTo(all);
        }
    }

}
