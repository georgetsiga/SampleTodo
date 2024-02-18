package com.example.sampletodo

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampletodo.adaptor.TodoAdaptor
import com.example.sampletodo.databinding.FragmentTodoListBinding
import com.example.sampletodo.utils.Constants
import com.example.sampletodo.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TodoListFragment : Fragment() {
    private val todoViewModel: TodoViewModel by activityViewModels()
    private var todoAdapter: TodoAdaptor? = null
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private var action: ACTION = ACTION.ADD
    private var index = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentResultListener()
        setMenuProvider()
        setAdapter()
        observeData()
    }

    private fun fragmentResultListener() {
        setFragmentResultListener(Constants.NEW_TODO) {_, bundle ->
            val todoAction = bundle.getString(Constants.NEW_TODO_ACTION)
            todoAction?.let {
                action = ACTION.fromString(it)
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch{
            todoViewModel.getTodos().collect {
                todoAdapter?.setTodoData(it)
                when(action) {
                    ACTION.ADD -> todoAdapter?.notifyDataSetChanged()
                    ACTION.UPDATE -> todoAdapter?.notifyItemChanged(index)
                    ACTION.DELETE -> todoAdapter?.notifyItemChanged(index)
                }
            }
        }
    }

    private fun setAdapter() {
        todoAdapter = TodoAdaptor(requireContext(), ::onTodoClick, ::onCompletedClick)
        binding.recyclerTodo.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    private fun setMenuProvider() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.action_add_todo -> {
                        action = ACTION.ADD
                        val actionTodoListFragmentToTodoDetailsFragment =
                            TodoListFragmentDirections.actionTodoListFragmentToTodoDetailsFragment(0)
                        findNavController().navigate(actionTodoListFragmentToTodoDetailsFragment)
                        true
                    } else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onTodoClick(todo: TodoModel, position: Int) {
        action = ACTION.UPDATE
        index = position
        val actionTodoListFragmentToTodoDetailsFragment =
            TodoListFragmentDirections.actionTodoListFragmentToTodoDetailsFragment(todo.id)
        findNavController().navigate(actionTodoListFragmentToTodoDetailsFragment)
    }

    private fun onCompletedClick(todo: TodoModel, position: Int) {
        action = ACTION.UPDATE
        index = position
      lifecycleScope.launch {
          todoViewModel.updateTodo(todo)
      }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    enum class ACTION(val value: String) {
        ADD("ADD"), UPDATE("UPDATE"), DELETE("DELETE");

        companion object {
            fun fromString(value: String): ACTION {
                val ordinal = ACTION.valueOf(value).ordinal
                return ACTION.values()[ordinal]
            }
        }
    }
}