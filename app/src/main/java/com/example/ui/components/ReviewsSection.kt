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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StoreFaq
import com.example.data.StoreRepository
import com.example.model.CustomerReview
import com.example.model.StoreInfo
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.GoldPrimary

@Composable
fun ReviewsSection(
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .testTag("reviews_section")
  ) {
    // Overall Trust Score Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.weight(0.4f)
        ) {
          Text(
            text = "${StoreInfo.RATING}",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
          )
          Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(5) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(16.dp)
              )
            }
          }
          Text(
            text = "${StoreInfo.TOTAL_RATINGS}+ Verified",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 2.dp)
          )
        }

        Column(
          modifier = Modifier
            .weight(0.6f)
            .padding(start = 12.dp),
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          RatingBarRow(stars = "5★", progress = 0.94f)
          RatingBarRow(stars = "4★", progress = 0.05f)
          RatingBarRow(stars = "3★", progress = 0.01f)
          RatingBarRow(stars = "2★", progress = 0.00f)
          RatingBarRow(stars = "1★", progress = 0.00f)
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Customer Testimonials
    Text(
      text = "Verified Buyer Experiences",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(8.dp))

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      StoreRepository.reviews.forEach { review ->
        ReviewItemCard(review = review)
      }
    }

    // FAQs Accordion
    Spacer(modifier = Modifier.height(20.dp))
    Text(
      text = "Frequently Asked Questions",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(8.dp))

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      StoreRepository.faqs.forEach { faq ->
        FaqItemCard(faq = faq)
      }
    }
  }
}

@Composable
private fun RatingBarRow(stars: String, progress: Float) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
  ) {
    Text(
      text = stars,
      style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
      fontWeight = FontWeight.SemiBold,
      modifier = Modifier.width(22.dp)
    )
    LinearProgressIndicator(
      progress = { progress },
      modifier = Modifier
        .weight(1f)
        .height(6.dp)
        .clip(RoundedCornerShape(3.dp)),
      color = GoldPrimary,
      trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
  }
}

@Composable
private fun ReviewItemCard(review: CustomerReview) {
  Card(
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(Color(review.avatarColorHex)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = review.customerName.take(1),
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = review.customerName,
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = review.location,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          repeat(review.rating) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = null,
              tint = GoldPrimary,
              modifier = Modifier.size(14.dp)
            )
          }
        }
      }

      Text(
        text = review.comment,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(top = 8.dp)
      )

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = EmeraldTrust,
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "Verified Buyer",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = EmeraldTrust,
            fontWeight = FontWeight.Bold
          )
        }
        Text(
          text = review.date,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

@Composable
private fun FaqItemCard(faq: StoreFaq) {
  var expanded by remember { mutableStateOf(false) }

  Card(
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { expanded = !expanded }
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = faq.question,
          style = MaterialTheme.typography.labelLarge,
          fontWeight = FontWeight.SemiBold,
          modifier = Modifier.weight(1f)
        )
        Icon(
          imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      if (expanded) {
        Text(
          text = faq.answer,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 18.sp,
          modifier = Modifier.padding(top = 8.dp)
        )
      }
    }
  }
}
