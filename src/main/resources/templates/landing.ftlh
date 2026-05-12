<!DOCTYPE html>
<#assign msg = springMacroRequestContext>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${msg.getMessage("common.app.name")}</title>
    <link rel="stylesheet" href="/css/auth.css">
    <link rel="stylesheet" href="/css/landing.css">
</head>
<body class="landing-body">

<div class="lang-switch">
    <a href="?lang=ru">RU</a> | <a href="?lang=en">EN</a>
</div>

<header class="landing-header">
    <div class="landing-container">
        <div class="landing-brand">
            <h1>${msg.getMessage("common.app.name")}</h1>
        </div>
        <div class="landing-actions">
            <a href="/sign-in" class="btn-outline">${msg.getMessage("landing.sign.in")}</a>
            <a href="/sign-up" class="btn-primary">${msg.getMessage("landing.sign.up")}</a>
        </div>
    </div>
</header>

<section class="hero">
    <div class="landing-container">
        <h2>${msg.getMessage("landing.hero.title")}</h2>
        <p class="hero-subtitle">${msg.getMessage("landing.hero.subtitle")}</p>
        <a href="/sign-up" class="btn-hero">${msg.getMessage("landing.hero.cta")}</a>
    </div>
</section>

<section class="roles">
    <div class="landing-container">
        <h3>${msg.getMessage("landing.roles.title")}</h3>
        <div class="roles-grid">

            <div class="role-card">
                <div class="role-icon">🎒</div>
                <h4>${msg.getMessage("landing.role.tourist.title")}</h4>
                <ul>
                    <li>${msg.getMessage("landing.role.tourist.f1")}</li>
                    <li>${msg.getMessage("landing.role.tourist.f2")}</li>
                    <li>${msg.getMessage("landing.role.tourist.f3")}</li>
                    <li>${msg.getMessage("landing.role.tourist.f4")}</li>
                    <li>${msg.getMessage("landing.role.tourist.f5")}</li>
                </ul>
            </div>

            <div class="role-card">
                <div class="role-icon">🗺️</div>
                <h4>${msg.getMessage("landing.role.guide.title")}</h4>
                <ul>
                    <li>${msg.getMessage("landing.role.guide.f1")}</li>
                    <li>${msg.getMessage("landing.role.guide.f2")}</li>
                    <li>${msg.getMessage("landing.role.guide.f3")}</li>
                    <li>${msg.getMessage("landing.role.guide.f4")}</li>
                </ul>
            </div>

        </div>
    </div>
</section>

<section class="features">
    <div class="landing-container">
        <h3>${msg.getMessage("landing.features.title")}</h3>
        <div class="features-grid">
            <div class="feature">
                <div class="feature-icon">🤖</div>
                <h5>${msg.getMessage("landing.feature.ai.title")}</h5>
                <p>${msg.getMessage("landing.feature.ai.desc")}</p>
            </div>
            <div class="feature">
                <div class="feature-icon">⭐</div>
                <h5>${msg.getMessage("landing.feature.reviews.title")}</h5>
                <p>${msg.getMessage("landing.feature.reviews.desc")}</p>
            </div>
            <div class="feature">
                <div class="feature-icon">🔔</div>
                <h5>${msg.getMessage("landing.feature.notif.title")}</h5>
                <p>${msg.getMessage("landing.feature.notif.desc")}</p>
            </div>
            <div class="feature">
                <div class="feature-icon">🌐</div>
                <h5>${msg.getMessage("landing.feature.i18n.title")}</h5>
                <p>${msg.getMessage("landing.feature.i18n.desc")}</p>
            </div>
        </div>
    </div>
</section>

<footer class="landing-footer">
    <div class="landing-container">
        <p>&copy; 2026 ${msg.getMessage("common.app.name")}</p>
    </div>
</footer>

</body>
</html>
