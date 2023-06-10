import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import tensorflow as tf
from tensorflow import keras
import numpy as np
import io
from PIL import Image
import base64
from google.cloud import storage

model = keras.models.load_model("Skinease_2_2.h5")

def predict_img(input_image):
    return prediction(model, input_image)

def prediction(model, image_pred):
    # Initialize the Cloud Storage client
    storage_client = storage.Client()
    bucket = storage_client.bucket('skinease-bucket')

    # Download the image from Cloud Storage
    blob = bucket.blob(image_pred)
    image_bytes = blob.download_as_bytes()

    img = Image.open(io.BytesIO(image_bytes)).convert('L')
    img = img.resize((300, 300))
    img = np.array(img) / 255
    img = np.expand_dims(img, 0)

    pred = model.predict(img)
    pred0 = pred[0]
    label0 = np.argmax(pred0)
    return label0

