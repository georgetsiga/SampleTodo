package com.example.sampletodo

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sampletodo.databinding.FragmentTodoDetailsBinding
import com.example.sampletodo.utils.Constants
import com.example.sampletodo.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoDetailsFragment : Fragment() {
    private var todoModel: TodoModel? = null
    private var todoModelId: Int = 0
    private val todoViewModel: TodoViewModel by activityViewModels()
    private val arguments: TodoDetailsFragmentArgs by navArgs()
    private var _binding: FragmentTodoDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoModelId = arguments.TodoModelIdArg
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentTodoDetailsBinding.inflate(inflater, container, false)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (todoModelId != 0) {
            setMenuProvider()
            getTodoModel(todoModelId)
        }

        binding.buttonSave.setOnClickListener {
            sendData()
        }
    }

    private fun setData(todo: TodoModel) {
        binding.titleText.setText(todo.title)
        binding.textDescription.setText(todo.description)

        binding.buttonSave.text = getString(R.string.update)
    }

    private fun getTodoModel(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            val todo = lifecycleScope.async { todoViewModel.getTodo(id) }
            todoModel = todo.await()
            todoModel?.let {
                setData(it)
            }
        }
    }

    private fun setMenuProvider() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_edit, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_delete_todo -> {
                        showDialog()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun sendData() {
        if (binding.titleText.text.toString()
                .isNotEmpty() && binding.textDescription.text.toString().isNotEmpty()
        ) {
            viewLifecycleOwner.lifecycleScope.launch {
                if (todoModelId == 0) {
                    todoModel = TodoModel(
                        title = binding.titleText.text.toString().trim(),
                        description = binding.textDescription.text.toString().trim(),
                        isCompleted = 0
                    )
                    todoViewModel.addTodos(todoModel!!)
                } else {
                    todoModel?.title = binding.titleText.text.toString().trim()
                    todoModel?.description = binding.textDescription.text.toString().trim()
                    todoViewModel.updateTodo(todoModel!!)
                }
            }
            navigateBack(TodoListFragment.ACTION.ADD.toString())
        } else {
            Toast.makeText(
                binding.root.context,
                "Please supply all required details",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun navigateBack(action: String) {
        setFragmentResult(Constants.NEW_TODO, bundleOf(Constants.NEW_TODO_ACTION to action))
        findNavController().navigateUp()
    }

    private fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(context, R.style.ThemeMyDialog)
        dialogBuilder.setCancelable(true)
        dialogBuilder.setTitle("Delete todo")
        dialogBuilder.setMessage("Are you sure want to delete the todo.\nThis action is not reversible")
        dialogBuilder.setPositiveButton("Yes, delete") { _, _ ->
            deleteTodo()
        }
        val alert = dialogBuilder.create()
        alert.show()
        val button = alert.getButton(DialogInterface.BUTTON_POSITIVE)
        button.setTextColor(Color.parseColor("#FFBB86FC"))
    }

    private fun deleteTodo() {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = lifecycleScope.async {
                todoViewModel.deleteTodo(todoModelId)
            }
            result.await()
            navigateBack(TodoListFragment.ACTION.DELETE.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}