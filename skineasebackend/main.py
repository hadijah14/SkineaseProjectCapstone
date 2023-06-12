import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import io
import tensorflow as tf
import numpy as np
import base64
from PIL import Image
from google.cloud import storage
from flask import Flask, request, jsonify

MODEL_PATH = 'models/Skinease_6.h5'
BUCKET_NAME = 'skineasebucket'

# Load the ML model
model = tf.keras.models.load_model(MODEL_PATH)

# Preprocess the image
def preprocess_image(image):
    image = image.resize((224, 224))
    image = np.array(image) / 255
    image = np.expand_dims(image, 0)
    return image

# Save image to Cloud Storage
def save_image_to_cloud_storage(image, filename):
    # Initialize the Cloud Storage client
    storage_client = storage.Client()
    bucket = storage_client.bucket(BUCKET_NAME)
    blob = bucket.blob(filename)
    blob.upload_from_string(image)

    # Get the public URL of the uploaded image
    image_url = f"https://storage.googleapis.com/{BUCKET_NAME}/{blob.name}"
    return image_url

# Initialize Flask server with error handling
app = Flask(__name__)

@app.route("/", methods=["GET","POST"])
def index():
    if request.method == "POST":
        file = request.files.get('file')
        if file is None or file.filename == "":
            return jsonify({'error': 'No image'})

        try:
            # Read the image file
            image_bytes = file.read()
            image = Image.open(io.BytesIO(image_bytes))

            # Preprocess the image
            preprocessed_image = preprocess_image(image)

            # Make prediction
            predicted_class = model.predict(preprocessed_image)
            predicted_class = np.argmax(predicted_class)

            # Save the image to Cloud Storage
            save_image_to_cloud_storage(image_bytes, f"image_{predicted_class}_{file}.jpg")

            # Prepare the response
            response = {
                'prediction': int(predicted_class),
            }
            return jsonify(response)
        except Exception as e:
            return jsonify({'error': str(e)})

    return "OK Skinease"

    
if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=8080)