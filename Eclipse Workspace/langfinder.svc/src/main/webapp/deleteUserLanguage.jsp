<%@page import="cs275.langfinder.data.factory.UserController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
int userId = Integer.parseInt(request.getParameter("userId"));
int languageInfoId = Integer.parseInt(request.getParameter("languageInfoId"));

UserController.deleteUserLanguage(userId, languageInfoId);
%>