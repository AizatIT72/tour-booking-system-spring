<!DOCTYPE html>
<#assign msg = springMacroRequestContext>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${msg.getMessage("auth.signin.title")} — ${msg.getMessage("common.app.name")}</title>
    <link rel="stylesheet" href="/css/auth.css">
</head>
<body>

<div class="lang-switch">
    <a href="?lang=ru">RU</a> | <a href="?lang=en">EN</a>
</div>

<div class="card">
    <div class="card-header">
        <h1>${msg.getMessage("common.app.name")}</h1>
        <p>${msg.getMessage("auth.signin.title")}</p>
    </div>
    <div class="card-body">

        <#if RequestParameters.error??>
            <div class="alert">${msg.getMessage("auth.signin.error")}</div>
        </#if>

        <#if RequestParameters.logout??>
            <div class="alert success">${msg.getMessage("auth.signin.logout.success")}</div>
        </#if>

        <form action="/sign-in" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <div class="form-group">
                <label for="email">${msg.getMessage("auth.signin.email")}</label>
                <input type="email" id="email" name="email" placeholder="example@mail.com" required/>
            </div>

            <div class="form-group">
                <label for="password">${msg.getMessage("auth.signin.password")}</label>
                <input type="password" id="password" name="password" required/>
            </div>

            <button type="submit" class="btn">${msg.getMessage("auth.signin.submit")}</button>
        </form>

        <div class="footer-link">
            ${msg.getMessage("auth.signin.no.account")}
            <a href="/sign-up">${msg.getMessage("auth.signin.signup.link")}</a>
        </div>
    </div>
</div>
</body>
</html>
