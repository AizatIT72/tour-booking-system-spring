<!DOCTYPE html>
<#assign msg = springMacroRequestContext>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${msg.getMessage("common.app.name")}</title>
    <link rel="stylesheet" href="/static/css/auth.css">
</head>
<body>

<div class="lang-switch">
    <a href="?lang=ru">RU</a> | <a href="?lang=en">EN</a>
</div>

<div class="card">
    <div class="card-header">
        <h1>${msg.getMessage("common.app.name")}</h1>
        <p>${msg.getMessage("home.title")}</p>
    </div>
    <div class="card-body">
        <p style="margin-bottom: 16px;">
            ${msg.getMessage("home.greeting")}, <strong>${user.fullName}</strong>!
        </p>
        <p style="margin-bottom: 8px;">${msg.getMessage("home.email")}: ${user.email}</p>
        <p style="margin-bottom: 8px;">${msg.getMessage("home.username")}: ${user.username}</p>
        <p style="margin-bottom: 24px;">${msg.getMessage("home.role")}: ${user.role.name()}</p>

        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn">${msg.getMessage("common.logout")}</button>
        </form>
    </div>
</div>
</body>
</html>
