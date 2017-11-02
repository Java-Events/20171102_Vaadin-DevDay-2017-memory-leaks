package org.rapidpm.vaadin.trainer.backend.security.role;

import org.rapidpm.vaadin.trainer.api.security.login.RoleService;

/**
 *
 */
public class RoleServiceInMemory  implements RoleService {


  //TODO connect to persistence layer
  //TODO hold in sync to User data
  //TODO hold in sync to menuitems


  @Override
  public boolean hasRole(String login , String requestedRole) {
    return true;
  }
}
