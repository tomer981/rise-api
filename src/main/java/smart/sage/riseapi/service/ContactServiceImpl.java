package smart.sage.riseapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smart.sage.riseapi.dto.ContactDTO;
import smart.sage.riseapi.exception.ContactNotFoundException;
import smart.sage.riseapi.model.Contact;
import smart.sage.riseapi.repository.ContactRepository;
import smart.sage.riseapi.service.interfaces.ContactService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<Contact> getContacts(Pageable page) {
        Page<Contact> pageContact = contactRepository.findAll(page);
        return pageContact.getContent();
    }

    @Override
    public Contact saveContact(ContactDTO contactDTO) {
        Contact contact = Contact
                .builder()
                .firstName(contactDTO.firstName())
                .lastName(contactDTO.lastName())
                .phoneNumber(contactDTO.phoneNumber())
                .address(contactDTO.address())
                .build();
        return contactRepository.save(contact);

    }

    @Override
    public Contact findContact(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id " + id));
    }

    @Transactional
    @Override
    public Contact updateContact(Long id, ContactDTO contactDTO) {
        Contact contact = this.findContact(id);
        contact.setFirstName(contactDTO.firstName());
        contact.setLastName(contactDTO.lastName());
        contact.setPhoneNumber(contactDTO.phoneNumber());
        contact.setAddress(contactDTO.address());
        return contactRepository.save(contact);
    }

    @Override
    public Contact searchContacts(ContactDTO contactDTO) {
        return contactRepository.findByFirstNameContainingOrLastNameContainingOrPhoneNumberContainingOrAddressContaining(
                contactDTO.firstName(),
                contactDTO.lastName(),
                contactDTO.phoneNumber(),
                contactDTO.address()
        );
    }
}