package com.example.arinspect;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Mock
    RowViewModel rowViewModel;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        rowViewModel = ViewModelProviders.of(mActivityTestRule.getActivity()).get(RowViewModel.class);
    }

    @Test
    public void changeOrientationToLandscape() {
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Test
    public void scrollToEnd() {
        onView(isRoot()).perform(waitFor(2000));
        if (rowViewModel != null && rowViewModel.getAllRows() != null && rowViewModel.getAllRows().getValue() != null && !rowViewModel.getAllRows().getValue().isEmpty()) {
            onView(ViewMatchers.withId(R.id.row_recyclerview))
                    .perform(RecyclerViewActions.scrollToHolder(isInTheLast()));
        } else {
            Log.e("Test", "No data found");
        }
    }

    /**
     * Matches the {@link RowRecyclerviewAdapter.ViewHolder}s in the last of the list.
     */
    private static Matcher<RowRecyclerviewAdapter.ViewHolder> isInTheLast() {
        return new TypeSafeMatcher<RowRecyclerviewAdapter.ViewHolder>() {
            @Override
            protected boolean matchesSafely(RowRecyclerviewAdapter.ViewHolder item) {
                return item.isLastItem();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Last Item");
            }
        };
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }
}