package ru.otus.butterknifeanddatabinding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.otus.butterknifeanddatabinding.data_binding.Person;
import ru.otus.butterknifeanddatabinding.data_binding.PersonActivity;
import ru.otus.butterknifeanddatabinding.utils.BundleUtil;

public class MainView extends ConstraintLayout {

    @BindView(R.id.edit_mail)
    EditText edit_mail;
    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.button)
    Button button;

    static final String NAME = "NAME";
    static final String EMAIL = "EMAIL";
    static final String PHONE = "PHONE";

    public MainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.main_view, this, true);
        ButterKnife.bind(this, this);
        final Pattern pattern = Patterns.EMAIL_ADDRESS;

        TextMatcherCallback emailMatcher = new TextMatcherCallback() {
            @Override
            public boolean match(CharSequence s) {
                if(pattern.matcher(s).matches()) return true;
                return false;
            }
        };

        TextMatcherCallback nameMatcher = new TextMatcherCallback() {
            @Override
            public boolean match(CharSequence s) {
                if (s.length() > 0) return true;
                return false;
            }
        };

        TextGotCallback getName = buildCallBack(nameMatcher);
        TextGotCallback getEmail = buildCallBack(emailMatcher);
        TextGotCallback getPhone = new TextGotCallback() {
            @Override
            public void textGot(CharSequence s, final EditText editText, EditText nextEdit) {
                if (validPhoneNumber(s)){
                    editText.setOnKeyListener(new OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_ENTER){
                                editText.setEnabled(false);
                                button.setEnabled(true);
                                button.setBackgroundColor(Color.GREEN);
                                return true;
                            }
                            return false;
                        }
                    });
                }
            }
        };


        edit_name.setEnabled(true);
        edit_name.requestFocus();
        setListener(edit_name, edit_mail, getName);
        setListener(edit_mail, edit_phone, getEmail);
        setListener(edit_phone, null, getPhone);


    }

    private void setListener(final EditText editText, @Nullable final EditText nextEdit, final TextGotCallback callback) {
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callback.textGot(s, editText, nextEdit);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.button)
    protected void onButtonClick() {
        if (edit_mail.getText().length() > 0 && edit_name.getText().length() > 0 &&
                edit_phone.getText().length() > 0) {
            Intent intent = new Intent(getContext(), PersonActivity.class);
            intent.putExtras(
                    BundleUtil.fromParcelable(new Person(
                            edit_name.getText().toString(),
                            edit_phone.getText().toString(),
                            edit_mail.getText().toString()
                    )));
            getContext().startActivity(intent);
        }
    }

    private TextGotCallback buildCallBack(final TextMatcherCallback textMatcher){
        return new TextGotCallback() {
            @Override
            public void textGot(CharSequence s, final EditText editText, final EditText nextEdit) {
                if (textMatcher.match(s)){

                    editText.setOnKeyListener(new OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_ENTER){
                                nextEdit.setEnabled(true);
                                editText.setEnabled(false);
                                nextEdit.requestFocus();
                                return true;
                            }
                            return false;
                        }
                    });
                }
            }
        };
    }

    private boolean validPhoneNumber(CharSequence s){
        if (s.length() > 10 && s.toString().matches("^[0-9]+")) {
            return true;
        }
        return false;
    }

    interface TextMatcherCallback {
        boolean match(CharSequence s);
    }

    interface TextGotCallback {
        void textGot(CharSequence s, EditText editText, EditText nextEdit);
    }

}
