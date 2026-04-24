import re
from flask import Flask, request, jsonify
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address

app = Flask(__name__)

# setting up rate limiter - max 30 requests per minute
limiter = Limiter(
    get_remote_address,
    app=app,
    default_limits=["30 per minute"]
)

# list of prompt injection patterns to detect
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
    # removing html tags from input
    clean = re.compile('<.*?>')
    return re.sub(clean, '', text)

def detect_prompt_injection(text: str) -> bool:
    # checking if input contains prompt injection patterns
    text_lower = text.lower()
    for pattern in INJECTION_PATTERNS:
        if pattern in text_lower:
            return True
    return False

# 🔹 Global sanitization (applies to all requests)
@app.before_request
def sanitize_request():
    if request.is_json:
        data = request.get_json(silent=True)
        if data:
            for key in data:
                if isinstance(data[key], str):
                    # strip html
                    data[key] = strip_html(data[key])
                    # check for injection
                    if detect_prompt_injection(data[key]):
                        return jsonify({"error": "Invalid input detected"}), 400

@app.route('/health', methods=['GET'])
def health():
    return jsonify({"status": "ok"})


# 🔹 Example endpoint (for Day 3 validation)
@app.route('/describe', methods=['POST'])
@limiter.limit("30 per minute")
def describe():
    data = request.get_json(silent=True, force=True)

    if not data or 'text' not in data:
        return jsonify({"error": "No input provided"}), 400

    if data['text'].strip() == "":
        return jsonify({"error": "Input cannot be empty"}), 400

    user_input = data['text']

    return jsonify({"message": "Input is safe", "cleaned_text": user_input}), 200


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000, debug=True)