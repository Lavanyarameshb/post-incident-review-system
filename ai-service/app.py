import re
from flask import Flask, request, jsonify
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address
from dotenv import load_dotenv
import os

load_dotenv()

app = Flask(__name__)

# Setting up rate limiter - max 30 requests per minute
limiter = Limiter(
    get_remote_address,
    app=app,
    default_limits=["30 per minute"]
)

# List of prompt injection patterns to detect
INJECTION_PATTERNS = [
    "ignore previous instructions",
    "ignore all instructions",
    "disregard previous",
    "forget previous instructions",
    "you are now",
    "act as",
    "jailbreak",
]

def strip_html(text: str) -> str:
    clean = re.compile('<.*?>')
    return re.sub(clean, '', text)

def detect_prompt_injection(text: str) -> bool:
    text_lower = text.lower()
    for pattern in INJECTION_PATTERNS:
        if pattern in text_lower:
            return True
    return False

@app.before_request
def sanitize_request():
    if request.is_json:
        data = request.get_json(silent=True)
        if data:
            for key in data:
                if isinstance(data[key], str):
                    data[key] = strip_html(data[key])
                    if detect_prompt_injection(data[key]):
                        return jsonify({"error": "Invalid input detected"}), 400

@app.after_request
def add_security_headers(response):
    response.headers['Content-Security-Policy'] = "default-src 'self'; script-src 'self'; style-src 'self'"
    response.headers['X-Content-Type-Options'] = 'nosniff'
    response.headers['X-Frame-Options'] = 'DENY'
    response.headers['Server'] = 'Unknown'
    return response

# Register all blueprints
from routes.health import health_bp
from routes.describe import describe_bp
from routes.recommend import recommend_bp
from routes.generate_report import generate_report_bp

app.register_blueprint(health_bp)
app.register_blueprint(describe_bp)
app.register_blueprint(recommend_bp)
app.register_blueprint(generate_report_bp)

@app.route("/")
def index():
    return {"service": "Tool-38 AI Service", "status": "running"}

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)