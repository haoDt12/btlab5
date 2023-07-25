const express = require('express');
const mongoose = require('mongoose');

const ProductModel = require('./model/ProductModel');
const uri = "mongodb://127.0.0.1:27017/api";
const bodyParser = require('body-parser');


mongoose.connect(uri,{
    useNewUrlParser: true,
    useUnifiedTopology: true,
}) 
.then(() => {
    console.log('Da ket noi voi MongoDB');
})
.catch((err) => {
    console.error('Khong ket noi dc MongoDB: ', err);
})

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.get('/listProduct', async function (req, res) {

    try {
        const productList = await ProductModel.find().lean();
        console.log('Da ket noi voi MongoDB');
        res.json(productList);
      } catch (err) {
        console.error('Error fetching products:', err);
        res.status(500).json({ error: 'Internal server error' });
      }
})

// Định nghĩa API thêm sản phẩm
app.post('/addProducts', async (req, res) => {
  try {
    const { name, price, description } = req.body;
    const product = new ProductModel({ name, price, description });
    await product.save();
    const productList = await ProductModel.find().lean();
    res.json(productList);
  } catch (err) {
    console.error('Error adding product:', err);
    res.status(500).json({ error: 'Internal server error' });
  }
});

// Định nghĩa API sửa đổi sản phẩm
app.put('/products/:productId', async (req, res) => {
  try {
    const { productId } = req.params;
    const { name, price, description } = req.body;
    const product = await ProductModel.findByIdAndUpdate(
      productId,
      { name, price, description },
      { new: true }
    );
    const productList = await ProductModel.find().lean();
    res.json(productList);
  } catch (err) {
    console.error('Error updating product:', err);
    res.status(500).json({ error: 'Internal server error' });
  }
});

// Định nghĩa API xóa sản phẩm
app.delete('/products/:productId', async (req, res) => {
  try {
    const { productId } = req.params;
    await ProductModel.findByIdAndRemove(productId);
    const productList = await ProductModel.find().lean();
    res.json(productList);
  } catch (err) {
    console.error('Error deleting product:', err);
    res.status(500).json({ error: 'Internal server error' });
  }
});

  
  // Khởi động server
  const port = 8000;
  app.listen(port, () => {
    console.log(`Server started on port ${port}`);
  });