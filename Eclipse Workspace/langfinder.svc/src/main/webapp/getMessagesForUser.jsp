<%@page import="cs275.langfinder.util.GsonSerializer"%><%@page import="cs275.langfinder.data.factory.MessageController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
int owningUserId = Integer.parseInt(request.getParameter("owningUserId"));
int userMessageFolderId = Integer.parseInt(request.getParameter("userMessageFolderId"));
out.print(GsonSerializer.serialize(MessageController.getMessagesForUser(owningUserId, userMessageFolderId)));

%>