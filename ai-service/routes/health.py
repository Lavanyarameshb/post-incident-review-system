from flask import Blueprint

health_bp = Blueprint("health", __name__)

@health_bp.route("/health", methods=["GET"])
def health():
    return {
        "status": "ok",
        "service": "Tool-38 AI Service",
        "port": 5000
    }, 200