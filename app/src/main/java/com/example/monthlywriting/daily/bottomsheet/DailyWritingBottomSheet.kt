package com.example.monthlywriting.daily.bottomsheet

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
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
import com.example.monthlywriting.util.CurrentInfo.Companion.currentDate
import com.example.monthlywriting.util.CurrentInfo.Companion.currentMonth
import com.example.monthlywriting.util.CurrentInfo.Companion.currentYear
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DailyWritingBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDailyWritingBottomSheetBinding
    private val viewModel: DailyWritingBottomSheetViewModel by activityViewModels()

    private val args: DailyWritingBottomSheetArgs by navArgs()

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), R.style.transparent_dialog)
        dialog.apply {
            setCanceledOnTouchOutside(true)
            behavior.isDraggable = false
            window?.attributes?.windowAnimations = R.style.animation_slide_up
        }
        return dialog
    }

    private fun setObservers() {
        viewModel.currentItem.observe(viewLifecycleOwner) {
            binding.bottomSheetTitle.text = it.name

            when (it.type) {
                "daily" -> {
                    displayBottomSheetByType("daily")

                    binding.bottomSheetCheckItem.apply {
                        this.adapter = DailyCheckItemAdapter(
                            it.done
                        ) { date, boolean -> setItemDone(date, boolean) }

                        val centerOfScreen = this.width / 2

                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        ).also { lm ->
                            lm.scrollToPositionWithOffset(
                                currentDate - 1,
                                centerOfScreen
                            )
                        }
                    }
                }
                "weekly" -> {
                    displayBottomSheetByType("weekly")

                    binding.bottomSheetCheckItem.apply {
                        this.adapter = DailyCheckItemAdapter(
                            it.done
                        ) { date, boolean -> setItemDone(date, boolean) }

                        val centerOfScreen = this.width / 2

                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        ).also { lm ->
                            lm.scrollToPositionWithOffset(
                                currentDate - 1,
                                centerOfScreen
                            )
                        }
                    }
                }
                "monthly" -> {
                    displayBottomSheetByType("monthly")

                    if (it.monthtimes == 0) {
                        binding.bottomSheetMonthtimes.visibility = View.GONE
                    } else {
                        val doneNumbers = it.monthtimesdone.size
                        binding.bottomSheetMonthtimes.text =
                            getString(R.string.bottom_sheet_month_times, doneNumbers, it.monthtimes)
                    }
                }
            }

            binding.bottomSheetDailyMemo.apply {
                this.adapter = DailyMemoItemAdapter(
                    it.dailymemo,
                    { date -> openDetailMemo(date) },
                    { date -> deleteMemo(date) }
                )

                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun displayBottomSheetByType(type: String) {
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

    private fun chooseDate() {
        val datePickerDialog = DatePickerDialog(requireContext(), { _, _, _, date ->
            viewModel.addMonthTimesDone(date)
        }, currentYear, currentMonth, currentDate)

        datePickerDialog.show()
    }

    private fun setItemDone(date: Int, boolean: Boolean) {
        viewModel.setItemDone(date, boolean)
    }

    private fun openDetailMemo(date: Int) {
        val action = DailyWritingBottomSheetDirections.openDetailMemo(date)
        NavHostFragment.findNavController(this@DailyWritingBottomSheet).navigate(action)
    }

    private fun deleteMemo(date: Int) {
        viewModel.deleteMemo(date)
    }
}