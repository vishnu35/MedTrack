package com.cs442team4.medtrack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PrescriptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prescription);

	}

	public void TakePic(View v) {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {

			Bitmap bp = (Bitmap) data.getExtras().get("data");
			ImageView TimageView1 = (ImageView) findViewById(R.id.TimageView1);
			TimageView1.setVisibility(View.VISIBLE);
			TimageView1.setImageBitmap(bp);
		}
	}
}
