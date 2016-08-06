<%@page import="cs275.langfinder.data.factory.UserController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
int userId = Integer.parseInt(request.getParameter("userId"));
int languageInfoId = Integer.parseInt(request.getParameter("languageInfoId"));
Boolean wantsToLearn = Boolean.parseBoolean(request.getParameter("wantsToLearn"));
Boolean isNative = Boolean.parseBoolean(request.getParameter("isNative"));
int languageLevelId = Integer.parseInt(request.getParameter("languageLevelId"));

UserController.updateUserLanguage(userId, languageInfoId, wantsToLearn, isNative, languageLevelId);
%>