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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Security
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
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.GoogleMapsRed
import com.example.ui.theme.NavyDeep
import com.example.util.IntentUtils

@Composable
fun TrustGuaranteesSection(
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .testTag("trust_guarantees_section")
  ) {
    Text(
      text = "Why Customers Trust RS Store",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
    )
    Text(
      text = "We prioritize authenticity, transparency, and personal accountability.",
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      modifier = Modifier.padding(bottom = 12.dp)
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      TrustPillarRow(
        title = "100% Genuine Sealed Products",
        description = "Every piece is brand certified with valid warranty and tax invoice. No refurbished or counterfeit items.",
        icon = Icons.Default.Security,
        badgeColor = EmeraldTrust
      )

      TrustPillarRow(
        title = "Direct Owner Accountability",
        description = "Speak directly to owner Ranjeet Sharma at ${StoreInfo.FORMATTED_PHONE}. No automated bots or delays.",
        icon = Icons.Default.Call,
        badgeColor = NavyDeep,
        onClick = { IntentUtils.dialPhoneNumber(context, StoreInfo.PHONE_NUMBER) }
      )

      TrustPillarRow(
        title = "Verified Amazon Storefront",
        description = "Official seller on Amazon India with prime delivery, verified reviews, and customer safety.",
        icon = Icons.Default.ShoppingCart,
        badgeColor = AmazonOrange,
        onClick = { IntentUtils.openAmazonStore(context, StoreInfo.AMAZON_STORE_URL) }
      )

      TrustPillarRow(
        title = "Physical Shop & Google Maps Verified",
        description = "Visit our showroom in person to see and test any product before purchase.",
        icon = Icons.Default.LocationOn,
        badgeColor = GoogleMapsRed,
        onClick = { IntentUtils.openShopLocation(context, StoreInfo.SHOP_MAPS_URL) }
      )
    }
  }
}

@Composable
private fun TrustPillarRow(
  title: String,
  description: String,
  icon: ImageVector,
  badgeColor: Color,
  onClick: (() -> Unit)? = null
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.Top
    ) {
      Box(
        modifier = Modifier
          .size(38.dp)
          .clip(CircleShape)
          .background(badgeColor.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = badgeColor,
          modifier = Modifier.size(20.dp)
        )
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(
          text = title,
          style = MaterialTheme.typography.labelLarge,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = description,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 18.sp,
          modifier = Modifier.padding(top = 2.dp)
        )
      }
    }
  }
}
