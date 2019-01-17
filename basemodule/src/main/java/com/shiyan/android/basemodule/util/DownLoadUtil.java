package com.shiyan.android.basemodule.util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;

import com.shiyan.android.basemodule.enumeration.DownLoadEnum;

import java.io.File;

/**
 * 下载工具类
 * shiyan
 * update 2019.01.17
 */
public class DownLoadUtil {

    private static volatile  DownLoadUtil instance;

    private long downloadId;

    private BroadcastReceiver downLoadReceiver;


    public interface DownLoadListener{

        void onDownLoadFinish(File file);

    }

    private DownLoadUtil(){

    }

    public static DownLoadUtil getInstance(){

        if(instance == null){

            synchronized (DownLoadUtil.class){

                if(instance == null){

                    instance = new DownLoadUtil();

                }

            }
        }

        return instance;
    }

    /**
     * 获取当前下载状态
     * @param context
     * @param downLoadEnum
     * @return
     */
    public String query(Context context,DownLoadEnum downLoadEnum){

        //获取下载管理者实例
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);

        //创建查询对象
        DownloadManager.Query query = new DownloadManager.Query();

        //根据下载id,过滤结果
        query.setFilterById(downloadId);

        //执行查询,返回一个cursor
        Cursor cursor = downloadManager.query(query);

        if(!cursor.moveToFirst()){

            cursor.close();

            return null;
        }

        //已经下载的大小
        long downLoadSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));

        //总大小
        long totalSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

        int percent = (int) (downLoadSize * 100.0/ totalSize);

        switch(downLoadEnum){

            case DOWNLOADSIZE:

                return String.valueOf(downLoadSize);

            case TOTALSIZE:

                return String.valueOf(totalSize);

            case PERCENT:

                return String.valueOf(percent);

        }

        return null;

    }

    /**
     * 下载
     * @param context
     * @param url
     * @param title
     * @param desc
     * @param filepath
     * @param downLoadListener
     */
    public void downLoad(Context context,String url, String title, String desc, String filepath,DownLoadListener downLoadListener){

        downLoad(context,url,title,desc,filepath,downLoadListener,DownloadManager.Request.VISIBILITY_VISIBLE);
    }

    /**
     * 下载
     * @param context
     * @param url
     * @param title
     * @param desc
     * @param filepath
     * @param downLoadListener
     * @param visiable
     */
    public void downLoad(Context context,String url, String title, String desc, String filepath,DownLoadListener downLoadListener,int visiable){

        //创建下载请求
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        /*
         * 设置在通知栏是否显示下载通知(下载进度), 有 3 个值可选:
         *    VISIBILITY_VISIBLE:                   下载过程中可见, 下载完后自动消失 (默认)
         *    VISIBILITY_VISIBLE_NOTIFY_COMPLETED:  下载过程中和下载完成后均可见
         *    VISIBILITY_HIDDEN:                    始终不显示通知
         */
        request.setNotificationVisibility(visiable);

        //设置通知的标题
        request.setTitle(title);

        //设置通知的描述
        request.setDescription(desc);

        //设置下载文件的保存位置
        File downLoadFile = new File(filepath);

        if(downLoadFile.exists()){

            downLoadFile.delete();

        }

        request.setDestinationUri(Uri.fromFile(downLoadFile));

        //获取下载服务
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        //将下载请求加入下载队列，返回一个下载ID
        downloadId = downloadManager.enqueue(request);

        registerDownloadReceiver(context,downloadId,downLoadFile,downLoadListener);
    }

    /**
     * 注册下载广播
     */
    public void registerDownloadReceiver(Context context,long id,File file,DownLoadListener downLoadListener){

        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        downLoadReceiver = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                //拿到下载的id
                long myDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);

                if(myDownloadId == downloadId){

                    //解除广播
                    unregisterDownloadReceiver(context);

                    if(downLoadListener != null){

                        downLoadListener.onDownLoadFinish(file);
                    }

                }

            }
        };

        context.registerReceiver(downLoadReceiver,intentFilter);
    }

    /**
     * 解除广播
     * @param context
     */
    public void unregisterDownloadReceiver(Context context){

        if(downLoadReceiver == null) return;

        context.unregisterReceiver(downLoadReceiver);

    }
}


