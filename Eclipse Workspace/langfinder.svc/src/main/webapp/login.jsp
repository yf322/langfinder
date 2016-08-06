<%@page import="cs275.langfinder.util.GsonSerializer"%><%@page import="cs275.langfinder.data.factory.UserController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
String username = request.getParameter("email");
String password = request.getParameter("passwordHash");

out.print(GsonSerializer.serialize(UserController.login(username, password)));

%>