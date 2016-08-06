<%@page import="cs275.langfinder.util.GsonSerializer"%><%@page import="cs275.langfinder.data.factory.MessageController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
int messageId = Integer.parseInt(request.getParameter("messageId"));

out.print(GsonSerializer.serialize(MessageController.getMessage(messageId)));

%>