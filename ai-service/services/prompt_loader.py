import os

def load_prompt(filename: str, **kwargs) -> str:
    """
    Loads a prompt template from the prompts/ folder
    and fills in the placeholders.
    """
    prompt_path = os.path.join("prompts", filename)

    try:
        with open(prompt_path, "r") as f:
            template = f.read()
        return template.format(**kwargs)
    except FileNotFoundError:
        raise Exception(f"Prompt file not found: {filename}")
    except KeyError as e:
        raise Exception(f"Missing placeholder in prompt: {e}")