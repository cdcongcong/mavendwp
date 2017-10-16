package com.webapp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController {
	
	private Logger log = LoggerFactory.getLogger(getClass().getName());
	private static final String template = "Hello, %s!";
	@Autowired
	private DataSource ds;

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request) {
		
		String path = request.getParameter("path") + "";
		
		Connection c = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT 1";
		log.debug(sql);
		String result = null;

		WebApplicationContext ctx =ContextLoader.getCurrentWebApplicationContext();
//		DataSource d= (DataSource ) ctx.getBean("dataSource");	

		
		try {
			c = ds.getConnection();
			ps = c.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			result = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("sysdate", new Date());
		mav.addObject("sysdate", result);
		mav.setViewName(path);
		return mav;
	}

}
