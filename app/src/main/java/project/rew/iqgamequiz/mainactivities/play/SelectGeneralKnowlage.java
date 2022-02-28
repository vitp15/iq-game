package project.rew.iqgamequiz.mainactivities.play;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import project.rew.iqgamequiz.MainActivity;
import project.rew.iqgamequiz.R;
import project.rew.iqgamequiz.mainactivities.play.notused.KnewCategorie;
import project.rew.iqgamequiz.mainactivities.play.nivels.NivelSelectActivity;

public class SelectGeneralKnowlage extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<KnewCategorie> options;
    FirebaseRecyclerAdapter<KnewCategorie, MyViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_general_knowlage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.recyclerview);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("RO").child("Categories");

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        loadData();

    }

    private void loadData() {
        options = new FirebaseRecyclerOptions.Builder<KnewCategorie>().setQuery(databaseReference, KnewCategorie.class).build();
        adapter = new FirebaseRecyclerAdapter<KnewCategorie, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull KnewCategorie model) {
                holder.title.setText(model.getTitle());
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(SelectGeneralKnowlage.this, NivelSelectActivity.class);
                        intent.putExtra("categorie",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.maket_categoire, parent, false);
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SelectGeneralKnowlage.this, MainActivity.class);
        startActivity(intent);
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView title;
    CardView cardView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
        title = itemView.findViewById(R.id.title);
        cardView = itemView.findViewById(R.id.view);
    }
}