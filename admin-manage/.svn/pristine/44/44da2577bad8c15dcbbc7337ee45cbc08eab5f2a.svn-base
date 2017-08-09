package org.bumishi.admin.interfaces.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	/**
	 * 关注时回复
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/attentionReply", method = RequestMethod.GET)
	public String test(Model model) {
	    model.addAttribute("name","关注时回复");
	    return "reply/attentionReply";
	}

}
