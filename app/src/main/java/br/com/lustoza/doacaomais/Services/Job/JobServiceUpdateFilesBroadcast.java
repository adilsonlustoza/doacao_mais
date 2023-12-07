package br.com.lustoza.doacaomais.Services.Job;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.Date;

import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.PrefHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Services.Arquivo.StoredHandleFileTask;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import me.tatarka.support.os.PersistableBundle;

/**
 * Created by Adilson on 4/27/2017.
 */

public class JobServiceUpdateFilesBroadcast extends BroadcastReceiver {
    private Context context;
    private int minutos = 5;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        TrackHelper.WriteInfo(this, "onReceive", String.format(" JobServiceUpdateFilesBroadcast - %s", new Date().toString()));

        this.context = context;
        if (this.context != null)
            this.onJobExecute();
    }

    private void onJobExecute() {
        try {

            TrackHelper.WriteInfo(this, "onJobExecute", String.format(" JobServiceUpdateFilesBroadcast - %s", new Date().toString()));

            ConstantHelper.isStartedAplication = false;

            StoredHandleFileTask storedHandleFileTask = new StoredHandleFileTask(context);
            storedHandleFileTask.start();

            String sMinutos = PrefHelper.getString(context, "pref_atualizar");
            boolean isDadosLocais = PrefHelper.getBoolean(context, "pref_local");

            if (!TextUtils.isEmpty(sMinutos))
                minutos = Integer.parseInt(sMinutos);

            ComponentName componentName = new ComponentName(context, JobServiceUpdateFiles.class);
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putString("job", "job iniciado");

            int jobId = 1;
            JobInfo jobInfo = new JobInfo
                    .Builder(jobId, componentName)
                    .setBackoffCriteria((ConstantHelper.OneMinute), JobInfo.NETWORK_TYPE_ANY)
                    .setExtras(persistableBundle)
                    .setPersisted(true)
                    .setPeriodic((ConstantHelper.OneMinute * minutos))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setRequiresCharging(false)
                    .setRequiresDeviceIdle(false)
                    .build();

            JobScheduler jobScheduler = JobScheduler.getInstance(context);
            jobScheduler.schedule(jobInfo);

            if (!isDadosLocais)
                jobScheduler.cancel(jobId);

        } catch (Exception e) {
            TrackHelper.WriteError(this, "onJobExecute", e.getMessage());
        }
    }
}
