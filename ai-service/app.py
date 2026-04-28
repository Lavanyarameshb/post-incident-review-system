from flask import Flask
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address
from flask_cors import CORS
from dotenv import load_dotenv
import os

load_dotenv()

app = Flask(__name__)
CORS(app)

limiter = Limiter(
    get_remote_address,
    app=app,
    default_limits=["30 per minute"]
)

# Register blueprints
from routes.health import health_bp
from routes.describe import describe_bp
from routes.recommend import recommend_bp

app.register_blueprint(health_bp)
app.register_blueprint(describe_bp)
app.register_blueprint(recommend_bp)

@app.route("/")
def index():
    return {"service": "Tool-38 AI Service", "status": "running"}

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
