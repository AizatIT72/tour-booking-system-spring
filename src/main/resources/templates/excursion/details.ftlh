<#import "../layout/base.ftlh" as layout>
<#import "../layout/macros.ftlh" as ui>
<#assign msg = springMacroRequestContext>

<@layout.page title=excursion.title()>

    <a href="/excursions" class="back-link">
        ← ${msg.getMessage("excursion.back.to.list")}
    </a>

    <@ui.card>
        <h1>${excursion.title()}</h1>

        <div class="excursion-detail-meta">
            <#if excursion.city()??>
                <span>📍 ${excursion.city()}</span>
            </#if>
            <#if excursion.durationHours()??>
                <span>⏱ ${excursion.durationHours()} ${msg.getMessage("excursion.hours")}</span>
            </#if>
            <span>👥 ${msg.getMessage("excursion.up.to")} ${excursion.maxGroupSize()}</span>
            <span class="price-tag">${excursion.price()} ₽</span>
        </div>

        <#if excursion.categories()?has_content>
            <div class="excursion-categories mb-2">
                <#list excursion.categories() as cat>
                    <span class="tag">${cat}</span>
                </#list>
            </div>
        </#if>

        <#if excursion.description()??>
            <h3>${msg.getMessage("excursion.description")}</h3>
            <p class="excursion-full-description">${excursion.description()}</p>
        </#if>
    </@ui.card>

    <@ui.card title=msg.getMessage("excursion.guide")>
        <p>
            <strong>${excursion.guideName()}</strong>
            <span class="text-muted">(@${excursion.guideUsername()})</span>
        </p>
    </@ui.card>

    <@ui.card title=msg.getMessage("excursion.dates.title")>

        <#if !excursion.dates()?has_content>
            <p class="text-muted">${msg.getMessage("excursion.dates.empty")}</p>
        <#else>
            <#if currentUser.role != 'TOURIST'>
                <@ui.alert type="info">
                    ${msg.getMessage("excursion.dates.tourist.only")}
                </@ui.alert>
            </#if>

            <div class="dates-list">
                <#list excursion.dates() as d>
                    <div class="date-item">
                        <div class="date-info">
                            <div class="date-when">
                                📅 ${d.dateTime()?datetime?string("dd.MM.yyyy HH:mm")}
                            </div>
                            <div class="date-slots">
                                <#if d.available() && d.availableSlots() gt 0>
                                    <span class="slots-available">
                                        ${msg.getMessage("excursion.dates.slots.left")}: ${d.availableSlots()}
                                    </span>
                                <#else>
                                    <span class="slots-empty">
                                        ${msg.getMessage("excursion.dates.no.slots")}
                                    </span>
                                </#if>
                            </div>
                        </div>

                        <div class="date-actions">
                            <#if currentUser.role == 'TOURIST'
                            && d.available()
                            && d.availableSlots() gt 0>
                                <a href="/bookings/new?excursionId=${excursion.id()}&dateId=${d.id()}"
                                   class="btn btn-sm">
                                    ${msg.getMessage("excursion.dates.book")}
                                </a>
                            </#if>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>

    </@ui.card>

</@layout.page>
