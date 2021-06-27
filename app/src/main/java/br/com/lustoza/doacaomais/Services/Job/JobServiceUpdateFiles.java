package br.com.lustoza.doacaomais.Services.Job;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.Date;

import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Services.Arquivo.StoredHandleFileTask;
import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by Adilson on 08/01/2018.
 */

public class JobServiceUpdateFiles extends JobService {
    JobAsyncTask jobAsyncTask;
    StoredHandleFileTask storedHandleFileTask;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        TrackHelper.WriteInfo(this, "onStartJob", jobParameters.getExtras().getString("job") + "  " + new Date().toString());
        jobAsyncTask = (JobAsyncTask) new JobAsyncTask(this).execute(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        TrackHelper.WriteInfo(this, "onStopJob", new Date().toString());
        if (jobAsyncTask != null)
            jobAsyncTask.cancel(true);
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    class JobAsyncTask extends AsyncTask<JobParameters, Void, String> {

        JobServiceUpdateFiles jobServiceUpdateFiles;

        public JobAsyncTask(JobServiceUpdateFiles jobServiceUpdateFiles) {
            this.jobServiceUpdateFiles = jobServiceUpdateFiles;
        }

        @Override
        protected String doInBackground(JobParameters... jobParameters) {

            TrackHelper.WriteInfo(this, "Gravando os arquivos JobServiceUpdateFiles em : ", new Date().toString());
            storedHandleFileTask = new StoredHandleFileTask(getBaseContext());
            storedHandleFileTask.start();
            jobServiceUpdateFiles.jobFinished(jobParameters[0], true);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            TrackHelper.WriteInfo(this, "onPostExecute", new Date().toString());
        }
    }
}
