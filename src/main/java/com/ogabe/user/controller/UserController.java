package com.ogabe.user.controller;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.sql.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ogabe.user.entity.UserStatusVO;
import com.ogabe.user.entity.UserVO;
import com.ogabe.user.model.UserModel;
import com.ogabe.user.service.UserService;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	UserService service;
	
	@GetMapping("/")
	public String home() {
		
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {

		return "user_login";
	}
	
    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "accessDenied";
    }
	
	@PostMapping("/getregister")
	public String regiTest(@ModelAttribute UserModel usermodel) {

		service.register(usermodel);
		
		return "redirect:/login";
	}

	
	@GetMapping("/userpage")
	public String UserPage(Model m) {
			
			String useremail = null;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				useremail = ((UserDetails)principal).getUsername();
			}else {
				useremail = principal.toString();
			}
			UserVO uservo = service.getUserByEmail(useremail);
			m.addAttribute("uservo", uservo);
			

			return "user_data";

	}
	
	@PostMapping("/useredit")
	public String userEdit(
			@RequestParam("username") String username,
			@RequestParam("usernickname") String usernickname,
			@RequestParam("useraddress") String useraddress,
			@RequestParam("usertel") String usertel,
			@RequestParam("userpic") MultipartFile file
			) throws IOException {
		
		String useremail = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			useremail = ((UserDetails)principal).getUsername();
		}else {
			useremail = principal.toString();
		}
		UserVO temp = service.getUserByEmail(useremail);

		temp.setUsername(username);
		temp.setUsernickname(usernickname);
		temp.setUseraddress(useraddress);
		temp.setUsertel(usertel);
		if(file.getOriginalFilename()!="") {
			temp.setUserpic(file.getBytes());
		}
		
		service.saveUser(temp);
		return "redirect:/userpage";
	}
	
	@GetMapping("/register")
	public String register() {
		return "user_register";
	}
	
	
	
	@GetMapping("/admin/userlist")
	public String adminUserList(Model m) {
		
		List<UserVO> uservo = service.getAllUser();
		m.addAttribute("uservo", uservo);
		
		return "user_admin_list";
	}
	
	@GetMapping("/image/display/{userid}")
	@ResponseBody
	public void showUserImg(@PathVariable Integer userid, HttpServletResponse res,
			UserVO uservo) throws ServletException, IOException{
		
		uservo = service.getUserById(userid);
		res.setContentType("image/jped, image/jpg, image/png, image/gif");
		res.getOutputStream().write(uservo.getUserpic());
		res.getOutputStream().close();
		
	}
	
	@GetMapping("/admin/edit/{userid}")
	public String goAdminEdit(@PathVariable Integer userid, Model m) {
		UserVO uservo = service.getUserById(userid);
		m.addAttribute("uservo", uservo);
		
		return "user_admin_update";
	}
	
	@PostMapping("/admin/edituser")
	public String adminedituser(
			@RequestParam("userid") Integer userid,
			@RequestParam("useremail") String useremail,
			@RequestParam("userpwd") String userpwd,
			@RequestParam("username") String username,
			@RequestParam("usernickname") String usernickname,
			@RequestParam("useraddress") String useraddress,
			@RequestParam("usertel") String usertel,
			@RequestParam("viplevelid") Integer viplevelid,
			@RequestParam("userstatus") Integer userstatus,
			@RequestParam("userpic") MultipartFile file
			) throws IOException {
		
		UserVO uservo = service.getUserById(userid);

		uservo.setUseremail(useremail);
		uservo.setUserpwd(userpwd);
		uservo.setUsername(username);
		uservo.setUsernickname(usernickname);
		uservo.setUseraddress(useraddress);
		uservo.setUsertel(usertel);
		uservo.setViplevelid(viplevelid);
		Integer userStatusID = userstatus;
		byte[] userpic = file.getBytes();
		if(file.getOriginalFilename()!="") {
			uservo.setUserpic(userpic);
		}
		
		service.adminUserEdit(userStatusID, uservo);
		
		return "redirect:/admin/userlist";
		
	}

}
