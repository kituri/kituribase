package com.kituri.app.ui.detailphotoview;

import com.kituri.app.model.KituriToast;
import com.kituri.app.model.MyAsyncTask;
import com.kituri.app.utils.FileManager;
import com.kituri.demo.R;

public class PicSaveTask extends MyAsyncTask<Void, Boolean, Boolean> {

    String path;
    
    static private PicSaveTask mInstance;

    public static synchronized PicSaveTask getInstance() {
		if (mInstance == null) {
			mInstance = new PicSaveTask();
		}
		return mInstance;
	}
    
    private PicSaveTask() {
        //this.path = path;
    }

    public void setPath(String path){
    	this.path = path;
    }
    
    @Override
    protected Boolean doInBackground(Void... params) {
        return FileManager.saveToPicDir(path);
    }


    @Override
    protected void onPostExecute(Boolean value) {
        super.onPostExecute(value);
        if (value) {
        	KituriToast.toastShow(R.string.save_to_album_successfully);
        } else {
        	KituriToast.toastShow(R.string.cant_save_pic);
        }
    }


}
