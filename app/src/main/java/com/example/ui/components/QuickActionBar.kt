package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.StoreInfo
import com.example.ui.theme.AmazonOrange
import com.example.ui.theme.GoogleMapsRed
import com.example.ui.theme.InstagramGradientEnd
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentUtils

@Composable
fun QuickActionBar(
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .testTag("quick_action_bar"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Direct Contact & Verified Links",
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontWeight = FontWeight.SemiBold
        )
        Text(
          text = "Tap to Connect",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.Medium
        )
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp)
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // 1. Call Now
        QuickActionButton(
          label = "Call Now",
          sublabel = StoreInfo.PHONE_NUMBER,
          icon = Icons.Default.Call,
          color = NavyDeep,
          testTag = "action_call_button",
          onClick = { IntentUtils.dialPhoneNumber(context, StoreInfo.PHONE_NUMBER) }
        )

        // 2. WhatsApp
        QuickActionButton(
          label = "WhatsApp",
          sublabel = "Instant Reply",
          icon = Icons.Default.Send,
          color = WhatsAppGreen,
          testTag = "action_whatsapp_button",
          onClick = {
            IntentUtils.openWhatsApp(
              context,
              StoreInfo.WHATSAPP_NUMBER,
              "Hello Ranjeet ji, I have an inquiry about products on your RS Store!"
            )
          }
        )

        // 3. Amazon Store
        QuickActionButton(
          label = "Amazon",
          sublabel = "Verified Store",
          icon = Icons.Default.ShoppingCart,
          color = AmazonOrange,
          testTag = "action_amazon_button",
          onClick = { IntentUtils.openAmazonStore(context, StoreInfo.AMAZON_STORE_URL) }
        )

        // 4. Instagram
        QuickActionButton(
          label = "Instagram",
          sublabel = StoreInfo.INSTAGRAM_HANDLE,
          icon = Icons.Default.PhotoCamera,
          color = InstagramGradientEnd,
          testTag = "action_instagram_button",
          onClick = { IntentUtils.openInstagram(context, StoreInfo.INSTAGRAM_HANDLE) }
        )

        // 5. Shop Address / Maps
        QuickActionButton(
          label = "Shop Map",
          sublabel = "Visit Us",
          icon = Icons.Default.LocationOn,
          color = GoogleMapsRed,
          testTag = "action_maps_button",
          onClick = { IntentUtils.openShopLocation(context, StoreInfo.SHOP_MAPS_URL) }
        )
      }
    }
  }
}

@Composable
private fun QuickActionButton(
  label: String,
  sublabel: String,
  icon: ImageVector,
  color: Color,
  testTag: String,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .clip(RoundedCornerShape(12.dp))
      .background(color.copy(alpha = 0.1f))
      .clickable(onClick = onClick)
      .padding(horizontal = 12.dp, vertical = 8.dp)
      .testTag(testTag),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Column(
      modifier = Modifier
        .size(36.dp)
        .clip(CircleShape)
        .background(color),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint = Color.White,
        modifier = Modifier.size(18.dp)
      )
    }
    Column {
      Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Text(
        text = sublabel,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}
