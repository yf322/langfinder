<%@page import="cs275.langfinder.data.factory.UserController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
int userId = Integer.parseInt(request.getParameter("userId"));
String aboutText = request.getParameter("aboutText");
String lookingFor = request.getParameter("lookingFor");

UserController.updateProfile(userId, aboutText, lookingFor);

%>