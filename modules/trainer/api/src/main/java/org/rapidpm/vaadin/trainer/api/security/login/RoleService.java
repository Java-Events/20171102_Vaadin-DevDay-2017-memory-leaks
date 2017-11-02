package org.rapidpm.vaadin.trainer.api.security.login;

/**
 *
 */
public interface RoleService {
  public boolean hasRole(String login, String requestedRole);
}
