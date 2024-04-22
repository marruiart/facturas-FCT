package com.example.facturas_tfc.ui.firstPract.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.facturas_tfc.R
import com.example.facturas_tfc.core.extension.asRoundedCurrency
import com.example.facturas_tfc.core.extension.toStringDate
import com.example.facturas_tfc.data.repository.model.Filter
import com.example.facturas_tfc.data.repository.model.Range
import com.example.facturas_tfc.databinding.FragmentInvoicesFilterBinding
import com.example.facturas_tfc.ui.firstPract.viewmodel.InvoicesViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.slider.RangeSlider
import com.marina.ruiz.globetrotting.core.dialog.FullScreenDialogFragment
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Calendar
import java.util.TimeZone
import kotlin.math.ceil
import kotlin.math.floor

interface InvoicesFilterDialogFragmentListener {
    fun onApplyFilters(filter: Filter)
}

class InvoicesFilterDialogFragment(
    private val callback: InvoicesFilterDialogFragmentListener
) : FullScreenDialogFragment(R.layout.fragment_invoices_filter) {
    private val invoicesVM: InvoicesViewModel by activityViewModels()
    private lateinit var binding: FragmentInvoicesFilterBinding
    private lateinit var tmpFilter: Filter

    companion object {
        private const val TAG = "VIEWNEXT InvoicesFilterFragment"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoicesFilterBinding.inflate(layoutInflater)
        initTmpFilter()
        return binding.root
    }

    private fun initTmpFilter() {
        tmpFilter = invoicesVM.filter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initUI()
    }

    private fun initListeners() {
        setToolbarMenuListeners()
        setFromAndToDatePickerListeners()
        setFooterButtonsListeners()
        setSliderTouchListener()
        setCheckboxesChangeListeners()
    }

    private fun initUI() {
        setFromAndToDatesLayout()
        setAmountsLayout()
        setCheckboxesLayout()
    }

    private fun setToolbarMenuListeners() {
        binding.includeAppTopBar.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setFromAndToDatePickerListeners() {
        setDatePickerListener(binding.dpIssueDateFromDate)
        setDatePickerListener(binding.dpIssueDateToDate)
    }

    private fun setDatePickerListener(pickerBtn: Button) {
        pickerBtn.setOnClickListener {
            showDatePickerDialog(it.id == R.id.dp_issue_date_from_date)
        }
    }

    private fun setFooterButtonsListeners() {
        binding.btnApply.setOnClickListener {
            callback.onApplyFilters(tmpFilter)
        }
        binding.btnClearFilters.setOnClickListener {
            tmpFilter = Filter(
                amountRange = Range(tmpFilter.amountRange.min, tmpFilter.amountRange.max)
            )
            initUI()
        }
    }

    private fun setSliderTouchListener() {
        binding.rsAmountSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                // empty on purpose
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val min = slider.values[0]
                val max = slider.values[1]
                Log.d(
                    TAG,
                    "Selected min: ${slider.values[0]} == ${tmpFilter.amountRange.min} ? " + "${slider.values[0] == tmpFilter.amountRange.min}"
                )
                Log.d(
                    TAG,
                    "Selected max: ${slider.values[1]} == ${tmpFilter.amountRange.max} ? " + "${slider.values[1] == tmpFilter.amountRange.max}"
                )
                val selectedMin =
                    if (slider.values[0] == tmpFilter.amountRange.min) null else slider.values[0]
                val selectedMax =
                    if (slider.values[1] == tmpFilter.amountRange.max) null else slider.values[1]
                tmpFilter.setSelectedAmounts(selectedMin, selectedMax)
                setMiddleAmountsLayout(min.toInt(), max.toInt())
            }
        })
    }

    private fun setCheckboxesChangeListeners() {
        binding.cbPaid.setOnCheckedChangeListener { _, isChecked ->
            tmpFilter.setState(R.string.invoice_item_paid, isChecked)
        }
        binding.cbCancelled.setOnCheckedChangeListener { _, isChecked ->
            tmpFilter.setState(R.string.invoice_item_cancelled, isChecked)
        }
        binding.cbFixedFee.setOnCheckedChangeListener { _, isChecked ->
            tmpFilter.setState(R.string.invoice_item_fixed_fee, isChecked)
        }
        binding.cbPending.setOnCheckedChangeListener { _, isChecked ->
            tmpFilter.setState(R.string.invoice_item_pending, isChecked)
        }
        binding.cbPaymentPlan.setOnCheckedChangeListener { _, isChecked ->
            tmpFilter.setState(R.string.invoice_item_payment_plan, isChecked)
        }
    }

    // AMOUNT SLIDER LAYOUT

    private fun setAmountsLayout() {
        val min = 0f
        val max = ceil(tmpFilter.amountRange.max)
        val selectedMin = floor(tmpFilter.selectedAmount.min ?: min)
        val selectedMax = ceil(tmpFilter.selectedAmount.max ?: max)

        binding.tvMinRange.text = getString(
            R.string.invoices_filter_amount_range, min.toInt().asRoundedCurrency()
        )
        binding.tvMaxRange.text = getString(
            R.string.invoices_filter_amount_range, max.toInt().asRoundedCurrency()
        )
        binding.rsAmountSlider.valueFrom = 0f
        binding.rsAmountSlider.valueTo = max
        setMiddleAmountsLayout(selectedMin.toInt(), selectedMax.toInt())
        binding.rsAmountSlider.setValues(selectedMin, selectedMax)
    }

    private fun setMiddleAmountsLayout(min: Int, max: Int) {
        binding.tvMiddleRange.text = getString(
            R.string.invoices_filter_selected_amount_range,
            min.asRoundedCurrency(),
            max.asRoundedCurrency()
        )
    }

    // DATES LAYOUT

    private fun setFromAndToDatesLayout() {
        setDateLayout(binding.dpIssueDateFromDate, tmpFilter.dates.from)
        setDateLayout(binding.dpIssueDateToDate, tmpFilter.dates.to)
    }

    private fun setDateLayout(dateButton: Button, date: LocalDate?) {
        dateButton.text =
            date?.toStringDate("dd/MM/yyyy") ?: getString(R.string.invoices_filter_default_date)
    }

    // CHECKBOXES LAYOUT

    private fun setCheckboxesLayout() {
        binding.cbPaid.isChecked = tmpFilter.state.getOrDefault(R.string.invoice_item_paid, false)
        binding.cbCancelled.isChecked =
            tmpFilter.state.getOrDefault(R.string.invoice_item_cancelled, false)
        binding.cbFixedFee.isChecked =
            tmpFilter.state.getOrDefault(R.string.invoice_item_fixed_fee, false)
        binding.cbPending.isChecked =
            tmpFilter.state.getOrDefault(R.string.invoice_item_pending, false)
        binding.cbPaymentPlan.isChecked =
            tmpFilter.state.getOrDefault(R.string.invoice_item_payment_plan, false)
    }

    // DATEPICKER

    private fun showDatePickerDialog(isDateFrom: Boolean) {
        val datePicker = buildDatePicker(isDateFrom)

        datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER")
        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            if (isDateFrom) {
                tmpFilter.dates.from = parseToLocalDate(selectedDate)
                setDateLayout(binding.dpIssueDateFromDate, tmpFilter.dates.from)
            } else {
                tmpFilter.dates.to = parseToLocalDate(selectedDate)
                setDateLayout(binding.dpIssueDateToDate, tmpFilter.dates.to)
            }
        }
    }

    private fun getCalendarConstraints(isDateFrom: Boolean): CalendarConstraints {
        val openValue =
            if (isDateFrom) getSelectedDate(isDateFrom) else MaterialDatePicker.todayInUtcMilliseconds()
        return CalendarConstraints.Builder().setOpenAt(openValue)
            .setValidator(DateValidatorPointBackward.now()).build()
    }

    private fun getSelectedDate(isDateFrom: Boolean): Long {
        MaterialDatePicker.todayInUtcMilliseconds()
        val date = (if (isDateFrom) tmpFilter.dates.from else tmpFilter.dates.to)
            ?: return getJanuaryThisYear()
        return date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    private fun buildDatePicker(isDateFrom: Boolean): MaterialDatePicker<Long> {
        val constraints = getCalendarConstraints(isDateFrom)
        val selectedDate = getSelectedDate(isDateFrom)
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.invoices_filter_pick_date))
            .setCalendarConstraints(constraints).setSelection(selectedDate).build()
    }

    private fun getJanuaryThisYear(): Long {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.JANUARY
        return calendar.timeInMillis
    }

    private fun parseToLocalDate(value: Long): LocalDate =
        Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDate()

}