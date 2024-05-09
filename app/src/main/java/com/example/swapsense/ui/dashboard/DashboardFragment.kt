package com.example.swapsense.ui.dashboard//package com.example.swapsense.ui.dashboard
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.example.swapsense.databinding.FragmentDashboardBinding
//
//
//class com.example.swapsense.ui.dashboard.DashboardFragment : Fragment() {
//
//    private var _binding: FragmentDashboardBinding? = null
//
//    // TextViews to display sensor data
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // TODO
//        // Initialize TextViews from the layout
//
//        // TODO
//        // Get the SensorManager instance
//        // Get list of all Sensors
//        // LOG it
//
//        // TODO
//        // Get sensor instances
//
//        // TODO
//        // Check if Sensors available
//        // Toast Message if Sensor not available
//
//        // TODO
//        // Define SensorEventListeners
//
//        // TODO
//        // Register listeners
//
//    }
//
//    // TODO
//    // checkPermission for the SENSORS
//
//
//    // TODO
//    // Callback for the result from requesting permissions
//
//
//    // TODO
//    // Declare Request codes for permissions
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.swapsense.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // SensorManager instance
    private lateinit var sensorManager: SensorManager

    // TextViews for sensor data
    private lateinit var magneticTextView: TextView
    private lateinit var accelerometerTextView: TextView
    private lateinit var proximityTextView: TextView
    private lateinit var pressureTextView: TextView
    private lateinit var lightTextView: TextView
    private lateinit var gyroscopeTextView: TextView
    private lateinit var stepCounterTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize TextViews
        magneticTextView = binding.textViewMagnetic
        accelerometerTextView = binding.textViewAccelerometer
        proximityTextView = binding.textViewProximity
        pressureTextView = binding.textViewPressure
        lightTextView = binding.textViewLight
        gyroscopeTextView = binding.textViewGyroscope
        stepCounterTextView = binding.textViewStepCounter

        // Get SensorManager instance
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Check permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasSensorPermissions()) {
            requestPermissions(arrayOf(Manifest.permission.BODY_SENSORS), SENSOR_PERMISSION_CODE)
        } else {
            // Register listeners
            registerSensorListeners()
        }
    }

    private fun hasSensorPermissions(): Boolean {
        return checkSelfPermission(
            requireContext(),
            Manifest.permission.BODY_SENSORS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun registerSensorListeners() {
        // Get list of all Sensors
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sensor in sensorList) {
            when (sensor.type) {
                Sensor.TYPE_MAGNETIC_FIELD -> {
                    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                }
                Sensor.TYPE_ACCELEROMETER -> {
                    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                }
                Sensor.TYPE_PROXIMITY -> {
                    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                }
                Sensor.TYPE_PRESSURE -> {
                    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                }
                Sensor.TYPE_LIGHT -> {
                    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                }
                Sensor.TYPE_GYROSCOPE -> {
                    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                }
                Sensor.TYPE_STEP_COUNTER -> {
                    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                }
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        // Update TextViews with sensor data
        when (event.sensor.type) {
            Sensor.TYPE_MAGNETIC_FIELD -> magneticTextView.text = "Magnetic Field: ${event.values[0]}"
            Sensor.TYPE_ACCELEROMETER -> accelerometerTextView.text = "Accelerometer: ${event.values[0]}"
            Sensor.TYPE_PROXIMITY -> proximityTextView.text = "Proximity: ${event.values[0]}"
            Sensor.TYPE_PRESSURE -> pressureTextView.text = "Pressure: ${event.values[0]}"
            Sensor.TYPE_LIGHT -> lightTextView.text = "Light: ${event.values[0]}"
            Sensor.TYPE_GYROSCOPE -> gyroscopeTextView.text = "Gyroscope: ${event.values[0]}"
            Sensor.TYPE_STEP_COUNTER -> stepCounterTextView.text = "Step Counter: ${event.values[0]}"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not implemented, but required by SensorEventListener interface
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // Unregister listeners
        sensorManager.unregisterListener(this)
    }

    companion object {
        private const val SENSOR_PERMISSION_CODE = 101
    }
}
