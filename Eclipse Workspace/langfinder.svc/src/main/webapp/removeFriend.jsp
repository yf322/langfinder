<%@page import="cs275.langfinder.util.GsonSerializer"%><%@page import="cs275.langfinder.data.factory.UserController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
Integer fromUserId = Integer.parseInt(request.getParameter("fromUserId"));
Integer otherUserId = Integer.parseInt(request.getParameter("otherUserId"));

UserController.removeFriend(fromUserId, otherUserId);

%>