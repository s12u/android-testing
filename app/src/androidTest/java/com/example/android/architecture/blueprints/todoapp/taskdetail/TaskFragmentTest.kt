package com.example.android.architecture.blueprints.todoapp.taskdetail

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.deleteAllTasksBlocking
import com.example.android.architecture.blueprints.todoapp.di.RepositoryModule
import com.example.android.architecture.blueprints.todoapp.launchFragmentInHiltContainer
import com.example.android.architecture.blueprints.todoapp.saveTaskBlocking
import com.example.android.architecture.blueprints.todoapp.tasks.TasksFragment
import com.example.android.architecture.blueprints.todoapp.tasks.TasksFragmentDirections
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TasksFragmentTest {

    @get:Rule var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        hiltRule.inject()
    }

    @After
    fun cleanupDb() = runBlockingTest {
        repository.deleteAllTasksBlocking()
    }

    @Test
    fun clickTask_navigateToDetailFragmentOne() = runBlockingTest {
        repository.saveTaskBlocking(Task("TITLE1", "DESCRIPTION1", false, "id1"))
        repository.saveTaskBlocking(Task("TITLE2", "DESCRIPTION2", true, "id2"))

        // GIVEN - On the home screen
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<TasksFragment>(Bundle(), R.style.AppTheme) {
            Navigation.setViewNavController(view!!, navController)
        }
//        scenario.onFragment {
//            Navigation.setViewNavController(it.view!!, navController)
//        }

        // WHEN - Click on the first list item
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                        hasDescendant(withText("TITLE1")), click()))

        // THEN - Verify that we navigate to the first detail screen
        verify(navController).navigate(
                TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment( "id1")
        )
    }

    @Test
    fun clickAddTaskButton_navigateToAddEditFragment() {
        // GIVEN - On the home screen
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<TasksFragment>(Bundle(), R.style.AppTheme) {
            Navigation.setViewNavController(view!!, navController)
        }

        // WHEN - Click on the "+" button
        onView(withId(R.id.add_task_fab)).perform(click())

        // THEN - Verify that we navigate to the add screen
        verify(navController).navigate(
                TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                        null, getApplicationContext<Context>().getString(R.string.add_task)
                )
        )
    }
}