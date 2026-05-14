<#import "navbar.ftlh" as nav>

<#macro page title="">
    <#assign msg = springMacroRequestContext>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><#if title?has_content>${title} — </#if>${msg.getMessage("common.app.name")}</title>
        <link rel="stylesheet" href="/css/app.css">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
    </head>
    <body>

    <@nav.navbar/>

    <main class="main">
        <div class="container">
            <#nested>
        </div>
    </main>

    <footer class="footer">
        <div class="container">
            <p>&copy; 2026 ${msg.getMessage("common.app.name")}</p>
        </div>
    </footer>

    </body>
    </html>
</#macro>
