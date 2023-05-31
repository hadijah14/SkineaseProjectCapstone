import tensorflow as tf
import numpy as np

from PIL import Image
from tensorflow.keras.preprocessing.image import load_img
#from keras.preprocessing.image import load_img
from tensorflow.keras.preprocessing.image import img_to_array


import io
import base64
import os

Model_path = 'models/Skinease_2_2.h5'

model = tf.keras.models.load_model(Model_path)

def predict_img(input_image):
    return prediction(model, input_image)

def prediction(model, image_pred):
    #img = load_img(image_pred, target_size=(300,300))
    #img = img_to_array(img)
    #img = np.expand_dims(img, 0)

    img = Image.open(io.BytesIO(base64.b64decode(image_pred)))
    img = img.resize((300, 300), Image.ANTIALIAS)
    img = np.array(img) / 255
    img = np.expand_dims(img, 0)

    pred = model.predict(img)

    pred0 = pred[0]
    label0 = np.argmax(pred0)
    return label0

#if __name__=="__main__":
    #input_image = tf.keras.utils.image_dataset_from_directory('D:\Document\Kuliah\BANGKIT 2023\Capstone\api\images\ISIC_6652978.jpg')
    #input_image = Image.open('D:\Document\Kuliah\BANGKIT 2023\Capstone\\api\images\ISIC_6652978.jpg')


    #predict_img(input_image)
    #print(predict_img)
