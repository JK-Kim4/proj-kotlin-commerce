import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';
const URL = `${BASE_URL}/api/ranking?period=ONE_MONTH`;

export const options = {
    scenarios: {
        warmup: {
            executor: 'constant-vus',
            vus: Number(__ENV.WARM_VUS || 10),
            duration: __ENV.WARM_DURATION || '3m',
            exec: 'run',
        },
        main: {
            executor: 'constant-vus',
            vus: Number(__ENV.MAIN_VUS || 120),   // 130 미만으로 유지
            duration: __ENV.MAIN_DURATION || '12m',
            startTime: __ENV.WARM_DURATION || '3m',
            exec: 'run',
            gracefulStop: '30s',
        },
    },
    summaryTrendStats: ['avg','min','med','max','p(90)','p(95)','p(99)'],
    thresholds: {
        http_req_failed: ['rate<0.05'],
        // 관측 목적: P99 산출이 주목적이므로 최초 임계치는 널널하게 두고, 튜닝 후 협소화
        http_req_duration: ['p(99)<30000'],
    },
    discardResponseBodies: true,
};

export function run() {
    const res = http.get(URL, {
        headers: { Accept: 'application/json', Connection: 'keep-alive' },
        timeout: '30s',
        tags: { endpoint: 'GET /api/ranking' },
    });
    check(res, { 'status is 200': (r) => r.status === 200 });
    // VU 고정이라 sleep은 아주 짧게만
    sleep(0.01);
}
