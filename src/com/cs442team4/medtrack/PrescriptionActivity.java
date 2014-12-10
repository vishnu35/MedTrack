package com.cs442team4.medtrack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.cs442team4.medtrack.helper.SaveImage;

import android.R.array;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

@SuppressWarnings("deprecation")
public class PrescriptionActivity extends Activity {

	final static int cameraData = 0;
	Intent intent;
	ArrayList<Uri> arraylist;
	String DirectoryPath;
	Gallery gallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prescription);

		gallery = (Gallery) findViewById(R.id.pgallery);
		DirectoryPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/MedTracker/Images";

		File[] pictures = GetFiles(DirectoryPath);
		arraylist = getFileNames(pictures, DirectoryPath);
		gallery.setAdapter(new ImageAdapter(this));
	}

	public File[] GetFiles(String directoryPath) {
		File f = new File(directoryPath);
		f.mkdirs();
		File[] file = f.listFiles();
		Arrays.sort(file);
		return file;
	}

	public ArrayList<Uri> getFileNames(File[] file, String directoryPath) {
		ArrayList<Uri> arrayFiles = new ArrayList<Uri>();
		if (file.length == 0)
			return null;
		else {
			Uri uri = null;
			for (int i = file.length-1; i >= 0; i--) {
				uri = Uri.parse("file://" + directoryPath + "/"
						+ file[i].getName());
				arrayFiles.add(uri);
			}
		}
		return arrayFiles;
	}

	public void TakePic(View v) {
		intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, cameraData);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (data != null) {
				Bundle extras = data.getExtras();
				Bitmap bp = (Bitmap) extras.get("data");
				SaveImage.Save(this, bp);

				File[] pictures = GetFiles(DirectoryPath);
				arraylist = getFileNames(pictures, DirectoryPath);
				gallery.setAdapter(new ImageAdapter(this));
			}
		}
	}

	public class ImageAdapter extends BaseAdapter {
		private Context context;
		int imgBg;

		public ImageAdapter(Context context) {
			this.context = context;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arraylist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView imageview = new ImageView(context);
			imageview.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			imageview.setScaleType(ScaleType.FIT_XY);
			imageview.setImageURI(arraylist.get(position));
			return imageview;
		}
	}
}
