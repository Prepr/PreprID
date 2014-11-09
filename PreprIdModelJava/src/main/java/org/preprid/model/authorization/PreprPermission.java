package org.preprid.model.authorization;

/**
 * The permissions needed to take some action on a resource.
 */
public interface PreprPermission {
    PreprResource getObject();
    String getAction();
}
