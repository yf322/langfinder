<%@page import="cs275.langfinder.util.GsonSerializer"%><%@page import="cs275.langfinder.data.factory.UserController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
int fromUserId = Integer.parseInt(request.getParameter("fromUserId"));
int toUserId = Integer.parseInt(request.getParameter("toUserId"));
String inviteMessage = request.getParameter("inviteMessage");

UserController.inviteUser(fromUserId, toUserId, inviteMessage);

%>