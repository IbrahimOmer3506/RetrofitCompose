package com.oztasibrahimomer.retrofitcompose3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oztasibrahimomer.retrofitcompose3.model.CryptoModel
import com.oztasibrahimomer.retrofitcompose3.service.CryptoAPI
import com.oztasibrahimomer.retrofitcompose3.ui.theme.RetrofitCompose3Theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitCompose3Theme {

                MainScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun MainScreen(){

    var cryptoList= remember {
        mutableStateListOf<CryptoModel>()
    }

    val BASE_URL = " https://raw.githubusercontent.com/"

    val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoAPI::class.java)

    val call=retrofit.getData()

    call.enqueue(object :Callback<List<CryptoModel>>{
        override fun onResponse(
            call: Call<List<CryptoModel>>,
            response: Response<List<CryptoModel>>
        ) {
            if(response.isSuccessful){
                response.body()?.let {

                    //list
                    cryptoList.addAll(it)

                }
            }
        }

        override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
            t.printStackTrace()
        }
    })


    Scaffold(topBar = {AppBar()}) {

        CryptoGridList(cryptos = cryptoList)
        
    }


}
@Composable

fun CryptoList(cryptos:List<CryptoModel>){

    LazyColumn{
        items(cryptos){crypto->

            CryptoRow(crypto1 =crypto)

        }
    }


}

@Composable
fun CryptoGridList(cryptos:List<CryptoModel>){

    LazyVerticalGrid(columns =GridCells.Adaptive(minSize = 128.dp), modifier =Modifier.clickable { println("anneeee") }.padding(10.dp) ){

        items(cryptos){

            CryptoRow(crypto1 = it)

        }
    }

}
@Composable

fun CryptoRow(crypto1:CryptoModel){

    Box(modifier = Modifier.padding(bottom = 5.dp, top = 5.dp)){
        Column(modifier = Modifier
            .width(150.dp)
            .height(130.dp)
            .background(color = MaterialTheme.colors.surface)
            .padding(12.dp)) {

            Text(text = crypto1.currency, fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.background(color=MaterialTheme.colors.surface))
            Text(text = crypto1.price, fontSize = 22.sp, fontStyle = FontStyle.Italic, color = Color.Black, modifier = Modifier.background(color=MaterialTheme.colors.onPrimary))

        }
    }

}


@Composable
fun AppBar(){

    TopAppBar(backgroundColor = Color.Cyan) {
        Text(text = "Retrofit Compose", fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Spacer(modifier = Modifier.padding(25.dp))

        Image(modifier = Modifier
            .clickable { println("yaraqu") }
            .background(color = Color.Green), imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "android logo"
            )
        
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitCompose3Theme {

        CryptoRow(crypto1 = CryptoModel("BTC","123456"))

    }
}