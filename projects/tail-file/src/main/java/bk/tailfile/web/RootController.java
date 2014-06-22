package bk.tailfile.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The only purpose of this root controller is to redirect the user on root context 
 * access to the right home page
 * 
 *
 */
@Controller
public class RootController {
	@RequestMapping("/")
	public String onRootAccess() {
		return "redirect:/files/home";
	}
}
