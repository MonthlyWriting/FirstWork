package com.example.monthlywriting.daily.bottomsheet

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monthlywriting.R
import com.example.monthlywriting.daily.viewmodel.DailyWritingBottomSheetViewModel
import com.example.monthlywriting.databinding.FragmentDailyWritingBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DailyWritingBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentDailyWritingBottomSheetBinding
    private val viewModel : DailyWritingBottomSheetViewModel by activityViewModels()

    private val args : DailyWritingBottomSheetArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyWritingBottomSheetBinding.inflate(layoutInflater)

        viewModel.getCurrentItem(args.id)
        setObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            bottomSheetCloseButton.setOnClickListener {
                NavHostFragment.findNavController(this@DailyWritingBottomSheet).navigateUp()
            }

            addDailyMemo.setOnClickListener {
                val action = DailyWritingBottomSheetDirections.openAddMemo(args.id)
                NavHostFragment.findNavController(this@DailyWritingBottomSheet).navigate(action)
            }

            bottomSheetMonthtimes.setOnLongClickListener {
                chooseDate()
                true
            }
        }
    }

    private fun setObservers() {
        viewModel.currentItem.observe(viewLifecycleOwner){

            Log.d("test", it.toString())
            binding.bottomSheetTitle.text = it.name
            val doneList = it.done
            val monthTimesDoneList = it.monthtimesdone

            when(it.type) {
                "daily" -> {
                    displayBottomSheetByType("daily")
                    binding.bottomSheetCheckItem.apply {
                        this.adapter = DailyCheckItemAdapter(doneList ,requireContext()) { date, boolean -> setItemDone(date, boolean) }
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    }
                }
                "weekly" -> {
                    displayBottomSheetByType("weekly")
                    binding.bottomSheetCheckItem.apply {
                        this.adapter = DailyCheckItemAdapter(doneList,requireContext()) { date, boolean -> setItemDone(date, boolean) }
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    }
                }
                "monthly" -> {
                    displayBottomSheetByType("monthly")
                    if (it.monthtimes == 0){
                        binding.bottomSheetMonthtimes.visibility = View.GONE
                    } else {
                        val doneNumbers = monthTimesDoneList.size
                        binding.bottomSheetMonthtimes.text = getString(R.string.bottom_sheet_month_times, doneNumbers, it.monthtimes)
                    }
                }
            }

            binding.bottomSheetDailyMemo.apply {
                this.adapter = DailyMemoItemAdapter(it.dailymemo, this@DailyWritingBottomSheet)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun displayBottomSheetByType(type : String) {
        when (type) {
            "daily" -> {
                binding.bottomSheetCheckItem.visibility = View.VISIBLE
                binding.bottomSheetMonthtimes.visibility = View.GONE
            }
            "weekly" -> {
                binding.bottomSheetCheckItem.visibility = View.VISIBLE
                binding.bottomSheetMonthtimes.visibility = View.GONE
            }
            "monthly" -> {
                binding.bottomSheetCheckItem.visibility = View.GONE
                binding.bottomSheetMonthtimes.visibility = View.VISIBLE
            }
        }
    }

    private fun setItemDone(date: Int, boolean: Boolean) {
        viewModel.setItemDone(date, boolean)
    }

    private fun chooseDate() {
        val currentYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt()
        val currentMonth = SimpleDateFormat("MM", Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt()
        val currentDay = SimpleDateFormat("dd", Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt()

        val datePickerDialog = DatePickerDialog(requireContext(), {
                _, _, _, day ->
            viewModel.setMonthTimesDone(day)
        }, currentYear, currentMonth , currentDay)

        datePickerDialog.show()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog (requireContext(), R.style.transparent_dialog)
        dialog.setCanceledOnTouchOutside(true)
        dialog.behavior.isDraggable = false
        return dialog
    }
}