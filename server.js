require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const Razorpay = require('razorpay');
const twilio = require('twilio');
const multer = require('multer');
const path = require('path');
const cors = require('cors');

const app = express();

// --- MIDDLEWARE ---
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cors());
app.use(express.static('public')); // Serves your HTML file
app.use('/uploads', express.static('uploads')); // Makes uploaded resumes accessible

// --- CONFIGURATION ---

// 1. MongoDB Connection
mongoose.connect(process.env.MONGO_URI || 'mongodb://127.0.0.1:27017/fixipy_db')
    .then(() => console.log('✅ MongoDB Connected'))
    .catch(err => console.error('❌ MongoDB Error:', err));

// 2. Razorpay Instance
const razorpay = new Razorpay({
    key_id: process.env.RAZORPAY_KEY_ID,
    key_secret: process.env.RAZORPAY_KEY_SECRET
});

// 3. Twilio Client
const twilioClient = new twilio(process.env.TWILIO_SID, process.env.TWILIO_AUTH_TOKEN);

// 4. File Upload (Multer) Storage
const storage = multer.diskStorage({
    destination: './uploads/',
    filename: function(req, file, cb) {
        cb(null, file.fieldname + '-' + Date.now() + path.extname(file.originalname));
    }
});
const upload = multer({ 
    storage: storage,
    limits: { fileSize: 2000000 } // 2MB limit
});

// --- DATABASE SCHEMAS ---

const OrderSchema = new mongoose.Schema({
    customerName: String,
    phone: String,
    device: String,
    issue: String,
    address: String,
    location: { lat: String, long: String },
    amount: Number,
    orderId: String,
    paymentId: String,
    status: { type: String, default: 'Pending' },
    createdAt: { type: Date, default: Date.now }
});
const Order = mongoose.model('Order', OrderSchema);

const ApplicationSchema = new mongoose.Schema({
    name: String,
    email: String,
    phone: String,
    role: String,
    reason: String,
    resumePath: String,
    createdAt: { type: Date, default: Date.now }
});
const Application = mongoose.model('Application', ApplicationSchema);

const RepairmanSchema = new mongoose.Schema({
    name: String,
    phone: String,
    email: String,
    aadhar: String,
    experience: String,
    skills: [String],
    address: String
});
const Repairman = mongoose.model('Repairman', RepairmanSchema);

// --- ROUTES ---

// 1. CREATE ORDER (Razorpay)
app.post('/create-order', async (req, res) => {
    try {
        const options = {
            amount: 50000, // 500 INR in paise
            currency: "INR",
            receipt: "order_rcptid_" + Date.now()
        };
        const order = await razorpay.orders.create(options);
        res.json(order);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// 2. VERIFY PAYMENT & SEND WHATSAPP
app.post('/verify-payment', async (req, res) => {
    const { razorpay_order_id, razorpay_payment_id, razorpay_signature, booking_details } = req.body;

    const body = razorpay_order_id + "|" + razorpay_payment_id;
    const crypto = require("crypto");
    const expectedSignature = crypto.createHmac('sha256', process.env.RAZORPAY_KEY_SECRET)
                                    .update(body.toString())
                                    .digest('hex');

    if (expectedSignature === razorpay_signature) {
        // Save to DB
        const newOrder = new Order({
            ...booking_details,
            orderId: razorpay_order_id,
            paymentId: razorpay_payment_id,
            status: "Paid"
        });
        await newOrder.save();

        // Send WhatsApp
        try {
            await twilioClient.messages.create({
                from: process.env.TWILIO_WHATSAPP_FROM,
                to: process.env.ADMIN_WHATSAPP_NUMBER,
                body: `🚀 *New Fixipy Order!*\n👤 Name: ${booking_details.customerName}\n📱 Phone: ${booking_details.phone}\n🔧 Device: ${booking_details.device}\n💰 Status: PAID`
            });
            console.log("WhatsApp sent");
        } catch (e) {
            console.error("WhatsApp failed:", e);
        }

        res.json({ status: "success" });
    } else {
        res.status(400).json({ status: "failure" });
    }
});

// 3. JOIN TEAM (File Upload)
app.post('/join-team', upload.single('resume'), async (req, res) => {
    try {
        if (!req.file) {
            return res.status(400).json({ status: "error", message: "No file uploaded" });
        }
        
        const newApp = new Application({
            name: req.body.name,
            email: req.body.email,
            phone: req.body.phone,
            role: req.body.role,
            reason: req.body.reason,
            resumePath: req.file.path
        });
        await newApp.save();
        
        res.json({ status: "success", message: "Application received" });
    } catch (error) {
        res.status(500).json({ status: "error", message: error.message });
    }
});

// 4. REGISTER REPAIRMAN
app.post('/register-repairman', async (req, res) => {
    try {
        const repairman = new Repairman(req.body);
        await repairman.save();
        res.json({ status: "success", message: "Registered successfully" });
    } catch (error) {
        res.status(500).json({ status: "error", message: error.message });
    }
});

// Start Server
// const PORT = process.env.PORT || 3000;
// const PORT = process.env.PORT || 10000 ;
// app.listen(PORT, () => console.log(`🚀 Server running on http://localhost:${PORT}`));
const PORT = process.env.PORT || 3000;

app.listen(PORT, "0.0.0.0/0", () => {
  console.log(`🚀 Server running on port ${PORT}`);
});
