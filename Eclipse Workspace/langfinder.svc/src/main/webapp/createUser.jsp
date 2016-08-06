<%@page import="cs275.langfinder.util.GsonSerializer"%><%@page import="cs275.langfinder.data.factory.UserController"%><%@page import="com.google.gson.Gson"%><%@page import="cs275.langfinder.util.HttpCacher"%><%
HttpCacher.setup(request, response, session);
String email = request.getParameter("email");
String passwordHash = request.getParameter("passwordHash");
String firstName = request.getParameter("firstName");
String lastName = request.getParameter("lastName");

int userId = UserController.createUser(email, passwordHash, firstName, lastName);

out.print(GsonSerializer.serialize(userId));

%>