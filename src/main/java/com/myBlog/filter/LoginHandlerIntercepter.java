package com.myBlog.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.myBlog.domain.User;

/**
 * ��½������ �������û�����鿴��ʱ�����ǽ��е�½�������������ж��û��Ƿ��½�� ��½�������أ�û��½����ת����½���棻 TODO ���ߣ�ԭ��׿
 * ʱ�䣺2016��1��8�� ����3:25:35 ���̣�SpringMvcMybatis1Demo
 */
public class LoginHandlerIntercepter implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2) throws Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			// ��½�ɹ����û�
			return true;
		} else {
			// û�е�½��ת���½����
			request.getRequestDispatcher("/admin").forward(request, arg1);
			return false;
		}
	}

}