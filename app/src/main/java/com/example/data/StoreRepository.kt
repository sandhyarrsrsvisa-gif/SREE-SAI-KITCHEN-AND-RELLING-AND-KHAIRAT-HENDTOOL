package com.example.data

import com.example.R
import com.example.model.CustomerReview
import com.example.model.Product

data class StoreFaq(
  val question: String,
  val answer: String
)

object StoreRepository {

  val reviews = listOf(
    CustomerReview(
      id = "rev-1",
      customerName = "Vikram Aditya",
      location = "Mumbai, MH",
      rating = 5,
      date = "Yesterday",
      comment = "Ordered the Apex Titanium Watch directly after talking with Ranjeet ji on WhatsApp. Got it in 2 days, 100% genuine sealed box with invoice! Super trusted seller.",
      avatarColorHex = 0xFF1E56A0
    ),
    CustomerReview(
      id = "rev-2",
      customerName = "Pooja Sharma",
      location = "Delhi NCR",
      rating = 5,
      date = "3 days ago",
      comment = "I checked their Amazon link and ordered earbuds. Outstanding sound quality and ANC. The seller Ranjeet Sharma is very responsive on call (8237225524).",
      avatarColorHex = 0xFF10B981
    ),
    CustomerReview(
      id = "rev-3",
      customerName = "Rahul Deshmukh",
      location = "Pune, MH",
      rating = 5,
      date = "1 week ago",
      comment = "Visited the shop in person using Google Maps location link! Genuine products, transparent pricing, and excellent behavior. Highly recommend!",
      avatarColorHex = 0xFFD4AF37
    ),
    CustomerReview(
      id = "rev-4",
      customerName = "Amit Patel",
      location = "Ahmedabad, GJ",
      rating = 5,
      date = "2 weeks ago",
      comment = "The leather bag is premium quality! Pure leather with brass fittings. You can trust Ranjeet Sharma blindly. Real deal.",
      avatarColorHex = 0xFFE11D48
    )
  )

  val faqs = listOf(
    StoreFaq(
      question = "How do I know the products are 100% original?",
      answer = "Every single product sold by Ranjeet Sharma is sourced directly from certified brand distributors in factory sealed packaging with genuine serial numbers, GST invoices, and valid manufacturer warranty."
    ),
    StoreFaq(
      question = "Can I order via Amazon or directly from the store?",
      answer = "Yes! You can order either via our verified Amazon storefront (prime delivery supported) or directly contact Ranjeet Sharma at 8237225524 / WhatsApp for exclusive direct-store pricing and fast dispatch."
    ),
    StoreFaq(
      question = "What if I face any issue with my order?",
      answer = "We offer a hassle-free 7-day replacement guarantee on defective items, plus brand warranty support. You can call Ranjeet ji directly anytime at 8237225524."
    ),
    StoreFaq(
      question = "Can I visit your physical shop in person?",
      answer = "Yes! Click the 'Visit Store' button at the top to open our exact Google Maps shop location. We welcome customers daily from 10:00 AM to 9:30 PM."
    )
  )

  val products = listOf(
    Product(
      id = "prod-1",
      title = "Apex Titanium Pro Smartwatch",
      subtitle = "1.43\" Ultra AMOLED • Sapphire Glass • BT Calling • 14-Day Battery",
      category = "Smart Tech",
      price = 3499,
      originalPrice = 7999,
      imageRes = R.drawable.img_smartwatch,
      badge = "Amazon Verified Best Seller",
      rating = 4.9f,
      reviewCount = 428,
      inStock = true,
      stockLeft = 7,
      description = "The Apex Titanium Pro combines military-grade durability with executive elegance. Featuring a vibrant always-on AMOLED display shielded by genuine sapphire crystal, precision dual-frequency GPS, 24/7 heart & SpO2 health tracking, and ultra-clear Bluetooth phone calls.",
      authenticityCode = "RS-APX-8237-ORIG",
      warrantyPeriod = "1 Year Brand Replacement Warranty",
      returnPolicy = "7 Days Easy Replacement Guarantee",
      specs = mapOf(
        "Display" to "1.43\" AMOLED 466x466 (1000 Nits)",
        "Glass" to "Scratch-proof Sapphire Crystal",
        "Body" to "Aerospace-Grade Titanium Alloy",
        "Battery Life" to "Up to 14 Days (450 mAh)",
        "Water Resistance" to "IP68 & 5ATM Swimming Ready",
        "Calling" to "Bluetooth 5.3 with ENC Mic",
        "Sensors" to "Optical Heart, SpO2, Sleep, Pedometer"
      ),
      packageIncludes = listOf(
        "1x Apex Titanium Pro Smartwatch",
        "1x Premium Titanium Mesh Strap",
        "1x Extra Silicone Sports Band",
        "1x Magnetic Fast Charger Cable",
        "1x Warranty Card & User Manual",
        "1x RS Store Authenticity Seal"
      ),
      highlights = listOf(
        "100% Genuine Sealed Stock guaranteed by Ranjeet Sharma",
        "Direct Amazon Verified Seller fulfillment",
        "Crisp dual-mic ENC for hands-free calling",
        "100+ Sports & Workout modes"
      ),
      verifiedReviews = listOf(reviews[0], reviews[2])
    ),
    Product(
      id = "prod-2",
      title = "AeroWave Elite ANC Wireless Earbuds",
      subtitle = "42dB Hybrid Active Noise Cancellation • 38H Playtime • Spatial Audio",
      category = "Audio",
      price = 2199,
      originalPrice = 4999,
      imageRes = R.drawable.img_earbuds,
      badge = "High Customer Trust ⭐ 4.9",
      rating = 4.9f,
      reviewCount = 389,
      inStock = true,
      stockLeft = 14,
      description = "Immerse yourself in studio-grade audio with AeroWave Elite. Powered by 11mm titanium dynamic drivers and hybrid 42dB Active Noise Cancellation that blocks out commute rumble and chatter. Features quad-microphone beamforming for crystal clear work calls and low-latency gaming mode.",
      authenticityCode = "RS-AERO-4758-CERT",
      warrantyPeriod = "1 Year Complete Warranty",
      returnPolicy = "7 Days Replacement Guarantee",
      specs = mapOf(
        "Driver" to "11mm Titanium Diaphragm Drivers",
        "ANC Depth" to "Up to -42dB Hybrid Noise Reduction",
        "Battery Total" to "38 Hours with USB-C Fast Charge",
        "Earbud Battery" to "8 Hours on single charge",
        "Latency" to "45ms Ultra Low Latency Mode",
        "Bluetooth" to "v5.3 with AAC / SBC Codecs",
        "Water Rating" to "IPX5 Sweat & Splash Resistant"
      ),
      packageIncludes = listOf(
        "1x Pair AeroWave Elite Earbuds",
        "1x Matte Black Fast-Charging Case",
        "3x Ergonomic Silicone Ear-tip sizes (S, M, L)",
        "1x Braided USB-C Charging Cable",
        "1x RS Store Certificate of Authenticity"
      ),
      highlights = listOf(
        "Original factory sealed retail packaging",
        "Dual device seamless smart pairing",
        "Instant touch controls with custom EQ presets",
        "Tested & verified by RS Store team"
      ),
      verifiedReviews = listOf(reviews[1], reviews[3])
    ),
    Product(
      id = "prod-3",
      title = "Heritage Full-Grain Leather Travel Pack",
      subtitle = "Handcrafted Genuine Buffalo Leather • 15.6\" Laptop Sleeve • Antique Brass",
      category = "Leather & Gear",
      price = 4299,
      originalPrice = 8999,
      imageRes = R.drawable.img_leather_bag,
      badge = "Handcrafted Pure Leather",
      rating = 5.0f,
      reviewCount = 215,
      inStock = true,
      stockLeft = 5,
      description = "Crafted for connoisseurs, this Heritage Travel Pack is made from 100% vegetable-tanned full-grain leather that ages with a rich patina over time. Equipped with padded 15.6-inch laptop compartment, heavy-duty antique brass zippers, water-resistant canvas lining, and ergonomic padded shoulder straps.",
      authenticityCode = "RS-LTHR-9182-ORIG",
      warrantyPeriod = "2 Years Craftsmanship Guarantee",
      returnPolicy = "7 Days Hassle-Free Returns",
      specs = mapOf(
        "Material" to "100% Genuine Full-Grain Buffalo Leather",
        "Hardware" to "Solid Antique Brass Buckles & YKK Zippers",
        "Laptop Fit" to "Dedicated Padded Sleeve up to 15.6\"",
        "Capacity" to "26 Liters with Multiple Compartments",
        "Lining" to "Heavy-Duty Water-Repellent Cotton Canvas",
        "Dimensions" to "42 cm x 31 cm x 16 cm",
        "Weight" to "1.25 kg"
      ),
      packageIncludes = listOf(
        "1x Handcrafted Full-Grain Leather Backpack",
        "1x Breathable Dust Storage Bag",
        "1x Leather Care & Conditioning Guide",
        "1x RS Official Store Authenticity Stamp"
      ),
      highlights = listOf(
        "Certified pure natural leather with aroma guarantee",
        "Reinforced stress points with bar-tack stitching",
        "Ergonomic breathable back panel support",
        "Direct physical store guarantee in India"
      ),
      verifiedReviews = listOf(reviews[2], reviews[3])
    ),
    Product(
      id = "prod-4",
      title = "RS Showroom Flagship Electronics Hub",
      subtitle = "Browse all high-performance gadgets, smart tech & exclusive store accessories",
      category = "All Store",
      price = 1999,
      originalPrice = 3999,
      imageRes = R.drawable.img_store_hero,
      badge = "Verified Showroom Collection",
      rating = 4.9f,
      reviewCount = 560,
      inStock = true,
      stockLeft = 20,
      description = "Welcome to the full RS Showroom inventory curated by Ranjeet Sharma. We provide top-of-the-line certified consumer electronics, smart accessories, fast chargers, power banks, and premium audio equipment at unbeatable wholesale and retail prices.",
      authenticityCode = "RS-STORE-8237-HUB",
      warrantyPeriod = "Brand Authorized Warranty",
      returnPolicy = "7 Days Replacement on all items",
      specs = mapOf(
        "Store Owner" to "Ranjeet Sharma",
        "Contact" to "8237225524",
        "Instagram" to "@ranjeetsharma4758",
        "Amazon Status" to "Active Verified Seller",
        "Dispatch Time" to "Within 24 Hours",
        "Packaging" to "3-Layer Tamper-Proof Bubble Packing"
      ),
      packageIncludes = listOf(
        "1x Certified Original Product",
        "1x Official Tax Invoice with GST",
        "1x Direct WhatsApp Support Access Card"
      ),
      highlights = listOf(
        "Inspect in person at our physical shop or order online",
        "Direct owner assistance for any questions",
        "Fast pan-India delivery with real-time tracking"
      ),
      verifiedReviews = listOf(reviews[0], reviews[1])
    )
  )

  fun getCategories(): List<String> = listOf("All", "Smart Tech", "Audio", "Leather & Gear", "All Store")
}
