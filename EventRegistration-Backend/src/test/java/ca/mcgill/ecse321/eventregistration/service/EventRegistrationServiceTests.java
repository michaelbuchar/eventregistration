package ca.mcgill.ecse321.eventregistration.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.eventregistration.dao.EventRepository;
import ca.mcgill.ecse321.eventregistration.dao.PersonRepository;
import ca.mcgill.ecse321.eventregistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.eventregistration.service.EventRegistrationService;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.context.junit4.SpringRunner;
import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationRestController;
import ca.mcgill.ecse321.eventregistration.model.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestEventRegistrationService {
    @Mock
    private PersonRepository personDao;

    @InjectMocks
    private EventRegistrationService service;

    private static final String PERSON_KEY = "TestPerson";
    private static final String NONEXISTING_KEY = "NotAPerson";

    @Before
    public void setMockOutput() {
        when(personDao.findPersonByName(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(PERSON_KEY)) {
                Person person = new Person();
                person.setName(PERSON_KEY);
                return person;
            } else {
                return null;
            }
        });
    }
    @Test
    public void testCreatePerson() {
        assertEquals(0, service.getAllPersons().size());

        String name = "Oscar";

        Person person = null;
        try {
            person = service.createPerson(name);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }

        assertEquals(name, person.getName());
    }

    @Test
    public void testCreatePersonNull() {
        String name = null;
        String error = null;

        Person person = null;
        try {
            person = service.createPerson(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        // check error
        assertEquals("Person name cannot be empty!", error);
    }
}