from flask import Flask, request, jsonify
from utilities import predict_img


app = Flask(__name__)

@app.route("/", methods=["GET", "POST"])
def img_prediction():
    if request.method == "POST":
        file = request.files.get('file')
        if file is None or file.filename == "":
            return jsonify({"error": "no file"})

        try:
            image_file = file.read()

            prediction = predict_img(image_file)

            result = {"prediction": int(prediction)}
            return jsonify(result)
        
        except Exception as e:
            return jsonify({"error": str(e)})

    return "OK"


if __name__ == "__main__":
    app.run(debug=True)