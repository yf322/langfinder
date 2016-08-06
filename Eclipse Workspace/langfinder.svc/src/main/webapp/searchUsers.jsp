<%@page import="cs275.langfinder.util.GsonSerializer"%><%@page import="cs275.langfinder.data.factory.UserController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);

String name = request.getParameter("name");
Integer languageInfoId = null;
if(request.getParameter("languageInfoId")!=null && request.getParameter("languageInfoId").trim().length()>0)
	languageInfoId = Integer.parseInt(request.getParameter("languageInfoId"));

Integer languageLevelId = null;
if(request.getParameter("languageInfoId")!=null && request.getParameter("languageInfoId").trim().length()>0)
	languageLevelId = Integer.parseInt(request.getParameter("languageLevelId"));

out.print(GsonSerializer.serialize(UserController.searchUsers(name, languageInfoId, languageLevelId)));

%>