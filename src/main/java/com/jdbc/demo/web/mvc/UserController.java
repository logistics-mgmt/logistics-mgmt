package com.jdbc.demo.web.mvc;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jdbc.demo.UserProfileService;
import com.jdbc.demo.UserService;
import com.jdbc.demo.domain.security.User;
import com.jdbc.demo.domain.security.UserProfile;

@Controller
@RequestMapping("/users")
@SessionAttributes("roles")
public class UserController {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = { "/" , "users_list"}, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "users_list";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		return "add_user";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String addUser(@Valid User user, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "add_user";
		}

		if (!userService.isUserSSOUnique(user.getId(), user.getSsoId())) {
			FieldError ssoError = new FieldError("user", "ssoId", messageSource.getMessage("non.unique.ssoId", new
			String[] { user.getSsoId() }, Locale.getDefault()));
			result.addError(ssoError);
			return "add_user";
		}

		userService.addUser(user);


		return "redirect:/users/users_list";
	}

	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String ssoId, ModelMap model) {
		User user = userService.getBySSO(ssoId);
		model.addAttribute("user", user);

		return "edit_user";
	}

	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result, ModelMap model, @PathVariable String ssoId) {

		if (result.hasErrors()) {
			return "edit_user";
		}

		userService.updateUser(user);

	
		return "redirect:/users/users_list";
	}

	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String ssoId) {
		userService.deleteUserBySSO(ssoId);
		return "redirect:/users/users_list";
	}

	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.getAll();
	}

}
