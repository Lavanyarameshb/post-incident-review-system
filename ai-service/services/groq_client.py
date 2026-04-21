import os
import time
import logging
import json
from groq import Groq
from dotenv import load_dotenv

# loading the api key from .env file
load_dotenv('../.env')

# setting up logging so i can see what's happening
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class GroqClient:
    def __init__(self):
        # connecting to groq using api key
        self.client = Groq(api_key=os.getenv("GROQ_API_KEY"))

    def call(self, prompt: str, max_retries: int = 3) -> dict:
        # trying up to 3 times in case groq fails
        for attempt in range(1, max_retries + 1):
            try:
                logger.info(f"Calling Groq API - Attempt {attempt}")
                response = self.client.chat.completions.create(
                    messages=[
                        {
                            "role": "user",
                            "content": prompt
                        }
                    ],
                    model="llama-3.3-70b-versatile",
                    temperature=0.3,
                    max_tokens=1000
                )
                content = response.choices[0].message.content
                # trying to parse as json, if not just return as text
                try:
                    parsed_data = json.loads(content)
                except:
                    parsed_data = content
                logger.info("Groq API call successful!")
                return {
                    "success": True,
                    "data": parsed_data
                }
            except Exception as e:
                # something went wrong, log it
                logger.error(f"Attempt {attempt} failed: {str(e)}")
                if attempt < max_retries:
                    # waiting before retrying
                    wait_time = 2 ** attempt
                    logger.info(f"Retrying in {wait_time} seconds...")
                    time.sleep(wait_time)
                else:
                    # all 3 attempts failed, returning fallback
                    logger.error("All attempts failed!")
                    return {
                        "success": False,
                        "error": str(e),
                        "is_fallback": True
                    }