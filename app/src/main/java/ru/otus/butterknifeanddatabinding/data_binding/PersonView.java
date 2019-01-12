package ru.otus.butterknifeanddatabinding.data_binding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import ru.otus.butterknifeanddatabinding.R;
import ru.otus.butterknifeanddatabinding.databinding.PersonViewExBinding;

public class PersonView extends LinearLayout {

    private final PersonViewExBinding binding;

    public PersonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.person_view_ex, this, true);
        PersonActivity activity = (PersonActivity) context;
        Person person = activity.getIntent().getParcelableExtra("Person");
        bind(person);
    }

    public void bind(ru.otus.butterknifeanddatabinding.data_binding.Person person){

        binding.setPerson(person);
    }


}
