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
package domainapp.integtests.tests.modules.simple;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;

import domainapp.dom.simple.businesses.BusinessLocation;
import domainapp.fixture.scenarios.RecreateSimpleObjects;
import domainapp.integtests.tests.DomainAppIntegTest;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleObjectIntegTest extends DomainAppIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    RecreateSimpleObjects fs;
    BusinessLocation businessLocationPojo;
    BusinessLocation businessLocationWrapped;

    @Before
    public void setUp() throws Exception {
        // given
        fs = new RecreateSimpleObjects().setNumber(1);
        fixtureScripts.runFixtureScript(fs, null);

        businessLocationPojo = fs.getBusinessLocations().get(0);

        assertThat(businessLocationPojo).isNotNull();
        businessLocationWrapped = wrap(businessLocationPojo);
    }

    public static class Name extends SimpleObjectIntegTest {

        @Test
        public void accessible() throws Exception {
            // when
            final String name = businessLocationWrapped.getName();
            // then
            assertThat(name).isEqualTo(fs.NAMES.get(0));
        }

        @Test
        public void cannotBeUpdatedDirectly() throws Exception {

            // expect
            expectedExceptions.expect(DisabledException.class);

            // when
            businessLocationWrapped.setName("new name");
        }
    }

    public static class UpdateName extends SimpleObjectIntegTest {

        @Test
        public void happyCase() throws Exception {

            // when
            businessLocationWrapped.updateName("new name");

            // then
            assertThat(businessLocationWrapped.getName()).isEqualTo("new name");
        }

        @Test
        public void failsValidation() throws Exception {

            // expect
            expectedExceptions.expect(InvalidException.class);
            expectedExceptions.expectMessage("Exclamation mark is not allowed");

            // when
            businessLocationWrapped.updateName("new name!");
        }
    }


    public static class Title extends SimpleObjectIntegTest {

        @Inject
        DomainObjectContainer container;

        @Test
        public void interpolatesName() throws Exception {

            // given
            final String name = businessLocationWrapped.getName();

            // when
            final String title = container.titleOf(businessLocationWrapped);

            // then
            assertThat(title).isEqualTo("Object: " + name);
        }
    }
}