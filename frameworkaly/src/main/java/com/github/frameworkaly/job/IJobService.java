package com.github.frameworkaly.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;

/**
 * @author lotty
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class IJobService extends JobService {

  private JobParameters jp ;

  public IJobService() {
  }

  @Override public boolean onStartJob(JobParameters params) {
    jp = params;
    Log.e("wh", "onStartJob: " + Thread.currentThread().getName());
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    jobFinished(jp,false);
    return false;
  }

  @Override public boolean onStopJob(JobParameters params) {
    return false;
  }
}
