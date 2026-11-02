package com.example.model

data class CustomerReview(
  val id: String,
  val customerName: String,
  val location: String,
  val rating: Int,
  val date: String,
  val comment: String,
  val verifiedPurchase: Boolean = true,
  val avatarColorHex: Long = 0xFF1E56A0
)

data class Product(
  val id: String,
  val title: String,
  val subtitle: String,
  val category: String,
  val price: Int,
  val originalPrice: Int,
  val imageRes: Int,
  val badge: String,
  val rating: Float,
  val reviewCount: Int,
  val inStock: Boolean = true,
  val stockLeft: Int = 12,
  val description: String,
  val authenticityCode: String,
  val warrantyPeriod: String = "1 Year Brand Warranty",
  val returnPolicy: String = "7 Days Replacement Guarantee",
  val specs: Map<String, String>,
  val packageIncludes: List<String>,
  val highlights: List<String>,
  val verifiedReviews: List<CustomerReview>,
  val amazonProductUrl: String = StoreInfo.AMAZON_STORE_URL
) {
  val discountPercent: Int
    get() = if (originalPrice > price) {
      (((originalPrice - price).toDouble() / originalPrice) * 100).toInt()
    } else 0
}
