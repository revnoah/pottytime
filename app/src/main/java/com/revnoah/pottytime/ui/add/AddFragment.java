package com.revnoah.pottytime.ui.add;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.revnoah.pottytime.R;
import com.revnoah.pottytime.database.AppDatabase;
import com.revnoah.pottytime.model.Category;
import com.revnoah.pottytime.model.Entry;

import java.util.List;
import java.util.concurrent.Executors;

public class AddFragment extends Fragment {

    private AddViewModel mViewModel;
    private RadioGroup radioGroup;
    private Button submitButton;
    private TextView cancelLink;
    private AppDatabase database;

    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout into a View object
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // Find views by ID using the inflated view
        radioGroup = view.findViewById(R.id.radio_group);
        submitButton = view.findViewById(R.id.button_submit);
        cancelLink = view.findViewById(R.id.cancel_link);

        // Initialize the database instance
        database = AppDatabase.getInstance(requireContext());

        // Fetch and populate categories
        populateCategories();

        // Set onClickListener for submit button
        submitButton.setOnClickListener(v -> submitEntry());

        // Set onClickListener for cancel link
        cancelLink.setOnClickListener(v -> requireActivity().onBackPressed());

        // Return the inflated view
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddViewModel.class);
        // TODO: Use the ViewModel
    }

    private void populateCategories() {
        // Fetch categories from the database asynchronously
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Category> categories = database.categoryDao().getAllCategories();
            requireActivity().runOnUiThread(() -> {
                for (Category category : categories) {
                    RadioButton radioButton = new RadioButton(requireContext());
                    radioButton.setText(category.getDescription());
                    radioButton.setTag(category.getId()); // Store category ID for later use
                    radioGroup.addView(radioButton);
                }
            });
        });
    }

    private void submitEntry() {
        // Get the selected radio button
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = radioGroup.findViewById(selectedRadioButtonId);

        if (selectedRadioButton != null) {
            // Retrieve the category ID from the tag
            int categoryId = (int) selectedRadioButton.getTag();

            // Create a new entry and save it to the database
            long currentTime = System.currentTimeMillis();
            Entry newEntry = new Entry(categoryId, currentTime);

            Executors.newSingleThreadExecutor().execute(() -> {
                database.entryDao().insert(newEntry);
                // Go back to the previous screen or show a confirmation
                requireActivity().runOnUiThread(() -> requireActivity().onBackPressed());
            });
        } else {
            // Handle no selection made (optional)
            // For example, show a Toast message to the user
        }
    }
}