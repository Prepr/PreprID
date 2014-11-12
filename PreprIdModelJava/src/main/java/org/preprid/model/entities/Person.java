package org.preprid.model.entities;

import lombok.*;
import lombok.experimental.Accessors;
import org.preprid.model.identification.Identifiable;
import org.preprid.model.information.ContactInformation;
import org.preprid.model.information.PersonalInformation;

/**
 * An entity is an identifiable class containing some information (also individually identifiable),
 * and possibly extending some {@link org.preprid.model.authorization.PreprRole}s
 * and/or acting as {@link org.preprid.model.authorization.PreprResource}s.
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class Person extends Identifiable {
    private PersonalInformation personalInformation;
    private ContactInformation contactInformation;
}
