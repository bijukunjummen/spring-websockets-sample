package bk.tailfile.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class TailController {

	@RequestMapping("/home")
	public String home(Model model) {
		model.addAttribute("filename", System.getProperty("java.io.tmpdir") + "/test.txt");
		return "files/home";
	}

}
