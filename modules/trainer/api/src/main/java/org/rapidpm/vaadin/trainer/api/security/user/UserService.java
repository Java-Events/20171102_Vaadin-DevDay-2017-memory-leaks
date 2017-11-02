package org.rapidpm.vaadin.trainer.api.security.user;

import org.rapidpm.frp.model.Result;

/**
 *
 */
public interface UserService {
  Result<User> loadUser(String login);
}
