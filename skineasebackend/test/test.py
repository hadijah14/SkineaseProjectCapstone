import requests

IMG_PATH = 'pred_6.jpg'
SERVER = 'https://skineasepredict-ise5ka2eaq-et.a.run.app'

with open(IMG_PATH, 'rb') as file:
    resp = requests.post(SERVER, files={'file': file})

print(resp.json())