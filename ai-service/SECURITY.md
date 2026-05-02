# SECURITY.md — Tool-38 AI Service

## Threat Model

| # | Threat | Risk | Mitigation |
|---|--------|------|------------|
| 1 | Prompt Injection | HIGH | Regex sanitisation strips injection keywords |
| 2 | HTML Injection | HIGH | HTML tags stripped from all inputs |
| 3 | Empty Input DoS | MEDIUM | All fields validated, 400 returned |
| 4 | Rate Limit Abuse | MEDIUM | flask-limiter 30 req/min per IP |
| 5 | API Key Exposure | HIGH | Key stored in .env, never committed |

## Tests Conducted

| Test | Input | Expected | Result |
|------|-------|----------|--------|
| Empty input | {} | 400 error | ✅ Pass |
| SQL injection | DROP TABLE in title | Sanitised | ✅ Pass |
| Prompt injection | Ignore previous instructions | Stripped | ✅ Pass |
| HTML injection | script tags | Stripped | ✅ Pass |
| Invalid severity | EXTREME | 400 error | ✅ Pass |

## Findings Fixed
- All inputs sanitised before being passed to Groq
- flask-limiter active on all endpoints
- .env in .gitignore — no secrets committed

## Residual Risks
- Groq API key rotation not automated
- No IP allowlist for Java backend calls

## Sign-off
- AI Developer 1: Lavanya
- AI Developer 2: ________________

## Additional Tests — /generate-report

| Test | Input | Expected | Result |
|------|-------|----------|--------|
| Empty input | {} | 400 error | ✅ Pass |
| Missing field | No root_cause | 400 error | ✅ Pass |
| Invalid severity | EXTREME | 400 error | ✅ Pass |
| Prompt injection | Ignore previous in summary | Stripped | ✅ Pass |
| Valid input | Full incident details | Full JSON report | ✅ Pass |
| Truncated JSON | Groq cuts off response | Auto fixed | ✅ Pass |