package de.hfu.pms;

import de.hfu.pms.shared.enums.Salutation;
import de.hfu.pms.utils.RepresentationWrapper;
import de.hfu.pms.utils.WrappedEntity;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RepresentationWrapperTest {

    @Test
    public void testFind() {

        Collection<WrappedEntity<Salutation>> collection = RepresentationWrapper.getWrappedSalutations();
        Salutation salutation = Salutation.Mr;

        WrappedEntity<Salutation> foundResult = RepresentationWrapper.find(salutation, collection);
        assertNotEquals(null, foundResult);

        WrappedEntity<Salutation> expectedResult = new WrappedEntity<>("test", salutation);

        assertEquals(expectedResult.getEntity(), foundResult.getEntity());

    }

}
