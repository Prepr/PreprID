package org.preprid.model.information;

import lombok.*;
import lombok.experimental.Accessors;

import java.net.URL;

/**
 * Bits and pieces of information to be pulled into different entities as needed.
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class PersonalInformation extends VersionedInformation {

    private String middleNames;

    private String currentFirstName;

    private String currentLastName;

    private String preferredName; //Nick name

    private String gender;

    private URL idPhotoUrl;

}
