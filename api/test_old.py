import tensorflow as tf
import os
import base64
import io
from utilities_old import predict_img
from PIL import Image
Model_path = 'models/Skinease_2_2.h5'

IMG_PATH = os.path.join(os.getcwd(),'images','ISIC_6653225.jpg')
encoded_img =''
with open(IMG_PATH, "rb") as image2string:
    encoded_img = base64.b64encode(image2string.read())
#print(encoded_img)
#print(type(encoded_img))

#model = tf.keras.models.load_model(Model_path)

#prediction= model.predict(encoded_img)
prediction = predict_img(encoded_img)
print(prediction)