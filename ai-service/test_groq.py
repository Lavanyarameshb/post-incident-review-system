import os
from groq import Groq
from dotenv import load_dotenv

# Load the .env file
load_dotenv('../.env')

# Get the API key from .env
api_key = os.getenv("GROQ_API_KEY")

# Create Groq client
client = Groq(api_key=api_key)

# Test API call
chat_completion = client.chat.completions.create(
    messages=[
        {
            "role": "user",
            "content": "Say hello and confirm you are working!"
        }
    ],
    model="llama-3.3-70b-versatile",
)

# Print the response
print("✅ Groq API is working!")
print("Response:", chat_completion.choices[0].message.content)