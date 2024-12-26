package smart.sage.riseapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart.sage.riseapi.dto.ContactDTO;
import smart.sage.riseapi.model.Contact;
import smart.sage.riseapi.service.interfaces.ContactService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contacts")
@Slf4j
@Tag(name = "Contact Rest API", description = "")
public class ContactController {
    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> getContacts(@ParameterObject Pageable pageable) {
        log.debug("Get contacts for page {}", pageable);
        List<Contact> contacts = contactService.getContacts(pageable);
        log.debug("Get contacts for page {}", pageable);
        return ResponseEntity.ok(contacts);
    }

    @PostMapping
    public ResponseEntity<Contact> saveContact(@RequestBody ContactDTO contactDTO) {
        log.debug("Save contact {}", contactDTO);
        Contact contact = contactService.saveContact(contactDTO);
        log.debug("Contact saved {}", contact);
        return ResponseEntity.ok(contact);
    }

    @PostMapping("/search")
    public ResponseEntity<Contact> searchContact(@RequestBody ContactDTO contactDTO) {
        log.debug("Search contact {}", contactDTO);
        Contact contact = contactService.searchContacts(contactDTO);
        log.debug("Search contact {}", contact);
        return ResponseEntity.ok(contact);
    }

    @PutMapping("{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        log.debug("Update contact {}", contactDTO);
        Contact contact = contactService.updateContact(id, contactDTO);
        log.info("Contact {} updated", contact);
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id){
        log.debug("Delete contact {}", id);
        contactService.deleteContact(id);
        log.info("Contact {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}
