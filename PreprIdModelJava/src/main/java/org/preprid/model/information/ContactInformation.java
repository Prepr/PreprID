package org.preprid.model.information;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Bits and pieces of information to be pulled into different entities as needed.
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class ContactInformation extends VersionedInformation {

    @NonNull private final String primaryEmail;

    private String address;

    private String dayTelephoneNumber;

}
