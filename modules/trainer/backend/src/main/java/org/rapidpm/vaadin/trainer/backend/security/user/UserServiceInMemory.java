package org.rapidpm.vaadin.trainer.backend.security.user;

import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.trainer.api.security.user.User;
import org.rapidpm.vaadin.trainer.api.security.user.UserService;

/**
 *
 */
public class UserServiceInMemory implements UserService {

  //How to hold in sync with Shiro.ini ??? next part
  @Override
  public Result<User> loadUser(String login) {
    if (login.equals("admin")) return Result.success(new User("admin" , "Admin" , "Secure"));
    if (login.equals("max")) return Result.success(new User("max" , "Max" , "Rimkus"));
    if (login.equals("sven")) return Result.success(new User("sven" , "Sven" , "Ruppert"));
    return Result.failure("User for Login " + login + " not found");
  }

}
