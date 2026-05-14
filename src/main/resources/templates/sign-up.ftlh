<!DOCTYPE html>
<#assign msg = springMacroRequestContext>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${msg.getMessage("auth.signup.title")} — ${msg.getMessage("common.app.name")}</title>
    <link rel="stylesheet" href="/css/auth.css">
</head>
<body>

<div class="lang-switch">
    <a href="?lang=ru">RU</a> | <a href="?lang=en">EN</a>
</div>

<div class="card">
    <div class="card-header">
        <h1>${msg.getMessage("common.app.name")}</h1>
        <p>${msg.getMessage("auth.signup.title")}</p>
    </div>
    <div class="card-body">

        <#if globalError??>
            <div class="alert">${globalError}</div>
        </#if>

        <form action="/sign-up" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <div class="form-group">
                <label for="email">${msg.getMessage("auth.signup.email")}</label>
                <input type="email" id="email" name="email"
                       placeholder="${msg.getMessage("auth.signup.email.placeholder")}"
                       value="${(form.email)!''}"/>
                <#if errors?? && errors.email??>
                    <div class="error">${errors.email}</div>
                </#if>
            </div>

            <div class="form-group">
                <label for="password">${msg.getMessage("auth.signup.password")}</label>
                <input type="password" id="password" name="password"
                       placeholder="${msg.getMessage("auth.signup.password.placeholder")}"/>
                <#if errors?? && errors.password??>
                    <div class="error">${errors.password}</div>
                <#else>
                    <div class="hint">${msg.getMessage("auth.signup.password.hint")}</div>
                </#if>
            </div>

            <div class="form-group">
                <label for="username">${msg.getMessage("auth.signup.username")}</label>
                <input type="text" id="username" name="username"
                       placeholder="${msg.getMessage("auth.signup.username.placeholder")}"
                       value="${(form.username)!''}"/>
                <#if errors?? && errors.username??>
                    <div class="error">${errors.username}</div>
                </#if>
            </div>

            <div class="form-group">
                <label for="fullName">${msg.getMessage("auth.signup.fullname")}</label>
                <input type="text" id="fullName" name="fullName"
                       placeholder="${msg.getMessage("auth.signup.fullname.placeholder")}"
                       value="${(form.fullName)!''}"/>
                <#if errors?? && errors.fullName??>
                    <div class="error">${errors.fullName}</div>
                </#if>
            </div>

            <div class="form-group">
                <label for="role">${msg.getMessage("auth.signup.role")}</label>
                <select id="role" name="role">
                    <option value="" disabled <#if !(form.role)?? || form.role == ''>selected</#if>>
                        ${msg.getMessage("auth.signup.role.placeholder")}
                    </option>
                    <option value="TOURIST" <#if (form.role)?? && form.role == 'TOURIST'>selected</#if>>
                        ${msg.getMessage("auth.signup.role.tourist")}
                    </option>
                    <option value="GUIDE" <#if (form.role)?? && form.role == 'GUIDE'>selected</#if>>
                        ${msg.getMessage("auth.signup.role.guide")}
                    </option>
                </select>
                <#if errors?? && errors.role??>
                    <div class="error">${errors.role}</div>
                </#if>
            </div>

            <button type="submit" class="btn">${msg.getMessage("auth.signup.submit")}</button>
        </form>

        <div class="footer-link">
            ${msg.getMessage("auth.signup.have.account")}
            <a href="/sign-in">${msg.getMessage("auth.signup.signin.link")}</a>
        </div>
    </div>
</div>
</body>
</html>
