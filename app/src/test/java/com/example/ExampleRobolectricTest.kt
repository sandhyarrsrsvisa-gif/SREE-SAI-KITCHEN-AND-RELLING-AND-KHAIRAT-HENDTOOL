package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.StoreRepository
import com.example.model.StoreInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("RS Store", appName)
  }

  @Test
  fun `verify store info contact and products`() {
    assertEquals("8237225524", StoreInfo.PHONE_NUMBER)
    assertEquals("@ranjeetsharma4758", StoreInfo.INSTAGRAM_HANDLE)
    assertTrue(StoreInfo.AMAZON_STORE_URL.contains("A3ECK4PAUPMH4I"))
    assertTrue(StoreInfo.SHOP_MAPS_URL.contains("uU7qEDBmQLW7gmasl"))
    assertTrue(StoreRepository.products.isNotEmpty())
  }
}
