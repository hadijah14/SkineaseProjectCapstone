import tensorflow as tf
import numpy as np
import io
import base64
from PIL import Image

Model_path = 'models/Skinease_2_2.h5'
model = tf.keras.models.load_model(Model_path)


def predict_img(input_image):
    return prediction(model, input_image)

def prediction(model, image_pred):

    encoded_img =''
    with open(image_pred, "rb") as image2string:
        encoded_img = base64.b64encode(image2string.read())

    img = Image.open(io.BytesIO(base64.b64decode(encoded_img)))
    img = img.resize((300, 300), Image.ANTIALIAS)
    img = np.array(img) / 255
    img = np.expand_dims(img, 0)

    pred = model.predict(img)

    pred0 = pred[0]
    label0 = np.argmax(pred0)
    return label0
