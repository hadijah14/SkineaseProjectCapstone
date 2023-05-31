require('@google-cloud/debug-agent').start()

const express = require('express');
const multer = require('multer');
const { Storage } = require('@google-cloud/storage');
const tf = require('@tensorflow/tfjs-node');
const { createCanvas, loadImage } = require('canvas');
const path = require('path');
const pathKey = path.resolve('./serviceaccountkey.json')

// Setting Google Cloud Storage
const gcs = new Storage({
  projectId: 'skinease-project',
  keyFilename: pathKey,
});
const bucketName = 'skinease-bucket';

function getPublicUrl(filename) {
  return 'https://storage.googleapis.com/' + bucketName + '/' + filename;
}

// Masukin model
const modelPath = './Skinease_2_0.h5';

// Jenis penyakit kulit
const classes = [];

app.post('/predict', upload.single('image'), async (req, res) => {
  try {
    const model = await tf.loadLayersModel(`file://${modelPath}`);

    // Get image dari request
    const imageFile = req.file;

    // Upload image ke Google Cloud Storage
    const bucket = gcs.bucket(bucketName);
    const fileName = `${Date.now()}_${imageFile.originalname}`;
    const file = bucket.file(fileName);
    await file.save(imageFile.buffer);
    const imageUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`;

    // Preprocess image
    const img = await loadImage(imageFile.buffer);
    const canvas = createCanvas(224, 224);
    const ctx = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0, 224, 224);
    const imageData = ctx.getImageData(0, 0, 224, 224);
    const input = tensor3d(imageData.data, [224, 224, 4]);
    const preprocessedInput = input.expandDims(0).div(255);

    // Model prediction
    const predictions = model.predict(preprocessedInput);
    const predictedClassIndex = predictions.argMax(1).dataSync()[0];
    const predictedClass = classes[predictedClassIndex];

    // Response
    const response = { predictedClass, imageUrl };
    res.json(response);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'An error occurred' });
  }
});

app.listen(5000, () => {
  console.log('Server running on port 5000');
});