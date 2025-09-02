import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    scenarios: {
        ramping_vus: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '30s', target: 5 },
                { duration: '30s', target: 10 },
                { duration: '30s', target: 15 },
                { duration: '30s', target: 20 },
                { duration: '30s', target: 25 },
                { duration: '30s', target: 30 },
                { duration: '2m',  target: 30 }, // 최대 VU 30 유지
                { duration: '30s', target: 0 },  // 정리
            ],
            gracefulRampDown: '30s',
        },
    },
    thresholds: {
        http_req_failed: ['rate<0.05'],                // 실패율 < 5%
        http_req_duration: ['p(90)<800', 'p(99)<1500'] // 팀 기준에 맞춰 조정
    },
};

export default function () {
    const url = 'http://localhost:8080/api/ranking?period=ONE_MONTH';

    const res = http.get(url, {
        headers: { Accept: 'application/json' },
        tags: { endpoint: 'GET /api/ranking' },
    });

    check(res, {
        'status is 200': (r) => r.status === 200,
    });

    // 너무 공격적이지 않게 짧은 sleep
    sleep(0.05);
}
