const mongoose = require('mongoose');
const ProductSchema = new mongoose.Schema({
    name: {
        type: String,
        require: true
    },
    price: {
        type: Number,
        require: true
    },
    description: {
        type: String,
        require: true
    },
        created_at: { 
            type: Date, default: Date.now 
        },
    updated_at: { 
        type: Date, default: null
     },
})

const ProductModel = new mongoose.model('products',ProductSchema);
module.exports = ProductModel;