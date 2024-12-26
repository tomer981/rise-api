package smart.sage.riseapi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import smart.sage.riseapi.dto.ContactDTO;
import smart.sage.riseapi.model.Contact;
import smart.sage.riseapi.repository.ContactRepository;
import smart.sage.riseapi.service.interfaces.ContactService;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RiseApiApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactRepository contactRepository;

    @MockBean
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        contactRepository.save(new Contact(null, "tomer", "Avivi", "0526172173", "tel aviv"));
    }

    @AfterEach
    void tearDown() {
        contactRepository.deleteAll();
    }

    @Test
    void testGetContacts() throws Exception {
        List<Contact> contacts = List.of(
                new Contact(1L, "tomer", "Avivi", "0526172173", "tel aviv")
        );

        Mockito.when(contactService.getContacts(any(Pageable.class))).thenReturn(contacts);

        mockMvc.perform(get("/api/v1/contacts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("tomer"));
    }


    @Test
    void testSaveContact() throws Exception {
        Contact savedContact = new Contact(1L, "Tomer", "Avivi", "1234567890", "tel aviv");

        Mockito.when(contactService.saveContact(any(ContactDTO.class)))
                .thenReturn(savedContact);

        mockMvc.perform(post("/api/v1/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Tomer\",\"lastName\":\"Avivi\",\"phoneNumber\":\"1234567890\",\"address\":\"tel aviv\"}")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Tomer"))
                .andExpect(jsonPath("$.lastName").value("Avivi"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.address").value("tel aviv"));
    }

    @Test
    void testDeleteContact() throws Exception {
        Mockito.doNothing().when(contactService).deleteContact(anyLong());

        mockMvc.perform(delete("/api/v1/contacts/{id}", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testUpdateContact() throws Exception {
        Contact updatedContact = new Contact(1L, "Tomer", "Updated", "0987654321", "Updated Address");

        Mockito.when(contactService.updateContact(anyLong(), any(ContactDTO.class)))
                .thenReturn(updatedContact);

        mockMvc.perform(put("/api/v1/contacts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Tomer\",\"lastName\":\"Updated\",\"phoneNumber\":\"0987654321\",\"address\":\"Updated Address\"}")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Tomer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("0987654321"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Updated Address"));
    }

    @Test
    void testSearchContacts() throws Exception {
        Contact foundContact = new Contact(1L, "Tomer", "Avivi", "0526172173", "Tel Aviv");

        Mockito.when(contactService.searchContacts(any(ContactDTO.class)))
                .thenReturn(foundContact);

        mockMvc.perform(post("/api/v1/contacts/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Tomer\",\"lastName\":\"Avivi\",\"phoneNumber\":\"0526172173\",\"address\":\"Tel Aviv\"}")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Tomer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Avivi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("0526172173"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Tel Aviv"));
    }
}