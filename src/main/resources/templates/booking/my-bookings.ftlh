<#import "../layout/base.ftlh" as layout>
<#import "../layout/macros.ftlh" as ui>
<#assign msg = springMacroRequestContext>

<@layout.page title=msg.getMessage("booking.my.title")>

    <h1>${msg.getMessage("booking.my.title")}</h1>

    <#if RequestParameters.created??>
        <@ui.alert type="success">
            ${msg.getMessage("booking.created.success")}
        </@ui.alert>
    </#if>

    <#if !bookings?has_content>
        <@ui.empty messageKey="booking.my.empty"/>
    <#else>
        <div class="bookings-list">
            <#list bookings as b>
                <div class="booking-item">
                    <div class="booking-main">
                        <h3>
                            <a href="/excursions/${b.excursionId()}">${b.excursionTitle()}</a>
                        </h3>
                        <div class="text-muted mb-1">
                            <#if b.city()??>📍 ${b.city()}</#if>
                            • ${msg.getMessage("excursion.guide")}: ${b.guideName()}
                        </div>
                        <div class="booking-meta">
                            <span>📅 ${b.dateTime()?datetime?string("dd.MM.yyyy HH:mm")}</span>
                            <span>👥 ${b.participantsCount()}</span>
                            <span class="price-tag">${b.totalPrice()} ₽</span>
                        </div>
                        <#if b.specialRequests()?? && b.specialRequests() != ''>
                            <div class="booking-requests">
                                <small>${msg.getMessage("booking.special.requests")}:</small>
                                <p>${b.specialRequests()}</p>
                            </div>
                        </#if>
                    </div>
                    <div class="booking-status">
                        <span class="status status-${b.status()?lower_case}">
                            ${msg.getMessage("booking.status." + b.status())}
                        </span>
                        <div class="text-muted mt-1">
                            <small>
                                ${msg.getMessage("booking.booked.at")}:
                                ${b.bookingDate()?datetime?string("dd.MM.yyyy")}
                            </small>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </#if>

</@layout.page>
