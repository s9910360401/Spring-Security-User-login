package com.ogabe.user.controller;

import java.io.IOException;
import java.net.http.HttpRequest;

import java.util.List;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ogabe.user.entity.UserStatusVO;
import com.ogabe.user.entity.UserVO;
import com.ogabe.user.entity.VerificationToken;
import com.ogabe.user.model.UserModel;
import com.ogabe.user.security.UserPrincipal;
import com.ogabe.user.service.EmailService;
import com.ogabe.user.service.UserService;
import com.ogabe.user.service.UserStatusService;
import com.ogabe.user.service.VerificationService;
import com.ogabe.user.ultility.SiteUrl;
import com.ogabe.user.ultility.UserVONotFindException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.bytebuddy.utility.RandomString;


@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	EmailService emailService;

	@Autowired
	VerificationService verificationService;

	@Autowired
	UserStatusService userStatusService;

	@GetMapping("/")
	public String home() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		// ???????????????security?????????username??????anonymousUser
		if (username.equals("anonymousUser")) {
			System.out.println("???????????????");
		} else {
			System.out.println("???????????????");
		}

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

	@GetMapping("/register")
	public String register(Model m) {
//		m.addAttribute("errormsg", "????????????????????????");
		return "user_register";
	}
	
	@PostMapping("/register")
	public String regiTest(@ModelAttribute UserModel usermodel, @RequestParam(name="userpwdconfirm") String  userpwdconfirm, Model m, HttpServletRequest req) throws UnknownHostException {
		
		String useremail = usermodel.getUseremail();
		String userpwdcheck = userpwdconfirm;
		if(userService.getUserByEmail(useremail)!=null) {
//			UserVO uservo = new UserVO();
//			uservo.setUsername("123");
			m.addAttribute("errormsg", "????????????");
			return "user_register";
		}
//		else if (usermodel.getUserpwd()!=userpwdconfirm) {
//			m.addAttribute("errormsg", "???????????????");
//			return "user_register";
//		}
		else {
			UserVO uservo = userService.register(usermodel);

			VerificationToken verificationToken = new VerificationToken(uservo);

			verificationService.saveToken(verificationToken);
			InetAddress addr = InetAddress.getLocalHost();

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(uservo.getUseremail());
			mailMessage.setSubject("Complete Registration!");
			mailMessage.setFrom("tibame105ogabe@gmail.com");
			mailMessage.setText("To confirm your account, please click here : " + SiteUrl.getSiteURL(req)
					+ "/confirm-account?token=" + verificationToken.getConfirmationToken());

			emailService.sendEmail(mailMessage);
			
			return "redirect:/login?register=sucess";
		}
		


		
	}

	@GetMapping("/forgetpassword")
	public String showForgetPasswordForm() {
		return "user_forgetpassword";
	}

	@PostMapping("/forgetpassword")
	public String processForgetPassword(@RequestParam(name = "useremail") String useremail, Model m,
			HttpServletRequest req) {
		String email = useremail;
		String token = RandomString.make(50);
		try {
			userService.updateResetPassword(token, email);
			String resetPasswordLink = SiteUrl.getSiteURL(req) + "/resetPassword?token=" + token;
			String sender = "tibame105ogabe@gmail.com";
			String mailSubject = "OGABE ????????????????????????";
			String mailContent = "<p>?????????????????????,</p>" + "????????????????????????????????????????????????," + "????????????????????????????????????: " + "<br>" + "<a href=\""
					+ resetPasswordLink + "\" > ???????????? </a>" + "<br>" + "???????????????????????????????????????~";

			new Thread(() -> {
				try {
					emailService.sendEmail(email, resetPasswordLink, sender, mailSubject, mailContent);
					m.addAttribute("message", "?????????????????????????????????????????????");
				} catch (MessagingException e) {
					m.addAttribute("message", "??????????????????????????????");
				}

			}).start();

		} catch (UserVONotFindException e) {
			m.addAttribute("error", e.getMessage());
		}
		m.addAttribute("message", "?????????????????????????????????????????????");
		return "user_forgetpassword";
	}

	@GetMapping("/resetPassword")
	public String resetPasswordform(@RequestParam(value = "token") String token, Model m) {

		UserVO uservo = userService.getUserByResetPasswordToken(token);

		if (uservo != null) {
			m.addAttribute("token", token);
		} else {
			m.addAttribute("title", "??????????????????");
			m.addAttribute("message", "???????????????");
			return "message";
		}

		return "user_resetpassword";

	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam(name = "token") String token,
			@RequestParam(name = "userpwd") String userpwd, Model m) {

		UserVO uservo = userService.getUserByResetPasswordToken(token);
		if (uservo != null) {
			userService.resetPassword(uservo, userpwd);
			m.addAttribute("msg", "??????????????????!!");
		}

		return "redirect:/login?msg=reset-sucess";
	}

	@GetMapping("/userpage")
	public String userPage(Model m) {

		UserVO uservo = null;
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			if (principal instanceof UserDetails) {
//				uservo = principal.getUservo();
//			}else {
//				return "redirect:/login";
//			} 

		uservo = principal.getUservo();
		m.addAttribute("uservo", uservo);

		return "user_data";

	}

	@PostMapping("/useredit")
	public String userEdit(@RequestParam("username") String username, @RequestParam("usernickname") String usernickname,
			@RequestParam("useraddress") String useraddress, @RequestParam("usertel") String usertel,
			@RequestParam("userpic") MultipartFile file) throws IOException {

		String useremail = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			useremail = ((UserDetails) principal).getUsername();
		} else {
			useremail = principal.toString();
		}
		System.out.println(((UserPrincipal) principal).getUservo().getUseraddress());
		UserVO temp = userService.getUserByEmail(useremail);

		temp.setUsername(username);
		temp.setUsernickname(usernickname);
		temp.setUseraddress(useraddress);
		temp.setUsertel(usertel);
		if (file.getOriginalFilename() != "") {
			temp.setUserpic(file.getBytes());
		}

		userService.saveUser(temp);
		return "redirect:/userpage";
	}

	@GetMapping("/userchangepassword")
	public String userChangePassword(Model m) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserVO uservo = principal.getUservo();
		m.addAttribute("uservo", uservo);

		return "user_data_changepassword";
	}

	@GetMapping("/admin/userlist")
	public String adminUserList(Model m) {

		List<UserVO> uservo = userService.getAllUser();
		m.addAttribute("uservo", uservo);

		return "user_admin_list";
	}

	@GetMapping("/image/display/{userid}")
	@ResponseBody
	public void showUserImg(@PathVariable Integer userid, HttpServletResponse res, UserVO uservo)
			throws ServletException, IOException {
		System.out.println(userid);
		uservo = userService.getUserById(userid);
		res.setContentType("image/jped, image/jpg, image/png, image/gif");
		res.getOutputStream().write(uservo.getUserpic());
		res.getOutputStream().close();

	}

	@GetMapping("/admin/edit/{userid}")
	public String goAdminEdit(@PathVariable Integer userid, Model m) {
		UserVO uservo = userService.getUserById(userid);
		m.addAttribute("uservo", uservo);

		return "user_admin_update";
	}

	@PostMapping("/admin/edituser")
	public String adminedituser(@RequestParam("userid") Integer userid, @RequestParam("useremail") String useremail,
			@RequestParam("userpwd") String userpwd, @RequestParam("username") String username,
			@RequestParam("usernickname") String usernickname, @RequestParam("useraddress") String useraddress,
			@RequestParam("usertel") String usertel, @RequestParam("viplevelid") Integer viplevelid,
			@RequestParam("userstatus") Integer userstatus, @RequestParam("userpic") MultipartFile file)
			throws IOException {

		UserVO uservo = userService.getUserById(userid);

		uservo.setUseremail(useremail);
		uservo.setUserpwd(userpwd);
		uservo.setUsername(username);
		uservo.setUsernickname(usernickname);
		uservo.setUseraddress(useraddress);
		uservo.setUsertel(usertel);
		uservo.setViplevelid(viplevelid);
		Integer userStatusID = userstatus;
		byte[] userpic = file.getBytes();
		if (file.getOriginalFilename() != "") {
			uservo.setUserpic(userpic);
		}

		userService.adminUserEdit(userStatusID, uservo);

		return "redirect:/admin/userlist";
	}

	@GetMapping("/confirm-account")
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		VerificationToken token = verificationService.findByConfirmationToken(confirmationToken);

		if (token != null) {
			UserVO user = userService.getUserByEmail(token.getUservo().getUseremail());
			user.setUserstatusvo(userStatusService.findById(3));
			userService.saveUser(user);
			modelAndView.setViewName("accountVerified");
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("error");
		}

		return modelAndView;
	}

}
