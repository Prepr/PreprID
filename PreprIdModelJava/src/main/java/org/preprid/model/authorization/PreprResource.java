package org.preprid.model.authorization;

import java.net.URL;

/**
 * Objects that require authorization to act upon them.
 */
public interface PreprResource {
    URL getURL();
}
