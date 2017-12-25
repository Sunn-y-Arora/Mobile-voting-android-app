package myapp.app.com.mobilevoting;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");

        Bundle b = getArguments();
        String consno=b.getString("cons").trim();
        String name=b.getString("name").trim();
        String votedto=b.getString("voted to").trim();
        String flag=b.getString("flag").trim();
        TextView myText = (TextView) getView().findViewById(R.id.flash);
        Toast.makeText(getContext(),consno+name+votedto+flag,Toast.LENGTH_LONG);
        TextView n,v,f,c;
        n=(TextView) getView().findViewById(R.id.Name);
        c=(TextView) getView().findViewById(R.id.Age);
        v=(TextView) getView().findViewById(R.id.party);
        f=(TextView) getView().findViewById(R.id.message);

        //Integer fg =Integer.parseInt(flag);


        if(flag.compareTo("0")==0)
        {
            myText.setText("Cast Your Vote Today");
            v.setText("Not voted Yet");
            f.setText("No");
        }
        else
        {
            myText.setText("Congratulations you have casted your vote already");
            v.setText(votedto);
            f.setText("Yes");
        }
         n.setText(name);
        c.setText("No "+consno);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);



    }
}
