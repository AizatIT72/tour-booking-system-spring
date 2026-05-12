<!DOCTYPE html>
<#assign msg = springMacroRequestContext>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${msg.getMessage("error.not.found")} — ${msg.getMessage("common.app.name")}</title>
    <link rel="stylesheet" href="/css/auth.css">
</head>
<body>
<div class="card">
    <div class="card-header">
        <h1>404</h1>
        <p>${msg.getMessage("error.not.found")}</p>
    </div>
    <div class="card-body">
        <p style="margin-bottom: 24px; color: #4b5563;">
            ${msg.getMessage("error.not.found.description")}
        </p>
        <a href="/" class="btn" style="display: block; text-align: center; text-decoration: none;">
            ${msg.getMessage("error.back.home")}
        </a>
    </div>
</div>
</body>
</html>
