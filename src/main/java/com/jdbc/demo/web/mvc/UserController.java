package com.jdbc.demo.web.mvc;

import java.util.List;

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

import com.jdbc.demo.domain.security.User;
import com.jdbc.demo.domain.security.UserRole;
import com.jdbc.demo.services.security.UserRoleService;
import com.jdbc.demo.services.security.UserService;

@Controller
@RequestMapping("/users")
@SessionAttributes("roles")
public class UserController {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		List<User> users = userService.getAll();
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

		if (!userService.isUserLoginUnique(user.getId(), user.getLogin())) {
			FieldError loginError = new FieldError("user", "login", "Login musi byc unikalny!");
			result.addError(loginError);
			return "add_user";
		}

		userService.addUser(user);


		return "redirect:/users/";
	}

	@RequestMapping(value = { "/edit/{login}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String login, ModelMap model) {
		User user = userService.getByLogin(login);
		model.addAttribute("user", user);

		return "edit_user";
	}

	@RequestMapping(value = { "/edit/{login}" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result, ModelMap model, @PathVariable String login) {

		if (result.hasErrors()) {
			return "edit_user";
		}

		userService.updateUser(user);

	
		return "redirect:/users/";
	}

	@RequestMapping(value = { "/delete/{login}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String login) {
		userService.deleteUserByLogin(login);
		return "redirect:/users/";
	}

	@ModelAttribute("roles")
	public List<UserRole> initializeRoles() {
		return userRoleService.getAll();
	}

}
