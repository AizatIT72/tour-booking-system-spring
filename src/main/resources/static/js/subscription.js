document.addEventListener('DOMContentLoaded', function () {

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    document.querySelectorAll('.btn-subscribe').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var guideId = btn.dataset.guideId;
            var isSubscribed = btn.dataset.subscribed === 'true';
            var method = isSubscribed ? 'DELETE' : 'POST';

            btn.disabled = true;

            var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            };
            headers[csrfHeader] = csrfToken;

            fetch('/api/subscriptions/' + guideId, {
                method: method,
                headers: headers
            })
                .then(function (response) {
                    if (!response.ok) {
                        throw new Error('HTTP ' + response.status);
                    }
                    return response.json();
                })
                .then(function (data) {
                    btn.dataset.subscribed = String(data.subscribed);

                    if (data.subscribed) {
                        btn.classList.add('btn-subscribe--active');
                        btn.querySelector('.btn-subscribe-text').textContent = btn.dataset.unsubText;
                    } else {
                        btn.classList.remove('btn-subscribe--active');
                        btn.querySelector('.btn-subscribe-text').textContent = btn.dataset.subText;
                    }

                    var card = btn.closest('.guide-card');
                    if (card) {
                        var counter = card.querySelector('.subscriber-count');
                        if (counter) {
                            counter.textContent = data.subscriberCount;
                        }
                    }
                })
                .catch(function (err) {
                    console.error('Subscription error:', err);
                })
                .finally(function () {
                    btn.disabled = false;
                });
        });
    });
});