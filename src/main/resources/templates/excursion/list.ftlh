<#import "../layout/base.ftlh" as layout>
<#import "../layout/macros.ftlh" as ui>
<#assign msg = springMacroRequestContext>

<@layout.page title=msg.getMessage("excursion.list.title")>

    <h1>${msg.getMessage("excursion.list.title")}</h1>

    <form method="get" action="/excursions" class="search-form">
        <div class="search-row">

            <div class="search-field search-field-wide">
                <label for="q">${msg.getMessage("excursion.filter.search")}</label>
                <input type="text" id="q" name="q" value="${(form.q)!''}"
                       placeholder="${msg.getMessage("excursion.filter.search.placeholder")}"/>
            </div>

            <div class="search-field">
                <label for="city">${msg.getMessage("excursion.filter.city")}</label>
                <input type="text" id="city" name="city" value="${(form.city)!''}"
                       placeholder="${msg.getMessage("excursion.filter.city.placeholder")}"/>
            </div>

            <div class="search-field">
                <label for="categoryId">${msg.getMessage("excursion.filter.category")}</label>
                <select id="categoryId" name="categoryId">
                    <option value="">${msg.getMessage("excursion.filter.category.all")}</option>
                    <#list categories as cat>
                        <option value="${cat.id()}"
                                <#if (form.categoryId)?? && form.categoryId == cat.id()>selected</#if>>
                            ${cat.name()}
                        </option>
                    </#list>
                </select>
            </div>

            <div class="search-field">
                <label for="maxPrice">${msg.getMessage("excursion.filter.max.price")}</label>
                <input type="number" id="maxPrice" name="maxPrice"
                       value="${(form.maxPrice)!''}" min="0" step="100"/>
            </div>

            <div class="search-actions">
                <button type="submit" class="btn">${msg.getMessage("excursion.filter.apply")}</button>
                <a href="/excursions" class="btn btn-secondary">${msg.getMessage("excursion.filter.reset")}</a>
            </div>
        </div>
    </form>

    <#if excursionsPage.totalElements == 0>
        <@ui.empty messageKey="excursion.list.empty"/>
    <#else>
        <p class="result-count">
            ${msg.getMessage("excursion.list.total")}: ${excursionsPage.totalElements}
        </p>

        <div class="excursion-grid">
            <#list excursionsPage.content as e>
                <a href="/excursions/${e.id()}" class="excursion-card">

                    <div class="excursion-card-header">
                        <h3>${e.title()}</h3>
                        <#if e.city()??>
                            <div class="excursion-city">📍 ${e.city()}</div>
                        </#if>
                    </div>

                    <div class="excursion-card-body">
                        <p class="excursion-description">${e.shortDescription()}</p>

                        <div class="excursion-meta">
                            <#if e.durationHours()??>
                                <span>⏱ ${e.durationHours()} ${msg.getMessage("excursion.hours")}</span>
                            </#if>
                            <span>👥 ${msg.getMessage("excursion.up.to")} ${e.maxGroupSize()}</span>
                        </div>

                        <#if e.categories()?has_content>
                            <div class="excursion-categories">
                                <#list e.categories() as cat>
                                    <span class="tag">${cat}</span>
                                </#list>
                            </div>
                        </#if>
                    </div>

                    <div class="excursion-card-footer">
                        <div class="excursion-guide">
                            <small>${msg.getMessage("excursion.guide")}:</small>
                            <strong>${e.guideName()}</strong>
                        </div>
                        <div class="excursion-price">${e.price()} ₽</div>
                    </div>

                </a>
            </#list>
        </div>

        <#if excursionsPage.totalPages gt 1>
            <#assign cp = excursionsPage.number>
            <#assign tp = excursionsPage.totalPages>
            <#assign q = "">
            <#if (form.q)?has_content><#assign q = q + "&q=" + form.q></#if>
            <#if (form.city)?has_content><#assign q = q + "&city=" + form.city></#if>
            <#if (form.categoryId)??><#assign q = q + "&categoryId=" + form.categoryId></#if>
            <#if (form.maxPrice)??><#assign q = q + "&maxPrice=" + form.maxPrice></#if>

            <div class="pagination">
                <#if cp gt 0>
                    <a href="?page=${cp - 1}${q}" class="page-link">
                        ← ${msg.getMessage("pagination.prev")}
                    </a>
                </#if>
                <span class="page-info">${cp + 1} / ${tp}</span>
                <#if cp lt (tp - 1)>
                    <a href="?page=${cp + 1}${q}" class="page-link">
                        ${msg.getMessage("pagination.next")} →
                    </a>
                </#if>
            </div>
        </#if>
    </#if>

</@layout.page>
