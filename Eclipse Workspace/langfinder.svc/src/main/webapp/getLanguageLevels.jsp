<%@page import="cs275.langfinder.util.GsonSerializer"%><%@page import="cs275.langfinder.data.factory.TypeController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
out.print(GsonSerializer.serialize(TypeController.getLanguageLevels()));
%>