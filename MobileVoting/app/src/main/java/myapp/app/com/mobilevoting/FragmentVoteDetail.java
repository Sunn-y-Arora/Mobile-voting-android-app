package myapp.app.com.mobilevoting;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.R.id.list;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentVoteDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentVoteDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentVoteDetail extends Fragment {
    private Spinner spinner1;
    String candi;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vote_detail, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");
        Bundle b = getArguments();
        String s=b.getString("cons");
        addItemsOnSpinner(s);
        addListenerOnSpinnerItemSelection();
        addListenerOnButton();


    }

    public void addItemsOnSpinner(String s) {

        spinner1 = (Spinner) getView().findViewById(R.id.spinner1) ;
        final List<String> list = new ArrayList<String>();
        final FirebaseDatabase database= FirebaseDatabase. getInstance ();;
        DatabaseReference myRef = database.getReference();
        DatabaseReference candi = myRef.child("constituency").child(s).child("Candidates");
        //Toast.makeText(this.getContext(),candi.toString(),Toast.LENGTH_LONG);
        //DatabaseReference candi1=consty.child("5");
        //DatabaseReference candi=candi1.child("Candidates");


        candi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a1=dataSnapshot.getValue().toString();
                //EditText ed1=(EditText) getView().findViewById(R.id.test);
                String a[]=a1.split(",");
                for(String i: a)
                {
                    list.add(i.trim());
                }

                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String> (getActivity(),android.R.layout.simple_spinner_dropdown_item,list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(dataAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    public void addListenerOnButton() {

        spinner1 = (Spinner) getView().findViewById(R.id.spinner1);
        Toast.makeText(this.getContext(),String.valueOf(spinner1.getSelectedItem()),Toast.LENGTH_SHORT).show();

        Button btnSubmit = (Button) getView().findViewById(R.id.button2);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final FirebaseDatabase database= FirebaseDatabase. getInstance ();
                final DatabaseReference myRef = database.getReference().child("candidates").child(candi);

                Toast.makeText(getContext(),myRef.toString(),Toast.LENGTH_LONG).show();

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String,Object> val1 = (Map<String, Object>) dataSnapshot.getValue();

                        TextView n,a,p,m;
                        n=(TextView) getView().findViewById(R.id.Name);
                        a=(TextView) getView().findViewById(R.id.Age);
                        p=(TextView) getView().findViewById(R.id.party);
                        m=(TextView) getView().findViewById(R.id.message);

                        Toast.makeText(getContext(),val1.keySet().toString(),Toast.LENGTH_LONG);

                        n.setText((String)val1.get("Name"));
                        a.setText((String)val1.get("Age"));
                        p.setText((String)val1.get("Party"));
                        m.setText((String)val1.get("Message"));

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                //Toast.makeText(this., "OnClickListener : " +"\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()),Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) getView().findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
            candi=parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

}


