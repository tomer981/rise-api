package smart.sage.riseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smart.sage.riseapi.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findByFirstNameContainingOrLastNameContainingOrPhoneNumberContainingOrAddressContaining(
            String firstName, String lastName, String phoneNumber, String address
    );
}
