from flask import Flask, request, jsonify, render_template
from utilities_old import predict_img
import json

app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict():
    data = request.json
    print(data)
    pred = predict_img(data.get('image'))
    return jsonify(pred)

    

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)