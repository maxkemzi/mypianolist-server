package com.maxkemzi.mypianolist.dashboard.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maxkemzi.mypianolist.user.model.UserRole;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	@Secured(UserRole.Constants.ADMIN)
	@GetMapping
	public String showDashboard() {
		return "dashboard";
	}
}
