<#macro navbar>
    <#assign msg = springMacroRequestContext>
    <nav class="navbar">
        <div class="container navbar-inner">

            <a href="/home" class="navbar-brand">
                ${msg.getMessage("common.app.name")}
            </a>

            <#if currentUser??>
                <ul class="navbar-menu">

                    <#if currentUser.role == 'TOURIST'>
                        <li><a href="/excursions">${msg.getMessage("nav.find.excursions")}</a></li>
                        <li><a href="/my-bookings">${msg.getMessage("nav.my.bookings")}</a></li>
                        <li><a href="/guides">${msg.getMessage("nav.all.guides")}</a></li>
                        <li><a href="/my-guides">${msg.getMessage("nav.my.guides")}</a></li>
                        <li><a href="/my-reviews">${msg.getMessage("nav.my.reviews")}</a></li>
                    </#if>

                    <#if currentUser.role == 'GUIDE'>
                        <li><a href="/my-excursions">${msg.getMessage("nav.my.excursions")}</a></li>
                        <li><a href="/reviews-about-me">${msg.getMessage("nav.reviews.about.me")}</a></li>
                        <li><a href="/my-subscribers">${msg.getMessage("nav.my.subscribers")}</a></li>
                        <li><a href="/guide-profile">${msg.getMessage("nav.profile")}</a></li>
                    </#if>

                    <#if currentUser.role == 'ADMIN'>
                        <li><a href="/admin">${msg.getMessage("nav.admin")}</a></li>
                    </#if>

                    <li><a href="/notifications" class="notif-link">
                            ${msg.getMessage("nav.notifications")}
                        </a></li>
                </ul>

                <div class="navbar-right">
                    <div class="lang-tiny">
                        <a href="?lang=ru">RU</a> | <a href="?lang=en">EN</a>
                    </div>

                    <div class="navbar-user">
                        <span class="user-name">${currentUser.fullName}</span>
                        <span class="user-role">${msg.getMessage("role." + currentUser.role)}</span>
                    </div>

                    <form action="/logout" method="post" class="logout-form">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button type="submit" class="btn-logout">
                            ${msg.getMessage("common.logout")}
                        </button>
                    </form>
                </div>
            </#if>

        </div>
    </nav>
</#macro>
