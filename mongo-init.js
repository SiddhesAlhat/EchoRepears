// MongoDB initialization script
db = db.getSiblingDB('fixipy_db');

// Create collections with validation
db.createCollection('orders');
db.createCollection('applications');
db.createCollection('repairmen');

// Create indexes for better performance
db.orders.createIndex({ "orderId": 1 }, { unique: true });
db.orders.createIndex({ "createdAt": -1 });
db.applications.createIndex({ "email": 1 });
db.repairmen.createIndex({ "phone": 1 });

// Insert sample data (optional)
db.orders.insertOne({
    customerName: "Test Customer",
    phone: "+919876543210",
    device: "Test Device",
    issue: "Test Issue",
    address: "Test Address",
    location: { lat: "19.0760", long: "72.8777" },
    amount: 500.00,
    orderId: "test_order_001",
    paymentId: "test_payment_001",
    status: "Pending",
    createdAt: new Date()
});

print("MongoDB initialized successfully for Fixipy!");
