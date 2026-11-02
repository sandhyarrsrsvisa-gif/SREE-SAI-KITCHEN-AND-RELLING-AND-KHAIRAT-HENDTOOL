package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.GoogleMapsRed
import com.example.ui.theme.InstagramGradientEnd
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectContactDialog(
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.colorScheme.surface,
    dragHandle = null,
    modifier = Modifier.testTag("direct_contact_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Contact ${StoreInfo.OWNER_NAME}",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
              imageVector = Icons.Default.Verified,
              contentDescription = "Verified",
              tint = EmeraldTrust,
              modifier = Modifier.size(20.dp)
            )
          }
          Text(
            text = "Official Store Owner & Fulfillment Lead",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("contact_sheet_close")
        ) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Direct Phone Call
      ContactChannelCard(
        title = "Direct Mobile Call",
        subtitle = StoreInfo.FORMATTED_PHONE,
        detail = "Tap to call directly for instant stock inquiry & bulk orders",
        icon = Icons.Default.Call,
        iconBg = NavyDeep,
        testTag = "channel_call",
        onClick = { IntentUtils.dialPhoneNumber(context, StoreInfo.PHONE_NUMBER) }
      )

      Spacer(modifier = Modifier.height(10.dp))

      // WhatsApp Chat
      ContactChannelCard(
        title = "WhatsApp Chat",
        subtitle = "+91 ${StoreInfo.PHONE_NUMBER}",
        detail = "Chat with Ranjeet ji on WhatsApp for product videos & photos",
        icon = Icons.Default.Send,
        iconBg = WhatsAppGreen,
        testTag = "channel_whatsapp",
        onClick = {
          IntentUtils.openWhatsApp(
            context,
            StoreInfo.WHATSAPP_NUMBER,
            "Hello Ranjeet ji, I saw your official RS Store app and want to place an order!"
          )
        }
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Instagram
      ContactChannelCard(
        title = "Instagram Profile",
        subtitle = StoreInfo.INSTAGRAM_HANDLE,
        detail = "Follow for daily customer unboxings, new stock updates & reels",
        icon = Icons.Default.PhotoCamera,
        iconBg = InstagramGradientEnd,
        testTag = "channel_instagram",
        onClick = { IntentUtils.openInstagram(context, StoreInfo.INSTAGRAM_HANDLE) }
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Amazon Store
      ContactChannelCard(
        title = "Amazon Official Storefront",
        subtitle = "Amazon Verified Merchant",
        detail = "Secure checkout with Amazon A-to-Z guarantee & Prime delivery",
        icon = Icons.Default.ShoppingCart,
        iconBg = AmazonOrange,
        testTag = "channel_amazon",
        onClick = { IntentUtils.openAmazonStore(context, StoreInfo.AMAZON_STORE_URL) }
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Google Maps Location
      ContactChannelCard(
        title = "Google Maps Shop Location",
        subtitle = "Open Daily: 10:00 AM – 9:30 PM",
        detail = "Get turn-by-turn navigation directly to our retail store",
        icon = Icons.Default.LocationOn,
        iconBg = GoogleMapsRed,
        testTag = "channel_maps",
        onClick = { IntentUtils.openShopLocation(context, StoreInfo.SHOP_MAPS_URL) }
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Copy Details button
      Button(
        onClick = {
          val info = """
            ${StoreInfo.STORE_NAME} - ${StoreInfo.OWNER_NAME}
            Phone / WhatsApp: ${StoreInfo.FORMATTED_PHONE}
            Instagram: ${StoreInfo.INSTAGRAM_HANDLE}
            Amazon Store: ${StoreInfo.AMAZON_STORE_URL}
            Shop Location: ${StoreInfo.SHOP_MAPS_URL}
          """.trimIndent()
          IntentUtils.copyToClipboard(context, info, "Store Contact Info")
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("copy_contact_info_button"),
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        shape = RoundedCornerShape(12.dp)
      ) {
        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Copy Contact & Store Details",
          style = MaterialTheme.typography.labelLarge,
          fontWeight = FontWeight.Bold
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
private fun ContactChannelCard(
  title: String,
  subtitle: String,
  detail: String,
  icon: ImageVector,
  iconBg: Color,
  testTag: String,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag(testTag),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(CircleShape)
          .background(iconBg),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelLarge,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.SemiBold
        )
        Text(
          text = detail,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}
