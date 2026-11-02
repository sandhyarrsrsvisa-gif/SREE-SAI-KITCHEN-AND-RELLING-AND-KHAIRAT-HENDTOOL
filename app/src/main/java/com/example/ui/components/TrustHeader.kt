package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.StoreInfo
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentUtils

@Composable
fun TrustHeader(
  onImageInfoClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .testTag("trust_header_card"),
    shape = RoundedCornerShape(20.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
    ) {
      // Hero Background Showroom Photo
      Image(
        painter = painterResource(id = R.drawable.img_store_hero),
        contentDescription = "Ranjeet Sharma Store Showroom",
        modifier = Modifier.matchParentSize(),
        contentScale = ContentScale.Crop
      )

      // Gradient overlay for contrast and luxury appearance
      Box(
        modifier = Modifier
          .matchParentSize()
          .background(
            Brush.verticalGradient(
              colors = listOf(
                NavyDeep.copy(alpha = 0.55f),
                NavyDeep.copy(alpha = 0.88f),
                NavyDeep.copy(alpha = 0.98f)
              )
            )
          )
      )

      // Content
      Column(
        modifier = Modifier
          .matchParentSize()
          .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
      ) {
        // Top Verified Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(EmeraldTrust.copy(alpha = 0.25f))
              .border(1.dp, EmeraldTrust.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
              .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Verified,
              contentDescription = "Verified Merchant",
              tint = EmeraldTrust,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Verified Official Seller",
              style = MaterialTheme.typography.labelSmall,
              color = Color.White,
              fontWeight = FontWeight.Bold
            )
          }

          // Rating badge
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(Color.Black.copy(alpha = 0.45f))
              .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "Rating",
              tint = GoldPrimary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "${StoreInfo.RATING} ★ (${StoreInfo.TOTAL_RATINGS}+)",
              style = MaterialTheme.typography.labelSmall,
              color = Color.White,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // Center: Store Name & Owner Details
        Column(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = StoreInfo.STORE_NAME,
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              letterSpacing = 0.5.sp
            ),
            color = Color.White
          )
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 2.dp)
          ) {
            Text(
              text = "Owned & Managed by ",
              style = MaterialTheme.typography.bodyMedium,
              color = Color.White.copy(alpha = 0.8f)
            )
            Text(
              text = StoreInfo.OWNER_NAME,
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
              color = GoldPrimary
            )
          }

          Text(
            text = StoreInfo.SELLER_TAGLINE,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.75f),
            modifier = Modifier.padding(top = 4.dp)
          )

          // Interactive prompt banner for clicking image
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 10.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(GoldPrimary.copy(alpha = 0.18f))
              .border(1.dp, GoldPrimary.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
              .clickable(onClick = onImageInfoClick)
              .padding(horizontal = 10.dp, vertical = 6.dp)
              .testTag("interactive_image_info_banner"),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = "Image Details info",
              tint = GoldPrimary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Tap any product image below for complete info, specs & zoom!",
              style = MaterialTheme.typography.labelSmall,
              color = Color.White,
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        // Bottom CTA Buttons: Call & WhatsApp directly
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = {
              IntentUtils.openWhatsApp(
                context,
                StoreInfo.WHATSAPP_NUMBER,
                "Hello Ranjeet ji, I want to inquire about ordering products from your store."
              )
            },
            modifier = Modifier
              .weight(1f)
              .height(42.dp)
              .testTag("header_whatsapp_cta"),
            colors = ButtonDefaults.buttonColors(
              containerColor = WhatsAppGreen,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Send,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "WhatsApp Chat",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold
            )
          }

          OutlinedButton(
            onClick = { IntentUtils.dialPhoneNumber(context, StoreInfo.PHONE_NUMBER) },
            modifier = Modifier
              .weight(1f)
              .height(42.dp)
              .testTag("header_call_cta"),
            colors = ButtonDefaults.outlinedButtonColors(
              contentColor = Color.White
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
              brush = Brush.horizontalGradient(listOf(GoldPrimary, Color.White))
            ),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Call,
              contentDescription = null,
              tint = GoldPrimary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Call ${StoreInfo.PHONE_NUMBER}",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}
