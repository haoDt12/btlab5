const express = require('express');
const mongoose = require('mongoose');

const ProductModel = require('./model/ProductModel');
const uri = "mongodb://127.0.0.1:27017/api";
const bodyParser = require('body-parser');
const moment = require('moment');
const { id } = require('date-fns/locale');

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
// array for JSON response

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.get('/listProduct', async function (req, res) {

    try {
          const productList = await ProductModel.find().lean();
          console.log('Da ket noi voi MongoDB');
          const formattedProductList = productList.map((product) => ({
          id: product._id,
          name: product.name,
          price: parseFloat(product.price).toFixed(2),
          description: product.description,
          created_at: moment(product.created_at).format('YYYY-MM-DD HH:mm:ss'),
          updated_at: product.updated_at ? moment(product.updated_at).format('YYYY-MM-DD HH:mm:ss') : '0000-00-00 00:00:00',
        }));
        console.log('Product List:', formattedProductList)
       res.json({ success: 1, product: formattedProductList });
      
      } catch (err) {
        console.error('Error fetching products:', err);
        res.status(500).json({ success: 0, error: 'Internal server error' });
      }
})

// Định nghĩa API thêm sản phẩm
app.post('/addProducts', async (req, res) => {
  try {
    const { name, price, description } = req.body;
    const product = new ProductModel({
      name,
      price: Number(price), // Convert price to a floating-point number
      description,
    });

    await product.save();
  
    // Format the newly added product to match the listProduct response format
    const newProduct = {
      //id: product._id,
      name: product.name,
      price: parseFloat(product.price).toFixed(2),
      description: product.description,
      created_at: moment(product.created_at).format('YYYY-MM-DD HH:mm:ss'),
      updated_at: product.updated_at ? moment(product.updated_at).format('YYYY-MM-DD HH:mm:ss') : '0000-00-00 00:00:00',
    };

    res.json({ success: 1, product: newProduct,message: "Product successfully created." });
  } catch (err) {
    console.error('Error adding product:', err);
    res.status(500).json({ success: 0, message: '"Oops! An error occurred.' });
  }
});

// Định nghĩa API sửa đổi sản phẩm
app.put('/products/:productId', async (req, res) => {
  try {
    const { productId } = req.params;
    const { name, price, description} = req.body;
    const product = await ProductModel.findByIdAndUpdate(
      productId,
      { name, price, description },
      { new: true }
    );
    const productList = await ProductModel.find().lean();
    if (!product) {
      return res.status(404).json({ error: 'Product not found' });
    }

  
    res.json({ success: 1, product:productList,message: 'Product successfully update' });

  } catch (err) {
    console.error('Error updating product:', err);
    res.status(500).json({success:0,message:"No product found"});
  }
});

// Định nghĩa API xóa sản phẩm
app.delete('/products/:productId', async (req, res) => {
  try {
    const { productId } = req.params;
    await ProductModel.findByIdAndRemove(productId);
    const productList = await ProductModel.find().lean();
    res.json({success:1,productList,message:"Product successfully deleted"});
  } catch (err) {
    console.error('Error deleting product:', err);
    res.status(500).json({success:0,message:"No product found"});
  }
});

  
  // Khởi động server
  const port = 3000;
  app.listen(port, () => {
    console.log(`Server started on port ${port}`);
  });