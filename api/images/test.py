import requests

IMG_PATH = 'images/ISIC_6652978.jpg'
SERVER = 'http://localhost:5000/'
resp = requests.post(SERVER, files={'file': IMG_PATH})

print(resp.json())