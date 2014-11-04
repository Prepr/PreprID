package org.preprid.model.information;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Created by abouelna on 02/11/2014.
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class PersonalInformation extends VersionedInformation {

    private String middleNames;

}
