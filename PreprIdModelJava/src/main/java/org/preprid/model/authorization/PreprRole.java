package org.preprid.model.authorization;

import org.preprid.model.identification.Identifiable;

import java.time.Instant;

/**
 * A role is a collection of permissions given to a subject,
 * for a period of time.
 */
public interface PreprRole {
    Identifiable getSubject();
    Iterable<PreprPermission> getPermissions();
    Instant getUtcTimeOfExpiry();
}
