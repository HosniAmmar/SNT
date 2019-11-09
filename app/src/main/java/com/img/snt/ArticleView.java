package com.img.snt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.img.snt.DAO.AppDatabase;
import com.img.snt.Model.Item;
import com.squareup.picasso.Picasso;

public class ArticleView extends AppCompatActivity {

    TextView titleText;
    TextView contentText;
    ImageView thumbnailImage;
    Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);
        String value = getIntent().getExtras().getString("id");
       // Toast.makeText(this, "Gotcha id = "+value, Toast.LENGTH_LONG).show();
        titleText = findViewById(R.id.articleTitle);
        contentText = findViewById(R.id.articleText);
        thumbnailImage = findViewById(R.id.articleImage);
        item = AppDatabase.getAppDatabase(ArticleView.this).itemDAO().getOneById(value);
        titleText.setText(item.title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentText.setText(Html.fromHtml(item.content, Html.FROM_HTML_MODE_COMPACT));
        } else {
            contentText.setText(Html.fromHtml(item.content));
        }
        //contentText.setText(item.content);
        String imageUrl = item.getThumbnail();
        if(!item.thumbnail.contains("https")){
            if(item.thumbnail.contains("http")){
                imageUrl=item.thumbnail.replaceFirst("http","https");

            }

        }
        if(imageUrl!= null && imageUrl.length()>10 ){
            Picasso.get().load(imageUrl).into(thumbnailImage);
            Toast.makeText(this, "Image url = "+imageUrl, Toast.LENGTH_LONG).show();

        }
    }
}
