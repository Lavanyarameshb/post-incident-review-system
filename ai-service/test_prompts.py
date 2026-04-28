from services.groq_client import call_groq
from datetime import datetime
import json

# 5 real test inputs
test_inputs = [
    {
        "title": "Database Connection Timeout",
        "description": "PostgreSQL database stopped accepting connections during peak hours",
        "severity": "HIGH",
        "affected_system": "Payment Service",
        "duration": "45 minutes"
    },
    {
        "title": "API Gateway 503 Errors",
        "description": "API gateway returned 503 for all requests due to memory exhaustion",
        "severity": "CRITICAL",
        "affected_system": "API Gateway",
        "duration": "20 minutes"
    },
    {
        "title": "File Upload Service Down",
        "description": "S3 bucket permissions were revoked causing all uploads to fail",
        "severity": "MEDIUM",
        "affected_system": "File Storage",
        "duration": "2 hours"
    },
    {
        "title": "Login Service Latency Spike",
        "description": "Authentication service response time went from 200ms to 8 seconds",
        "severity": "HIGH",
        "affected_system": "Auth Service",
        "duration": "30 minutes"
    },
    {
        "title": "Email Notification Failure",
        "description": "SMTP credentials expired causing all email notifications to fail silently",
        "severity": "LOW",
        "affected_system": "Notification Service",
        "duration": "6 hours"
    }
]

def load_prompt(filename: str, **kwargs) -> str:
    with open(f"prompts/{filename}", "r") as f:
        return f.read().format(**kwargs)

def test_all_inputs():
    print("=" * 60)
    print("Testing Describe Prompt with 5 Inputs")
    print("=" * 60)

    for i, input_data in enumerate(test_inputs, 1):
        print(f"\n--- Test {i}: {input_data['title']} ---")

        prompt = load_prompt(
            "describe.txt",
            generated_at=datetime.utcnow().isoformat(),
            **input_data
        )

        result = call_groq(prompt)

        if result:
            try:
                parsed = json.loads(result)
                print("✅ Valid JSON returned")
                print(json.dumps(parsed, indent=2))
            except json.JSONDecodeError:
                print("❌ Invalid JSON — refine the prompt")
                print(result)
        else:
            print("❌ Groq call failed — check API key")

if __name__ == "__main__":
    test_all_inputs()