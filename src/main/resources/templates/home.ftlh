<#import "layout/base.ftlh" as layout>
<#import "layout/macros.ftlh" as ui>

<@layout.page title=springMacroRequestContext.getMessage("home.title")>

    <@ui.card title=springMacroRequestContext.getMessage("home.greeting") + ", " + currentUser.fullName + "!">
        <p><strong>${springMacroRequestContext.getMessage("home.email")}:</strong> ${currentUser.email}</p>
        <p><strong>${springMacroRequestContext.getMessage("home.username")}:</strong> ${currentUser.username}</p>
        <p><strong>${springMacroRequestContext.getMessage("home.role")}:</strong> ${springMacroRequestContext.getMessage("role." + currentUser.role)}</p>
    </@ui.card>

    <#if currentUser.role == 'TOURIST'>
        <@ui.alert type="info">
            ${springMacroRequestContext.getMessage("home.welcome.tourist")}
        </@ui.alert>
    <#elseif currentUser.role == 'GUIDE'>
        <@ui.alert type="info">
            ${springMacroRequestContext.getMessage("home.welcome.guide")}
        </@ui.alert>
    </#if>

</@layout.page>
