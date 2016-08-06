<%@page import="cs275.langfinder.util.GsonSerializer"%><%@page import="cs275.langfinder.data.factory.UserController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
int userId = Integer.parseInt(request.getParameter("userId"));
int lanuageInfoId = Integer.parseInt(request.getParameter("lanuageInfoId"));

out.print(GsonSerializer.serialize(UserController.getUserLanguage(userId, lanuageInfoId)));
%>