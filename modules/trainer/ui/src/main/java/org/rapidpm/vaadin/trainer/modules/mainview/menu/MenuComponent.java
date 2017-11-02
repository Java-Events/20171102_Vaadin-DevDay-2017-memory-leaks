package org.rapidpm.vaadin.trainer.modules.mainview.menu;

import static com.vaadin.icons.VaadinIcons.EXIT;
import static com.vaadin.icons.VaadinIcons.VIEWPORT;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_BORDERLESS;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_HUGE;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_ICON_ALIGN_TOP;
import static com.vaadin.ui.themes.ValoTheme.MENU_ITEM;
import static com.vaadin.ui.themes.ValoTheme.MENU_PART;
import static com.vaadin.ui.themes.ValoTheme.MENU_PART_LARGE_ICONS;
import static org.rapidpm.ddi.DI.activateDI;
import static org.rapidpm.vaadin.ComponentIDGenerator.buttonID;

import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidpm.frp.model.Pair;
import org.rapidpm.vaadin.trainer.api.property.PropertyService;
import org.rapidpm.vaadin.trainer.api.security.login.RoleService;
import org.rapidpm.vaadin.trainer.api.security.user.User;
import org.rapidpm.vaadin.trainer.modules.mainview.MainView;
import org.rapidpm.vaadin.trainer.modules.mainview.dashboard.DashboardComponent;
import org.vaadin.dialogs.ConfirmDialog;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

/**
 *
 */
public class MenuComponent extends Composite {

  public static final String MENU_POINT_DASHBOARD = "menu.point.dashboard";
  public static final String MENU_POINT_EXIT = "menu.point.exit";
  public static final String MENU_POINT_EXIT_MESSAGE = "menu.point.exit.message";
  public static final String MENU_BTN_WIDTH = "100%";

  private final Layout contentLayout;

  public MenuComponent(Layout contentLayout) {this.contentLayout = contentLayout;}


  @Inject private PropertyService propertyService;
  @Inject private RoleService roleService;

  private String property(String key) {
    return propertyService.resolve(key);
  }

  @PostConstruct
  private void postConstruct() {
    final CssLayout menuButtons = new CssLayout();
    menuButtons.setSizeFull();
    menuButtons.addStyleName(MENU_PART);
    menuButtons.addStyleName(MENU_PART_LARGE_ICONS);
    menuButtons.addComponents(getComponents());
    setCompositionRoot(menuButtons);
  }

  private Component[] getComponents() {
    return Stream
        .of(
            createMenuButton(VIEWPORT , MENU_POINT_DASHBOARD , DashboardComponent::new) ,
            createMenuButtonForNotification(EXIT , MENU_POINT_EXIT , MENU_POINT_EXIT_MESSAGE)
        )
        .filter(p -> roleService.hasRole("" , p.getT1())) //TODO how to get logged in user
        .map(Pair::getT2)
        .map(Component.class::cast)
        .toArray(Component[]::new);
  }


  //TODO more generic - refactoring
  private Pair<String, Button> createMenuButtonForNotification(VaadinIcons icon ,
                                                               String caption ,
                                                               String message) {
    final Button button
        = new Button(property(caption) ,
                     (e) -> {
                       UI ui = UI.getCurrent();
                       ConfirmDialog.show(
                           ui ,
                           property(message) ,
                           (ConfirmDialog.Listener) dialog -> {
                             if (dialog.isConfirmed()) {
                               //TODO log out from Security
//                               UI.getCurrent().close();
                               VaadinSession vaadinSession = VaadinSession.getCurrent();
                               vaadinSession.setAttribute(User.class , null);
                               WrappedSession httpSession = vaadinSession.getSession();
                               httpSession.invalidate();
//                               VaadinSession vaadinSession = ui.getSession();
//                               vaadinSession.close();
                               ui.getPage().setLocation("/");
                             } else {
                               // VaadinUser did not confirm
                               // CANCEL STUFF
                             }
                           });
                     });

    button.setIcon(icon);
    button.addStyleName(BUTTON_HUGE);
    button.addStyleName(BUTTON_ICON_ALIGN_TOP);
    button.addStyleName(BUTTON_BORDERLESS);
    button.addStyleName(MENU_ITEM);
    button.setWidth(MENU_BTN_WIDTH);

    button.setId(buttonID().apply(MainView.class , caption));

    return new Pair<>(caption , button);

  }


  private Pair<String, Button> createMenuButton(VaadinIcons icon ,
                                                String caption ,
                                                Supplier<Composite> content) {
    final Button button = new Button(property(caption) , (e) -> {
      contentLayout.removeAllComponents();
      contentLayout.addComponent(activateDI(content.get()));
    });
    button.setIcon(icon);
    button.addStyleName(BUTTON_HUGE);
    button.addStyleName(BUTTON_ICON_ALIGN_TOP);
    button.addStyleName(BUTTON_BORDERLESS);
    button.addStyleName(MENU_ITEM);
    button.setWidth(MENU_BTN_WIDTH);

    button.setId(buttonID().apply(this.getClass() , caption));
    return new Pair<>(caption , button);
  }


}
