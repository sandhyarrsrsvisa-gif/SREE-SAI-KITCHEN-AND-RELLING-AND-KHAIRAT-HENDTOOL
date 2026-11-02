package com.example.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.model.Product
import com.example.model.StoreInfo
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object IntentUtils {

  fun dialPhoneNumber(context: Context, phoneNumber: String = StoreInfo.PHONE_NUMBER) {
    try {
      val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
      }
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "Cannot open dialer. Phone: $phoneNumber", Toast.LENGTH_LONG).show()
    }
  }

  fun openWhatsApp(
    context: Context,
    phone: String = StoreInfo.WHATSAPP_NUMBER,
    message: String = "Hello Ranjeet ji, I saw your product on RS Store and would like to buy!"
  ) {
    try {
      val encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
      val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=$encodedMessage")
      val intent = Intent(Intent.ACTION_VIEW, uri)
      context.startActivity(intent)
    } catch (e: Exception) {
      // Fallback to web link
      try {
        val webUri = Uri.parse("https://wa.me/$phone")
        context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
      } catch (ex: Exception) {
        Toast.makeText(context, "WhatsApp not installed. Call $phone", Toast.LENGTH_LONG).show()
      }
    }
  }

  fun openInstagram(context: Context, handle: String = "ranjeetsharma4758") {
    val cleanHandle = handle.removePrefix("@")
    try {
      val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/$cleanHandle")).apply {
        setPackage("com.instagram.android")
      }
      context.startActivity(appIntent)
    } catch (e: Exception) {
      // Fallback to browser
      try {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/$cleanHandle"))
        context.startActivity(webIntent)
      } catch (ex: Exception) {
        Toast.makeText(context, "Could not open Instagram profile", Toast.LENGTH_SHORT).show()
      }
    }
  }

  fun openAmazonStore(context: Context, url: String = StoreInfo.AMAZON_STORE_URL) {
    try {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "Could not open Amazon link", Toast.LENGTH_SHORT).show()
    }
  }

  fun openShopLocation(context: Context, locationUrl: String = StoreInfo.SHOP_MAPS_URL) {
    try {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(locationUrl))
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "Could not open Maps link", Toast.LENGTH_SHORT).show()
    }
  }

  fun shareProduct(context: Context, product: Product) {
    try {
      val text = """
        Check out ${product.title} from ${StoreInfo.OWNER_NAME}'s Store!
        Price: ₹${product.price} (MRP: ₹${product.originalPrice})
        
        Authenticity Guaranteed • 100% Original
        Contact: ${StoreInfo.FORMATTED_PHONE}
        Amazon Store: ${StoreInfo.AMAZON_STORE_URL}
      """.trimIndent()
      val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
      }
      val shareIntent = Intent.createChooser(sendIntent, "Share ${product.title}")
      context.startActivity(shareIntent)
    } catch (e: Exception) {
      Toast.makeText(context, "Unable to share", Toast.LENGTH_SHORT).show()
    }
  }

  fun copyToClipboard(context: Context, text: String, label: String = "Store Info") {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard?.setPrimaryClip(clip)
    Toast.makeText(context, "$label copied to clipboard!", Toast.LENGTH_SHORT).show()
  }
}
