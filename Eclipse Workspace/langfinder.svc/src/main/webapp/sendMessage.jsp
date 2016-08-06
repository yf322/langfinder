<%@page import="cs275.langfinder.data.factory.MessageController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);

int fromUserId = Integer.parseInt(request.getParameter("fromUserId"));
int toUserId = Integer.parseInt(request.getParameter("toUserId"));
String subject = request.getParameter("subject");
String message = request.getParameter("message");

MessageController.sendMessage(fromUserId, toUserId, subject, message);
%>