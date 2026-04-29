import re

def sanitise_input(data: dict) -> tuple[bool, str, dict]:
    """
    Returns (is_valid, error_message, cleaned_data)
    """
    cleaned = {}

    # Check required fields
    required_fields = ["title", "description", "severity", "affected_system", "duration"]
    for field in required_fields:
        if field not in data or not data[field]:
            return False, f"Missing required field: {field}", {}

    # Strip HTML tags and dangerous characters
    for key, value in data.items():
        if isinstance(value, str):
            # Remove HTML tags
            value = re.sub(r"<[^>]*>", "", value)
            # Remove prompt injection attempts
            value = re.sub(r"(ignore previous|forget all|you are now|act as)", 
                         "", value, flags=re.IGNORECASE)
            # Strip extra whitespace
            value = value.strip()
            cleaned[key] = value
        else:
            cleaned[key] = value

    # Validate severity value
    valid_severities = ["LOW", "MEDIUM", "HIGH", "CRITICAL"]
    if cleaned.get("severity", "").upper() not in valid_severities:
        return False, f"Severity must be one of: {valid_severities}", {}

    cleaned["severity"] = cleaned["severity"].upper()

    return True, "", cleaned