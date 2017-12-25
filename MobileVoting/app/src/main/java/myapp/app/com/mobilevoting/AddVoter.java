package myapp.app.com.mobilevoting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddVoter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voter);

        final EditText name=(EditText) findViewById(R.id.vname);
        final EditText mob=(EditText) findViewById(R.id.vmob);
        final EditText con=(EditText) findViewById(R.id.vcons);
        final EditText vid=(EditText) findViewById(R.id.vid);
        final String flag="0";
        final String voted="none";
        final Button add=(Button)findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database= FirebaseDatabase. getInstance ();;
                DatabaseReference myRef = database.getReference().child("users");

                String sn=name.getText().toString().trim();
                String sm=mob.getText().toString().trim();
                String sc=con.getText().toString().trim();
                String sv=vid.getText().toString().trim();

                //Toast.makeText(getBaseContext(), myRef.toString(), Toast.LENGTH_SHORT).show();
                if(sm.length()==10)
                {
                    //Toast.makeText(getBaseContext(), myRef.toString(), Toast.LENGTH_SHORT).show();
                    if(sn.length()==0 || sc.length()==0 || sv.length()==0)
                    {
                        Toast.makeText(AddVoter.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Integer t= Integer.parseInt(sc);
                        if(t>7 || t<1)
                        {
                            Toast.makeText(getBaseContext(),"Consituency must be between 1 and 7",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            myRef=myRef.child(sm);
                            DatabaseReference temp=myRef.child("Name");   temp.setValue(sn);
                            temp=myRef.child("cons");   temp.setValue(sc);
                            temp=myRef.child("voter id");   temp.setValue(sv);
                            temp=myRef.child("flag");   temp.setValue(flag);
                            temp=myRef.child("voted to"); temp.setValue(voted);
                            Toast.makeText(getBaseContext(), "Created", Toast.LENGTH_SHORT).show();

                            Intent in = new Intent(getApplicationContext(),AdminPage.class);
                            startActivity(in);

                        }

                    }
                }
                else{
                    Toast.makeText(getBaseContext(),"Mobile no must be of 10 digits",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
