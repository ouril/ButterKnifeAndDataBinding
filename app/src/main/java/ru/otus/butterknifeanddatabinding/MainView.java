package ru.otus.butterknifeanddatabinding;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.otus.butterknifeanddatabinding.data_binding.PersonActivity;

public class MainView extends ConstraintLayout {

    @BindView(R.id.edit_mail)
    EditText edit_mail;
    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.button)
    Button button;


    public MainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.main_view, this, true);
        ButterKnife.bind(this, this);
        // add callback
    }
    @OnClick(R.id.button)
    protected void onButtonClick() {
        if (edit_mail.getText().length() > 0 && edit_name.getText().length() > 0 &&
                edit_phone.getText().length() > 0) {
            Intent intent = new Intent(getContext(), PersonActivity.class);
            intent.putExtra("NAME", edit_name.getText());
            intent.putExtra("PHONE", edit_phone.getText());
            intent.putExtra("EMAIL", edit_mail.getText());
            getContext().startActivity(intent);

        }
    }

    interface MainCallback {
        void openPerson();
    }
}
