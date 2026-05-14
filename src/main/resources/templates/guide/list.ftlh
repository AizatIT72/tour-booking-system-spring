<#import "../layout/base.ftlh" as layout>
<#import "../layout/macros.ftlh" as ui>
<#assign msg = springMacroRequestContext>

<@layout.page title=msg.getMessage("guide.list.title")>

    <h1>${msg.getMessage("guide.list.title")}</h1>

    <#if !guides?has_content>
        <@ui.empty messageKey="guide.list.empty"/>
    <#else>
        <div class="guides-grid">
            <#list guides as g>
                <div class="guide-card" data-guide-id="${g.userId()}">
                    <div class="guide-card-header">
                        <div class="guide-avatar">
                            ${g.fullName()?substring(0, 1)?upper_case}
                        </div>
                        <div>
                            <h3>${g.fullName()}</h3>
                            <span class="text-muted">@${g.username()}</span>
                        </div>
                    </div>

                    <#if g.bio()?? && g.bio() != ''>
                        <p class="guide-bio">${g.bio()}</p>
                    </#if>

                    <div class="guide-card-meta">
                        <#if g.rating()??>
                            <span class="guide-rating">⭐ ${g.rating()}</span>
                        </#if>
                        <span class="guide-subscribers">
                            👥 <span class="subscriber-count">${g.subscriberCount()}</span>
                            ${msg.getMessage("guide.subscribers")}
                        </span>
                    </div>

                    <#if currentUser.role == 'TOURIST'>
                        <button class="btn-subscribe <#if g.subscribed()>btn-subscribe--active</#if>"
                                data-guide-id="${g.userId()}"
                                data-subscribed="${g.subscribed()?string('true', 'false')}"
                                data-sub-text="${msg.getMessage('guide.subscribe')}"
                                data-unsub-text="${msg.getMessage('guide.unsubscribe')}">
                            <span class="btn-subscribe-text">
                                <#if g.subscribed()>
                                    ${msg.getMessage("guide.unsubscribe")}
                                <#else>
                                    ${msg.getMessage("guide.subscribe")}
                                </#if>
                            </span>
                        </button>
                    </#if>
                </div>
            </#list>
        </div>
    </#if>

    <#if currentUser.role == 'TOURIST'>
        <script src="/js/subscription.js"></script>
    </#if>

</@layout.page>