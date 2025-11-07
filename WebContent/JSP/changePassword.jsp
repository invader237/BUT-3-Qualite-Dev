<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>Changer le mot de passe</title>
</head>
<body>
<h1>Changer votre mot de passe</h1>

<s:form action="saveNewPassword" method="POST">
    <s:password name="oldPassword" label="Ancien mot de passe" />
    <s:password name="newPassword" label="Nouveau mot de passe" />
    <s:password name="confirmPassword" label="Confirmer le mot de passe" />
    <s:submit value="Valider" />
</s:form>
</body>
</html>
