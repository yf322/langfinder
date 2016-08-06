<%@page import="cs275.langfinder.util.GsonSerializer"%><%@page import="cs275.langfinder.data.factory.UserController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.data.User"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
	int id = Integer.parseInt(request.getParameter("id"));
	out.print(GsonSerializer.serialize(UserController.getUser(id)));
%>