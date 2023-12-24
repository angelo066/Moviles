package com.practica1.androidengine;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Clase que se encarga de manejar los sensores
 */
public class SensorHandler implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private float[] acceloremeterValues = new float[3];

    public SensorHandler(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            acceloremeterValues[0] = event.values[0];
            acceloremeterValues[1] = event.values[1];
            acceloremeterValues[2] = event.values[2];

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Este método se llama cuando cambia la precisión del sensor (puede ignorarse en este caso)
    }

    /**
     * Metodo que registra el listener al reanudar la aplciacion
     */
    public void onResume() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Metodo que borra el listener al pausar la aplicacion
     */
    public void onPause() {
        sensorManager.unregisterListener(this);
    }

    /**
     * Metodo que borra el listener al destruir la aplicacion
     */
    public void onDestroy() {
        sensorManager.unregisterListener(this);
    }

    /**
     * @return Valor de los acelerometros
     */
    public float[] getAcceloremeterValues() {
        return acceloremeterValues;
    }

    /**
     * @param includeY Si queremos incluir el eje Y o no
     * @return Devuelve la suma de los 3 elementos del vector
     */
    public float getAcccelerometerValuesAdded(boolean includeY) {
        if (includeY)
            return acceloremeterValues[0] + acceloremeterValues[1] + acceloremeterValues[2];
        else return acceloremeterValues[0] + acceloremeterValues[2];
    }


}
