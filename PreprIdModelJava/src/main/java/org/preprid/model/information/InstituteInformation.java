package org.preprid.model.information;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Bits and pieces of information to be pulled into different entities as needed.
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class InstituteInformation extends VersionedInformation {

    private String name;

    private String type;

}
