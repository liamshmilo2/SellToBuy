package com.example.selltobuy;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

/**
 * The type My job service.
 */
public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Toast.makeText(getApplicationContext(), "good", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
