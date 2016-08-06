<%@page import="cs275.langfinder.data.factory.UserController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
int userId = Integer.parseInt(request.getParameter("userId"));
int languageId = Integer.parseInt(request.getParameter("languageId"));
boolean wantsToLearn = Boolean.parseBoolean(request.getParameter("wantsToLearn"));
boolean isNative = Boolean.parseBoolean(request.getParameter("isNative"));
int languageLevelId = Integer.parseInt(request.getParameter("languageLevelId"));

UserController.createUserLanguage(userId, languageId, wantsToLearn, isNative, languageLevelId);

%>