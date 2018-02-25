package nyc.c4q.comprehensivefinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BreedsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private DogBreed breed;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds);

        TextView textView = (TextView) findViewById(R.id.session_message_textview);
        Intent intent = getIntent();
        String user = intent.getStringExtra("currentUser");
        textView.setText("What kind of dog would you like to see, " + user +"?");



        retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/breed/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getPhoto("terrier");
        getPhoto("spaniel");
        getPhoto("retriever");
        getPhoto("poodle");
    }

    private void getPhoto(String breedName) {
        DogBreedsApi breedService = retrofit.create(DogBreedsApi.class);
        Call<DogBreed> getBreedimg = breedService.getBreed(breedName);
        getBreedimg.enqueue(new Callback<DogBreed>() {
            @Override
            public void onResponse(Call<DogBreed> call, Response<DogBreed> response) {

                breed = response.body();

                if (breed.getMessage().contains("terrier")) {
                    Picasso.with(BreedsActivity.this).load(breed.getMessage()).into(terrier);
                }
                if (breed.getMessage().contains("spaniel")) {
                    Picasso.with(BreedsActivity.this).load(breed.getMessage()).into(spaniel);
                }
                if (breed.getMessage().contains("retriever")) {
                    Picasso.with(BreedsActivity.this).load(breed.getMessage()).into(retriever);
                }
                if (breed.getMessage().contains("poodle")) {
                    Picasso.with(BreedsActivity.this).load(breed.getMessage()).into(poodle);
                }
            }

            @Override
            public void onFailure(Call<DogBreed> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
