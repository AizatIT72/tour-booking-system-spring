<#import "../layout/base.ftlh" as layout>
<#import "../layout/macros.ftlh" as ui>
<#assign msg = springMacroRequestContext>

<@layout.page title=msg.getMessage("booking.new.title")>

    <a href="/excursions/${excursion.id()}" class="back-link">
        ← ${msg.getMessage("booking.back.to.excursion")}
    </a>

    <h1>${msg.getMessage("booking.new.title")}</h1>

    <div class="booking-grid">

        <@ui.card title=msg.getMessage("booking.summary")>
            <h3>${excursion.title()}</h3>
            <p class="text-muted">${excursion.city()!''}</p>

            <#list excursion.dates() as d>
                <#if d.id() == selectedDateId>
                    <div class="selected-date">
                        <strong>📅 ${d.dateTime()?datetime?string("dd.MM.yyyy HH:mm")}</strong>
                        <div class="text-muted">
                            ${msg.getMessage("excursion.dates.slots.left")}: ${d.availableSlots()}
                        </div>
                    </div>
                </#if>
            </#list>

            <p class="mt-2">
                <strong>${msg.getMessage("booking.price.per.person")}:</strong>
                ${excursion.price()} ₽
            </p>
        </@ui.card>

        <@ui.card title=msg.getMessage("booking.fill.form")>
            <form method="post" action="/bookings?excursionId=${excursion.id()}">
                <@ui.csrf/>
                <input type="hidden" name="excursionDateId" value="${form.excursionDateId}"/>

                <div class="form-group">
                    <label for="participantsCount">
                        ${msg.getMessage("booking.participants")}
                    </label>
                    <input type="number"
                           id="participantsCount"
                           name="participantsCount"
                           value="${(form.participantsCount)!1}"
                           min="1"
                           max="${excursion.maxGroupSize()}"/>
                    <#if errors?? && errors.participantsCount??>
                        <div class="error">${errors.participantsCount}</div>
                    </#if>
                </div>

                <div class="form-group">
                    <label for="specialRequests">
                        ${msg.getMessage("booking.special.requests")}
                    </label>
                    <textarea id="specialRequests"
                              name="specialRequests"
                              rows="3"
                              placeholder="${msg.getMessage("booking.special.requests.placeholder")}"
                    >${(form.specialRequests)!''}</textarea>
                    <#if errors?? && errors.specialRequests??>
                        <div class="error">${errors.specialRequests}</div>
                    </#if>
                </div>

                <@ui.submit labelKey="booking.submit"/>
            </form>
        </@ui.card>

    </div>

</@layout.page>
