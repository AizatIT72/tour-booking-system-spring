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

    <#if cancelSuccess??>
        <@ui.alert type="success">
            ${msg.getMessage("booking.cancel.success")}
        </@ui.alert>
    </#if>

    <div class="booking-tabs">
        <a href="/my-bookings"
           class="booking-tab <#if !currentStatus??>booking-tab--active</#if>">
            ${msg.getMessage("booking.filter.all")}
        </a>
        <a href="/my-bookings?status=PENDING"
           class="booking-tab <#if currentStatus?? && currentStatus?string == 'PENDING'>booking-tab--active</#if>">
            ${msg.getMessage("booking.status.PENDING")}
        </a>
        <a href="/my-bookings?status=CONFIRMED"
           class="booking-tab <#if currentStatus?? && currentStatus?string == 'CONFIRMED'>booking-tab--active</#if>">
            ${msg.getMessage("booking.status.CONFIRMED")}
        </a>
        <a href="/my-bookings?status=COMPLETED"
           class="booking-tab <#if currentStatus?? && currentStatus?string == 'COMPLETED'>booking-tab--active</#if>">
            ${msg.getMessage("booking.status.COMPLETED")}
        </a>
        <a href="/my-bookings?status=CANCELLED"
           class="booking-tab <#if currentStatus?? && currentStatus?string == 'CANCELLED'>booking-tab--active</#if>">
            ${msg.getMessage("booking.status.CANCELLED")}
        </a>
    </div>

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

                        <#if b.status()?string == 'PENDING' || b.status()?string == 'CONFIRMED'>
                            <form method="post" action="/my-bookings/${b.id()}/cancel"
                                  class="mt-2"
                                  onsubmit="return confirm('${msg.getMessage("booking.cancel.confirm")}');">
                                <@ui.csrf/>
                                <button type="submit" class="btn-danger btn-sm">
                                    ${msg.getMessage("booking.cancel.button")}
                                </button>
                            </form>
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