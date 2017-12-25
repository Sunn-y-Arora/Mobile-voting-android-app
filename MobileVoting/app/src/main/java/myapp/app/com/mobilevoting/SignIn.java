package myapp.app.com.mobilevoting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class SignIn extends AppCompatActivity {

    EditText ed1,ed2;
    Button b,b2;
    FirebaseDatabase database= FirebaseDatabase. getInstance ();;
    DatabaseReference myRef = database.getReference();
    DatabaseReference users = myRef.child("users");
    static String usermob;
    static DatabaseReference user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ed1=(EditText) findViewById(R.id.editText);
        ed2=(EditText) findViewById(R.id.editText2);
        b=(Button) findViewById(R.id.button);
        b2=(Button)findViewById(R.id.admin);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),AdminLogin.class);
                startActivity(in);
            }
        });

        FirebaseDatabase database= FirebaseDatabase. getInstance ();;
        DatabaseReference myRef = database.getReference();
        try{
            String mob=ed2.getText().toString().trim();
            user1=users.child(mob);
           // usermob=user1.toString();
            //Toast. makeText (getBaseContext(),user1.toString(),Toast. LENGTH_SHORT ).show();
        }
        catch(Exception e)
        {
            Toast. makeText (getBaseContext(),"Invalid Mobile No.",Toast. LENGTH_SHORT ).show();
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user1=users.child(ed2.getText().toString());


              user1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String id=ed1.getText().toString().trim();
                        String mob=ed2.getText().toString().trim();
                        Map<String,Object> val1 = (Map<String, Object>) dataSnapshot.getValue();
                        String userid=(String)val1.get("voter id");

                        if(userid.compareTo(id)==0)
                        {
                            Intent in = new Intent(getApplicationContext(),VoterHome.class);
                            /*String s=(String)val1.get("Name");
                            s+=","+(String)val1.get("flag");
                            s+=","+(String)val1.get("voted to");
                            s+=","+(String)val1.get("voter id");
                            s+=","+(String)val1.get("cons");
                            //in.putExtra("arg",s);*/
                            //Toast. makeText (getBaseContext(),,Toast. LENGTH_SHORT ).show();

                            in.putExtra("name",(String)val1.get("Name"));
                            in.putExtra("flag",(String)val1.get("flag"));
                            in.putExtra("voted",(String)val1.get("voted to"));
                            in.putExtra("voter",(String)val1.get("voter id"));
                            in.putExtra("const",val1.get("cons").toString());
                            in.putExtra("mob",mob);
                            startActivity(in);
                        }
                        else
                        {
                            Toast. makeText (getBaseContext(),"Id not matching with Mobile No",Toast. LENGTH_SHORT ).show();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
    }
}
