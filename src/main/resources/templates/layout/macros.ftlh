<#--
  Переиспользуемые макросы (наши "свои теги").
  Подключение:
    <#import "layout/macros.ftlh" as ui>
-->

<#--
  Текстовое поле формы.
-->
<#macro textField name labelKey value="" placeholderKey="" type="text" errors={}>
    <#assign msg = springMacroRequestContext>
    <div class="form-group">
        <label for="${name}">${msg.getMessage(labelKey)}</label>
        <input type="${type}" id="${name}" name="${name}"
               <#if placeholderKey?has_content>placeholder="${msg.getMessage(placeholderKey)}"</#if>
               value="${value}"/>
        <#if errors?? && errors[name]??>
            <div class="error">${errors[name]}</div>
        </#if>
    </div>
</#macro>

<#--
  Поле пароля.
-->
<#macro passwordField name labelKey placeholderKey="" hintKey="" errors={}>
    <#assign msg = springMacroRequestContext>
    <div class="form-group">
        <label for="${name}">${msg.getMessage(labelKey)}</label>
        <input type="password" id="${name}" name="${name}"
               <#if placeholderKey?has_content>placeholder="${msg.getMessage(placeholderKey)}"</#if>/>
        <#if errors?? && errors[name]??>
            <div class="error">${errors[name]}</div>
        <#elseif hintKey?has_content>
            <div class="hint">${msg.getMessage(hintKey)}</div>
        </#if>
    </div>
</#macro>

<#--
  Textarea.
-->
<#macro textareaField name labelKey value="" rows=4 errors={}>
    <#assign msg = springMacroRequestContext>
    <div class="form-group">
        <label for="${name}">${msg.getMessage(labelKey)}</label>
        <textarea id="${name}" name="${name}" rows="${rows}">${value}</textarea>
        <#if errors?? && errors[name]??>
            <div class="error">${errors[name]}</div>
        </#if>
    </div>
</#macro>

<#--
  Кнопка отправки.
-->
<#macro submit labelKey class="btn">
    <#assign msg = springMacroRequestContext>
    <button type="submit" class="${class}">${msg.getMessage(labelKey)}</button>
</#macro>

<#--
  CSRF токен (одной строкой).
-->
<#macro csrf>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</#macro>

<#--
  Карточка контента.
-->
<#macro card title="">
    <div class="card-block">
        <#if title?has_content>
            <div class="card-block-title">${title}</div>
        </#if>
        <div class="card-block-body">
            <#nested>
        </div>
    </div>
</#macro>

<#--
  Алерт: info / success / error / warning.
-->
<#macro alert type="info">
    <div class="alert alert-${type}">
        <#nested>
    </div>
</#macro>

<#--
  Пустое состояние (когда нет данных).
-->
<#macro empty messageKey>
    <#assign msg = springMacroRequestContext>
    <div class="empty-state">
        ${msg.getMessage(messageKey)}
    </div>
</#macro>
