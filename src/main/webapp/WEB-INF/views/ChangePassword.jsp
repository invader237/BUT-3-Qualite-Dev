<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
  <title>Changer le mot de passe</title>
  <link rel="stylesheet" href="/_00_ASBank2023/style/style.css" />
</head>
<body>
<h1>Changer votre mot de passe</h1>

<s:form action="changePassword" method="POST">
  <s:password name="oldPassword" label="Ancien mot de passe" />
  <s:password name="newPassword" label="Nouveau mot de passe" />
  <s:password name="confirmPassword" label="Confirmer le mot de passe" />
  <s:submit value="Valider" />
</s:form>
</body>
</html>
